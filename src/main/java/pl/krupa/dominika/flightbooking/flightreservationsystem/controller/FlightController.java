package pl.krupa.dominika.flightbooking.flightreservationsystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.FlightService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;
}


public class ProjectController {

    @Autowired
    private ProjectService projectService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);


    //wyswietla formularz
    @GetMapping("/create")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("project", new ProjectRequest());
        model.addAttribute("statuses", ProjectStatus.values());

        return "project/project-form";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute("project") @Valid ProjectRequest request,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("statuses", ProjectStatus.values());
            return "project/project-form";
        }

        try {
            ProjectResponse response = projectService.createProject(request);
            model.addAttribute("response", response);
            redirectAttributes.addFlashAttribute("successMessage", "Project has been successfully added!");
            return "redirect:/projects";

        } catch (IllegalArgumentException | ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            LOGGER.error("Error validation or wrong argument saving project", e);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unexpected error occurred. Please try again.");
            LOGGER.error("Error saving project", e);
        }
        return "project/project-form";
    }


    @GetMapping()
    public String getAllProjects(Model model,
                                 @RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) ProjectStatus status,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "6") int  size,
                                 @RequestParam(defaultValue = "id,asc") String[] sort) {

        Page<ProjectResponse> pageProjects = projectService.getAllProjects(keyword, status, page, size, sort);
        List<ProjectResponse> projects = pageProjects.getContent();

        model.addAttribute("columns", ProjectResponse.getColumnNamesAllMethod());
        model.addAttribute("statuses", ProjectStatus.values());
        model.addAttribute("projects", projects);

        model.addAttribute("currentPage", pageProjects.getNumber() + 1);
        model.addAttribute("totalItems", pageProjects.getTotalElements());
        model.addAttribute("totalPages", pageProjects.getTotalPages());
        model.addAttribute("pageSizes", List.of(5, 10, 15, 20));
        model.addAttribute("size", size);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        if (keyword != null) {
            model.addAttribute("keyword", keyword);
        }
        if (status != null) {
            model.addAttribute("status", status);
        }

        return "project/projects-list";
    }


    @GetMapping("/edit/{project_id}")
    public String editProject(@PathVariable Long project_id,
                              Model model) {

        ProjectResponse project = projectService.getProjectById(project_id);

        model.addAttribute("project", project);
        model.addAttribute("statuses", ProjectStatus.values());

        return "project/project-form";
    }


    @PostMapping("/edit/{projectId}")
    public String updateProject(@PathVariable Long projectId,
                                @ModelAttribute("project") ProjectRequest request,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        try {
            projectService.updateProject(request, projectId);
            redirectAttributes.addFlashAttribute("successMessage", "Project has been successfully updated!");
            return "redirect:/projects";
        } catch (Exception e) {
            request.setId(projectId);

            model.addAttribute("errorMessage", "An error occurred while updating the project. Please try again...");
            model.addAttribute("project", request);
            model.addAttribute("statuses", ProjectStatus.values());
            return "project/project-form";
        }
    }

    @GetMapping("/{project_id}")
    public String getProjectDetails(@PathVariable Long project_id,
                                    Model model) {
        ProjectResponse project = projectService.getProjectById(project_id);

        model.addAttribute("project", project);
        model.addAttribute("columns", ProjectResponse.getColumnNamesDetailsMethod());

        return "project/project-details";
    }

    @GetMapping("/delete/{project_id}")
    public String deleteProject(@PathVariable Long project_id,
                                RedirectAttributes redirectAttributes) {

        try {
            projectService.deleteProject(project_id);
            redirectAttributes.addFlashAttribute("successMessage", "Project has been successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the project. Please try again.");
        }

        return "redirect:/projects";
    }
}