package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "date_reservation")
    private LocalDate dateReservation;

    @Column(name = "time_reservation")
    private LocalTime timeReservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_reservation")
    private TypeReservation typeReservation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tattooArtist_id")
    private TattoArtist tattoArtist;


    public Reservation(LocalDate dateReservation, LocalTime timeReservation, TypeReservation typeReservation, User user, TattoArtist tattoArtist) {
        this.dateReservation = dateReservation;
        this.timeReservation = timeReservation;
        this.typeReservation = typeReservation;
        this.user = user;
        this.tattoArtist = tattoArtist;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "dateReservation=" + dateReservation +
                ", timeReservation=" + timeReservation +
                ", typeReservation=" + typeReservation +
                ", tattoArtist=" + tattoArtist +
                ", id=" + id +
                '}';
    }
}
