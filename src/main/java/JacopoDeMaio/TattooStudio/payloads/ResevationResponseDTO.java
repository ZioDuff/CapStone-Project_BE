package JacopoDeMaio.TattooStudio.payloads;

import JacopoDeMaio.TattooStudio.enums.TypeReservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResevationResponseDTO(
        LocalDate dateReservation,
        LocalTime timeReservation,
        TypeReservation typeReservation,
        String tattooArtistUsername
) {
}
