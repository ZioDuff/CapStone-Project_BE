package JacopoDeMaio.TattooStudio.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDTO(
        @NotNull(message = "la data non puo essere vuoto")
        LocalDate dateReservation,
        @NotNull(message = "l'ora di prenotazione non puo essere vuota")
        LocalTime timeReservation,
        @NotEmpty(message = "il campo artista non puo essere vuoto")
        String tattooArtistUsername
) {
}
