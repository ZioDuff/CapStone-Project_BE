package JacopoDeMaio.TattooStudio.payloads;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResevationResponseDTO(
        LocalDate dateReservation,
        LocalTime timeReservation,
        String typeReservation,
        String tattooArtistUsername
) {
}
