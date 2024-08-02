package JacopoDeMaio.TattooStudio.repositories;

import JacopoDeMaio.TattooStudio.entities.Reservation;
import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUserIdAndTypeReservation(UUID userId, TypeReservation typeReservation);

    List<Reservation> findByDateReservationAndTattoArtistId(LocalDate date, UUID tattooArtistId);


}
