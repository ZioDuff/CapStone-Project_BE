package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.repositories.TattooArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TattooArtistService {
    @Autowired
    private TattooArtistRepository tattooArtistRepository;

    public void deleteTattooArtistById(UUID tattooArtistId) {
        TattoArtist found = this.tattooArtistRepository.findById(tattooArtistId)
                .orElseThrow(() -> new NotFoundException("Artista con id: " + tattooArtistId + " non trovato"));
        this.tattooArtistRepository.delete(found);
    }

}
