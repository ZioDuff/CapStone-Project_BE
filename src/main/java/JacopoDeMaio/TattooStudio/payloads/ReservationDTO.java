package JacopoDeMaio.TattooStudio.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ReservationDTO(
        @NotNull(message = "la data non puo essere vuoto")
        LocalDate dateReservation,
        @NotNull(message = "l'ora di prenotazione non puo essere vuota")
        LocalTime timeReservation,
        @NotNull(message = "il campo artista non puo essere vuoto")
        UUID tattooArtistId
) {
}
