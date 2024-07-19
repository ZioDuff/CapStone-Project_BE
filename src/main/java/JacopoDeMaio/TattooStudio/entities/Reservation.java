package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.TypeReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate dateReservation;

    private TypeReservation typeReservation;

    @ManyToOne
    @JoinColumn(name = "generic_id")
    private Generic generic;

    public Reservation(LocalDate dateReservation, TypeReservation typeReservation, Generic generic) {
        this.dateReservation = dateReservation;
        this.typeReservation = typeReservation;
        this.generic = generic;
    }
}
