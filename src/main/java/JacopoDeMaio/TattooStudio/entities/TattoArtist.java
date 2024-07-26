package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class TattoArtist extends Generic {

    private String description;

    private String phoneNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "tattooArtist")
    private Set<Tattoo> tattoos;

    public TattoArtist(String username, String email, String password, String name, String surname, int age, String avatarURL, String description, String phoneNumber) {
        super(username, email, password, name, surname, age, avatarURL);
        this.role = Role.TATTOOARTIST;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String toString() {
        return "TattoArtist{" +
                "description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tattoos=" + tattoos +
                "} " + super.toString();
    }
}
