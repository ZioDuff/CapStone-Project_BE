package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.Role;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends Generic {

    public User(String username, String email, String password, String name, String surname, int age, String avatarURL) {
        super(username, email, password, name, surname, age, avatarURL);
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
