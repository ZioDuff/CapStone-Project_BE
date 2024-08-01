package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.Reservation;
import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.ReservationDTO;
import JacopoDeMaio.TattooStudio.payloads.ResevationResponseDTO;
import JacopoDeMaio.TattooStudio.payloads.TattooSessionReservationDTO;
import JacopoDeMaio.TattooStudio.payloads.TattooSessionReservationResponseDTO;
import JacopoDeMaio.TattooStudio.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GenericService genericService;

    @Autowired
    private UserService userService;

    public Reservation findById(UUID reservationId) {
        return this.reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }


    private boolean hasActiveConsultation(UUID userId) {
        List<Reservation> reservations = reservationRepository.findByGenericIdAndTypeReservation(userId, TypeReservation.CONSULTATION);
        for (Reservation reservation : reservations) {
            if (reservation.getDateReservation().isAfter(LocalDate.now()) ||
                    (reservation.getDateReservation().isEqual(LocalDate.now()) &&
                            reservation.getTimeReservation().isAfter(LocalTime.now()))) {
                return true;
            }
        }
        return false;
    }


    public ResevationResponseDTO saveReservation(ReservationDTO payload, UUID userId) {
        User userFound = (User) this.genericService.findById(userId);
        TattoArtist tattooArtistFound = this.genericService.findTattooArtistById(payload.tattooArtistId());


        Reservation reservation = new Reservation(
                payload.dateReservation(),
                payload.timeReservation(),
                TypeReservation.CONSULTATION,
                userFound,
                tattooArtistFound
        );

        if (hasActiveConsultation(userFound.getId())) {
            throw new BadRequestException("Hai gia una prenotazione di consultazione nel nostro studio!");
        }


        userFound.getReservations().add(reservation);
        tattooArtistFound.getReservations().add(reservation);

        System.out.println("Saved Reservation for User: " + userFound.getReservations());
        System.out.println("Saved Reservation for Artist: " + tattooArtistFound.getReservations());


        Reservation savedReservation = this.reservationRepository.save(reservation);
        return new ResevationResponseDTO(
                savedReservation.getDateReservation(),
                savedReservation.getTimeReservation(),
                savedReservation.getTypeReservation(),
                savedReservation.getTattoArtist().getUsername()

        );


    }

    public TattooSessionReservationResponseDTO saveTattooSessionReservation(TattooSessionReservationDTO payload, UUID tattooArtistId) {
        User found = this.userService.findUserByUsername(payload.username());
        TattoArtist tattoArtist = this.genericService.findTattooArtistById(tattooArtistId);

        List<Reservation> existingConsultations = reservationRepository.findByGenericIdAndTypeReservation(found.getId(), TypeReservation.CONSULTATION);
        for (Reservation consultation : existingConsultations) {
            reservationRepository.delete(consultation);
        }


        Reservation reservation = new Reservation(
                payload.dateReservation(),
                payload.timeReservation(),
                TypeReservation.TATTOO_SESSION,
                found,
                tattoArtist
        );

        found.getReservations().add(reservation);
        tattoArtist.getReservations().add(reservation);

        Reservation savedReservation = this.reservationRepository.save(reservation);
        return new TattooSessionReservationResponseDTO(
                savedReservation.getDateReservation(),
                savedReservation.getTimeReservation(),
                savedReservation.getTypeReservation(),
                savedReservation.getGeneric().getUsername()
        );
    }

    public Page<Reservation> getAllReservation(int page, int size, String sortedBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return reservationRepository.findAll(pageable);
    }


    public void deleteOwnReservation(UUID reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("la prenotazione con id: " + reservationId + " non è stata trovata"));

        this.reservationRepository.delete(reservation);
    }


    public List<Reservation> findReservationByDateAndTattooArtist(LocalDate date, UUID tattooArtistId) {
        return this.reservationRepository.findByDateReservationAndTattoArtistId(date, tattooArtistId);
    }


}


