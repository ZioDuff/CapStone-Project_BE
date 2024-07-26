package JacopoDeMaio.TattooStudio.repositories;

import JacopoDeMaio.TattooStudio.entities.Tattoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TattooRepository extends JpaRepository<Tattoo, UUID> {
}
