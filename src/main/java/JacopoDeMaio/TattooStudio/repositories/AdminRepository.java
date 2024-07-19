package JacopoDeMaio.TattooStudio.repositories;

import JacopoDeMaio.TattooStudio.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
