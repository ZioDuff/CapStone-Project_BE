package JacopoDeMaio.TattooStudio.repositories;

import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TattooArtistRepository extends JpaRepository<TattoArtist, UUID> {
    Optional<TattoArtist> findByPhoneNumber(String phoneNumber);

}
