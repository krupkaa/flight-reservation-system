package pl.krupa.dominika.flightbooking.flightreservationsystem.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.FlightMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.FlightService;
import pl.krupa.dominika.flightbooking.flightreservationsystem.utils.ColumnNameUtils;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightMapper flightMapper;

    @GetMapping("/create")
    public String showCreateFlightForm(Model model) {
        model.addAttribute("flight", new FlightRequest());
        model.addAttribute("directions", FlightEntity.DirectionEnum.values());
        return "flight/flight-form";
    }

    @PostMapping("/create")
    public String createFlight(@ModelAttribute("flight") @Valid FlightRequest request,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("directions", FlightEntity.DirectionEnum.values());
            return "flight/flight-form";
        }

        try {
            FlightResponse response = flightService.createFlight(request);
            model.addAttribute("response", response);
            redirectAttributes.addFlashAttribute("successMessage", "Flight has been successfully added!");
            return "redirect:/flights";
        } catch (IllegalArgumentException | ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unexpected error occurred. Please try again.");
        }
        return "flight/flight-form";
    }

    @GetMapping()
    public String getAllFlights(Model model) {
        List<FlightResponse> flights = flightService.getAllFlights();
        model.addAttribute("flights", flights);
        model.addAttribute("columns", ColumnNameUtils.getColumnNamesMethod(FlightResponse.class));
        model.addAttribute("directions", FlightEntity.DirectionEnum.values());
        return "flight/flights-list";
    }

    @GetMapping("/{FlightId}")
    public String getFlightDetails(@PathVariable Long flightId,
                                      Model model) {
        FlightResponse flightResponse = flightService.getFlightById(flightId);

        model.addAttribute("flight", flightResponse);
        model.addAttribute("columns", ColumnNameUtils.getColumnNamesMethod(FlightResponse.class));

        return "flight/flight-details";
    }

    @GetMapping("/edit/{flightId}")
    public String editFlight(@PathVariable Long flightId, Model model) {
        FlightResponse flightResponse = flightService.getFlightById(flightId);
        FlightRequest flightRequest = flightMapper.toFlightRequest(flightResponse);
        model.addAttribute("flight", flightRequest);
        model.addAttribute("directions", FlightEntity.DirectionEnum.values());
        return "flight/flight-form";
    }

    @PostMapping("/edit/{flightId}")
    public String updateFlight(@PathVariable Long flightId,
                                  @ModelAttribute("flight") FlightRequest request,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        try {
            FlightResponse response = flightService.updateFlight(request, flightId);
            model.addAttribute("flight", response);
            redirectAttributes.addFlashAttribute("successMessage", "Flight has been successfully updated!");
            return "redirect:/flights";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error while updating the passenger. Please try again ...");
            return "redirect:/flight/edit/" + flightId;
        }
    }

    @GetMapping("/delete/{flightId}")
    public String deleteFlight(@PathVariable Long flightId,
                                  RedirectAttributes redirectAttributes) {
        try {
            flightService.deleteFlight(flightId);
            redirectAttributes.addFlashAttribute("successMessage", "Flight has been successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the passenger. Please try again");
        }
        return "redirect:/flights";
    }
}

