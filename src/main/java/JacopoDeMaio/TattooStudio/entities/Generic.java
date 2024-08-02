package JacopoDeMaio.TattooStudio.entities;

import JacopoDeMaio.TattooStudio.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "enabled", "authorities", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public abstract class Generic implements UserDetails {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    protected UUID id;

    protected String username;

    protected String email;

    protected String password;

    protected String name;

    protected String surname;

    protected int age;

    @Column(name = "avatar_URL")
    protected String avatarURL;

    @Enumerated(EnumType.STRING)
    protected Role role;


    public Generic(String username, String email, String password, String name, String surname, int age, String avatarURL) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.avatarURL = avatarURL;
    }
}


