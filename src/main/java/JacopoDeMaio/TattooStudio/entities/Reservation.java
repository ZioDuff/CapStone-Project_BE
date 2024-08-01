package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "data_prenotazione")
    private LocalDate dateReservation;

    @Column(name = "ora_prenotazione")
    private LocalTime timeReservation;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_di_prenotazione")
    private TypeReservation typeReservation;

    @ManyToOne
    @JoinColumn(name = "generic_id")
    @JsonIgnoreProperties("reservations")
    private Generic generic;

    @ManyToOne
    @JoinColumn(name = "tattooArtist_id")
    @JsonIgnoreProperties("reservations")
    private TattoArtist tattoArtist;


    public Reservation(LocalDate dateReservation, LocalTime timeReservation, TypeReservation typeReservation, Generic generic, TattoArtist tattoArtist) {
        this.dateReservation = dateReservation;
        this.timeReservation = timeReservation;
        this.typeReservation = typeReservation;
        this.generic = generic;
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
