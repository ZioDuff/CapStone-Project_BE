package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.entities.Tattoo;
import JacopoDeMaio.TattooStudio.payloads.TattooDTO;
import JacopoDeMaio.TattooStudio.repositories.TattooRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public String uploadTattooImage(MultipartFile file, String payloadToString, UUID tattooArtistId) throws IOException {
        String img = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        ObjectMapper objectMapper = new ObjectMapper();
        TattooDTO payload = objectMapper.readValue(payloadToString, TattooDTO.class);
        Tattoo newTattoo = new Tattoo(
                img, payload.name(), payload.description()
        );
        TattoArtist tattoArtist = (TattoArtist) this.genericService.findById(tattooArtistId);
        newTattoo.setTattooArtist(tattoArtist);

        this.tattooRepository.save(newTattoo);
        return img;
    }

    public Page<Tattoo> getAllTattoos(int page, int size, String sortedBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return tattooRepository.findAll(pageable);
    }
}
