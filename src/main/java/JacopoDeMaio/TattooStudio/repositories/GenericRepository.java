package JacopoDeMaio.TattooStudio.repositories;


import JacopoDeMaio.TattooStudio.entities.Generic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenericRepository extends JpaRepository<Generic, UUID> {
    Optional<Generic> findByEmail(String email);

    Optional<Generic> findByUsername(String username);
}
