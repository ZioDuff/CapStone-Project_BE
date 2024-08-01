package JacopoDeMaio.TattooStudio.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record TattooSessionReservationDTO(
        @NotNull(message = "la data non puo essere vuoto")
        LocalDate dateReservation,
        @NotNull(message = "l'ora di prenotazione non puo essere vuota")
        LocalTime timeReservation,
        @NotEmpty(message = "l'username dell'utente non puo essere vuoto")
        String username
) {
}
