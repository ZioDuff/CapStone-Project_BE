package JacopoDeMaio.TattooStudio.repositories;


import JacopoDeMaio.TattooStudio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.rolesList r WHERE r.name = :roleName")
    List<User> findByRolesListName(@Param("roleName") String roleName);
}
