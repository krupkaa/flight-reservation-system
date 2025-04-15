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
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.PassengerMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.PassengerService;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private PassengerMapper passengerMapper;

    // Form for creating new passenger
    @GetMapping("/create")
    public String showCreatePassengerForm(Model model) {
        model.addAttribute("passenger", new PassengerRequest());
        return "passenger/passenger-form";
    }

    @PostMapping("/create")
    public String createPassenger(@ModelAttribute("passenger") @Valid PassengerRequest request,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "passenger/passenger-form";
        }

        try {
            PassengerResponse response = passengerService.createPassenger(request);
            model.addAttribute("response", response);
            redirectAttributes.addFlashAttribute("successMessage", "Passenger has been successfully added!");
            return "redirect:/passengers";
        } catch (IllegalArgumentException | ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Unexpected error occurred. Please try again.");
        }
        return "passenger/passenger-form";
    }

    @GetMapping()
    public String getAllPassengers(Model model) {
        List<PassengerResponse> passengers = passengerService.getAllPassengers();
        model.addAttribute("passengers", passengers);
        model.addAttribute("columns", PassengerResponse.getColumnNamesMethod());
        return "passenger/passengers-list";
    }

    @GetMapping("/{passengerId}")
    public String getPassengerDetails(@PathVariable Long passengerId,
                                 Model model) {
        PassengerResponse passengerResponse = passengerService.getPassengerById(passengerId);

        model.addAttribute("passenger", passengerResponse);
        model.addAttribute("columns", PassengerResponse.getColumnNamesMethod());

        return "passenger/passenger-details";
    }

    @GetMapping("/edit/{passengerId}")
    public String editPassenger(@PathVariable Long passengerId, Model model) {
        PassengerResponse passengerResponse = passengerService.getPassengerById(passengerId);
        PassengerRequest passengerRequest = passengerMapper.toPassengerRequest(passengerResponse);
        model.addAttribute("passenger", passengerRequest);
        return "passenger/passenger-form";
    }

    @PostMapping("/edit/{passengerId}")
    public String updatePassenger(@PathVariable Long passengerId,
                                  @ModelAttribute("passenger") PassengerRequest request,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        try {
            PassengerResponse response = passengerService.updatePassenger(request, passengerId);
            model.addAttribute("passenger", response);
            redirectAttributes.addFlashAttribute("successMessage", "Passenger has been successfully updated!");
            return "redirect:/passengers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error while updating the passenger. Please try again ...");
            return "redirect:/passengers/edit/" + passengerId;
        }
    }

    @GetMapping("/delete/{passengerId}")
    public String deletePassenger(@PathVariable Long passengerId,
                                  RedirectAttributes redirectAttributes) {
        try {
            passengerService.deletePassenger(passengerId);
            redirectAttributes.addFlashAttribute("successMessage", "Passenger has been successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the passenger. Please try again");
        }
        return "redirect:/passengers";
    }
}