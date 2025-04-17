package pl.krupa.dominika.flightbooking.flightreservationsystem.controller;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.FlightNotFoundException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.PassengerNotFoundException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.ReservationMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.ReservationService;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation.MailServiceImpl;
import pl.krupa.dominika.flightbooking.flightreservationsystem.utils.ColumnNameUtils;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private MailServiceImpl mailService;

    // Form for creating new reservation
    @GetMapping("/create")
    public String showCreateReservationForm(Model model) {
        model.addAttribute("reservation", new ReservationRequest());
        return "reservation/reservation-form";
    }

    @PostMapping("/create")
    public String createReservation(@ModelAttribute("reservation") @Valid ReservationRequest request,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "reservation/reservation-form";
        }

        try {
            ReservationResponse response = reservationService.createReservation(request);
            model.addAttribute("response", response);
            mailService.sendMail(response.getPassengerEmail(), "RESERVATION FOR FLIGHT", makeReservationMailContent(response));
            redirectAttributes.addFlashAttribute("successMessage", "Reservation has been successfully added!");
            return "redirect:/reservations";
        } catch (FlightNotFoundException | PassengerNotFoundException | IllegalArgumentException | ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "reservation/reservation-form";
    }

    @GetMapping()
    public String getAllReservations(Model model) {
        List<String> selectedColumns = Arrays.asList("reservationNumber", "flightNumber", "passengerEmail");
        List<ReservationResponse> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        model.addAttribute("columns", ColumnNameUtils.getColumnNamesSelectedMethod(ReservationResponse.class, selectedColumns));
        return "reservation/reservations-list";
    }

    // Reservation details by ID
    @GetMapping("/{reservationId}")
    public String getReservationDetails(@PathVariable Long reservationId,
                                        Model model) {
        ReservationResponse reservationResponse = reservationService.getReservationById(reservationId);

        model.addAttribute("reservation", reservationResponse);
        model.addAttribute("columns", ColumnNameUtils.getColumnNamesMethod(ReservationResponse.class));

        return "reservation/reservation-details";
    }

    // Form for editing reservation
    @GetMapping("/edit/{reservationId}")
    public String editReservation(@PathVariable Long reservationId, Model model) {
        ReservationResponse reservationResponse = reservationService.getReservationById(reservationId);
        ReservationRequest reservationRequest = reservationMapper.toReservationRequest(reservationResponse);
        model.addAttribute("reservation", reservationRequest);
        return "reservation/reservation-form";
    }

    @PostMapping("/edit/{reservationId}")
    public String updateReservation(@PathVariable Long reservationId,
                                    @ModelAttribute("reservation") ReservationRequest request,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        try {
            ReservationResponse response = reservationService.updateReservation(request, reservationId);
            model.addAttribute("reservation", response);
            redirectAttributes.addFlashAttribute("successMessage", "Reservation has been successfully updated!");
            return "redirect:/reservations";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the reservation. Please try again ...");
            return "redirect:/reservations/edit/" + reservationId;
        }
    }

    // Delete reservation
    @GetMapping("/delete/{reservationId}")
    public String deleteReservation(@PathVariable Long reservationId,
                                    RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteReservation(reservationId);
            redirectAttributes.addFlashAttribute("successMessage", "Reservation has been successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the reservation. Please try again");
        }
        return "redirect:/reservations";
    }

    private String makeReservationMailContent(ReservationResponse response) {
        return String.format("Reservation number: %s, flight number: %s",
                response.getReservationNumber(),
                response.getFlightNumber());
    }
}
