package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.repositories.TattooArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TattooArtistService {
    @Autowired
    private TattooArtistRepository tattooArtistRepository;

}
