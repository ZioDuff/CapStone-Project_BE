package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.entities.Reservation;
import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.ReservationDTO;
import JacopoDeMaio.TattooStudio.payloads.TattooSessionReservationDTO;
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

    private TypeReservation convertStringToTypeReservation(String type) {
        try {
            return TypeReservation.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("The selected staff type don't exists");
        }
    }

    public Reservation findById(UUID reservationId) {
        return this.reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }


    private boolean hasActiveConsultation(UUID userId) {
        List<Reservation> reservations = reservationRepository.findByUserIdAndTypeReservation(userId, TypeReservation.CONSULTATION);
        for (Reservation reservation : reservations) {
            if (reservation.getDateReservation().isAfter(LocalDate.now()) ||
                    (reservation.getDateReservation().isEqual(LocalDate.now()) &&
                            reservation.getTimeReservation().isAfter(LocalTime.now()))) {
                return true;
            }
        }
        return false;
    }


    public Reservation saveReservation(ReservationDTO payload, UUID userId) {
        User userFound = (User) this.genericService.findById(userId);
        TattoArtist tattooArtistFound = this.genericService.findByUsername(payload.tattooArtistUsername());

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();


        if (payload.dateReservation().isBefore(today)) {
            throw new BadRequestException("La data della prenotazione deve essere almeno la data di oggi.");
        }


        if (payload.dateReservation().isEqual(today) && payload.timeReservation().isBefore(now.plusHours(1))) {
            throw new BadRequestException("L'ora della prenotazione deve essere almeno un'ora dopo l'ora attuale.");
        }

        Reservation reservation = new Reservation(
                payload.dateReservation(),
                payload.timeReservation(),
                convertStringToTypeReservation("CONSULTATION"),
                userFound,
                tattooArtistFound
        );

        if (hasActiveConsultation(userFound.getId())) {
            throw new BadRequestException("Hai gia una prenotazione di consultazione nel nostro studio!");
        }


        return this.reservationRepository.save(reservation);
    }

    public Reservation saveTattooSessionReservation(TattooSessionReservationDTO payload, UUID tattooArtistId) {
        User found = this.userService.findUserByUsername(payload.username());
        TattoArtist tattoArtist = this.genericService.findTattooArtistById(tattooArtistId);

        List<Reservation> existingConsultations = reservationRepository.findByUserIdAndTypeReservation(found.getId(), TypeReservation.CONSULTATION);
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


        return this.reservationRepository.save(reservation);


    }

    public Page<Reservation> getAllReservation(int page, int size, String sortedBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return reservationRepository.findAll(pageable);
    }


    public void deleteOwnReservation(UUID reservationId, UUID genericId) {

        Generic generic = this.genericService.findById(genericId);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        Reservation reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("la prenotazione con id: " + reservationId + " non è stata trovata"));

        if (reservation.getUser().getId().equals(generic.getId()) && reservation.getDateReservation().isEqual(today)) {
            throw new BadRequestException("Attenzione! non puoi eliminare più la prenotazione. Per ulteriori informazioni chiamaci");
        }
        if (reservation.getUser().getId().equals(generic.getId()) && reservation.getDateReservation().isEqual(tomorrow)) {
            throw new BadRequestException("Attenzione! non puoi eliminare più la prenotazione. Per ulteriori informazioni chiamaci");
        }

        if (generic instanceof User user) {
            if (reservation.getUser().getRole().equals(user.getRole()) && reservation.getTypeReservation().name().equalsIgnoreCase("TATTOO_SESSION")) {
                throw new BadRequestException("Non puo eliminare Le prenotazioni con tipo: TATTOO SESSION");
            }
        } else if (generic instanceof TattoArtist tattooArtist) {
            this.reservationRepository.delete(reservation);
        }

    }


    public List<Reservation> findReservationByDateAndTattooArtist(LocalDate date, UUID tattooArtistId) {
        return this.reservationRepository.findByDateReservationAndTattoArtistId(date, tattooArtistId);
    }

    public List<Reservation> findReservationByUserId(UUID userId) {
        return this.reservationRepository.findByUserId(userId);

    }


}


