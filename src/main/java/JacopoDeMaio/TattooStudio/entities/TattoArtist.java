package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TattoArtist extends Generic {

    private String description;

    private String phoneNumber;

    @OneToMany(mappedBy = "tattooArtist")
    private Set<Tattoo> tattoos;

    public TattoArtist(String username, String email, String password, String name, String surname, String age, String avatarURL, String description, String phoneNumber) {
        super(username, email, password, name, surname, age, avatarURL);
        this.role = Role.TATTOOARTIST;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
