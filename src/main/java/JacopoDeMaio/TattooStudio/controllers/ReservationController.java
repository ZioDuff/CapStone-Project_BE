package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.entities.Reservation;
import JacopoDeMaio.TattooStudio.payloads.ReservationDTO;
import JacopoDeMaio.TattooStudio.payloads.ResevationResponseDTO;
import JacopoDeMaio.TattooStudio.payloads.TattooSessionReservationDTO;
import JacopoDeMaio.TattooStudio.payloads.TattooSessionReservationResponseDTO;
import JacopoDeMaio.TattooStudio.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    @GetMapping("/me")
    public Page<Reservation> getReservationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortedBy
    ) {
        return this.reservationService.getAllReservation(page, size, sortedBy);
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public ResevationResponseDTO saveReservation(@RequestBody ReservationDTO payload, @AuthenticationPrincipal Generic currentAuthenticatedUser) {
        return this.reservationService.saveReservation(payload, currentAuthenticatedUser.getId());
    }

    @PostMapping("/tattooSessionRequest/me")
    @ResponseStatus(HttpStatus.CREATED)
    public TattooSessionReservationResponseDTO saveTattooSessionReservation(@RequestBody TattooSessionReservationDTO payload, @AuthenticationPrincipal Generic currentAuthenticatedUser) {
        return this.reservationService.saveTattooSessionReservation(payload, currentAuthenticatedUser.getId());
    }


    @DeleteMapping("/me/{reservationId}")
    public void deleteOwnReservation(@PathVariable UUID reservationId) {
        this.reservationService.deleteOwnReservation(reservationId);
    }


    @GetMapping("/me/{reservationId}")
    public Reservation findReservationById(@PathVariable UUID reservationId) {
        return this.reservationService.findById(reservationId);
    }


    @GetMapping("/me/date")
    @PreAuthorize("hasAuthority('TATTOOARTIST')")
    public List<Reservation> findReservationByDateAndTattooArtist(@RequestParam LocalDate date, @AuthenticationPrincipal Generic currentAuthenticatedUser) {
        return this.reservationService.findReservationByDateAndTattooArtist(date, currentAuthenticatedUser.getId());
    }

}
