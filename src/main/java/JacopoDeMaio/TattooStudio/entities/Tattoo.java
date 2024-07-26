package JacopoDeMaio.TattooStudio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tattoos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tattoo {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "tattoo_URL")
    private String tattoURL;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "tattooArtist_Id")
    private TattoArtist tattooArtist;

    public Tattoo(String tattoURL, String name, String description) {
        this.tattoURL = tattoURL;
        this.name = name;
        this.description = description;
    }
}
