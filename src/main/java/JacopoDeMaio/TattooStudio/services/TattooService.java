package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.entities.Tattoo;
import JacopoDeMaio.TattooStudio.payloads.TattooDTO;
import JacopoDeMaio.TattooStudio.repositories.TattooRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class TattooService {


    @Autowired
    public Cloudinary cloudinaryUploader;
    @Autowired
    private TattooRepository tattooRepository;

    @Autowired
    private GenericService genericService;

    public String uploadTattooImage(MultipartFile file, TattooDTO payload, UUID tattooArtistId) throws IOException {
        String img = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        Tattoo newTattoo = new Tattoo(
                img, payload.name(), payload.description()
        );
        TattoArtist tattoArtist = (TattoArtist) this.genericService.findById(tattooArtistId);
        newTattoo.setTattooArtist(tattoArtist);

        this.tattooRepository.save(newTattoo);
        return img;
    }
}
