package JacopoDeMaio.TattooStudio.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "enabled", "authorities", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String avatarURL;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> rolesList;

    public User(String username, String email, String password, String name, String surname, String avatarURL, List<Role> rolesList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.avatarURL = avatarURL;
        this.rolesList = rolesList;
    }

    public User(String username, String email, String password, String name, String surname, String avatarURL) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.avatarURL = avatarURL;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.rolesList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}


