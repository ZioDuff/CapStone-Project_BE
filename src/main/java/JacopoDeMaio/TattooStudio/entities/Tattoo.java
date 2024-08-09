package JacopoDeMaio.TattooStudio.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tattoos")
@Getter
@Setter
@NoArgsConstructor
public class Tattoo {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "tattoo_URL")
    private String tattoURL;

    private String name;

    private String description;


    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "tattooArtist_Id")
    private TattoArtist tattooArtist;

    public Tattoo(String tattoURL, String name, String description) {
        this.tattoURL = tattoURL;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tattoo{" +
                "id=" + id +
                ", tattoURL='" + tattoURL + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
