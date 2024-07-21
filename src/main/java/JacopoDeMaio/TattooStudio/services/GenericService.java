package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.repositories.GenericRepository;
import JacopoDeMaio.TattooStudio.repositories.TattooArtistRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class GenericService {

    @Autowired
    private GenericRepository genericRepository;
    @Autowired
    private PasswordEncoder bCrypt;
    @Autowired
    private Cloudinary cloudinaryUploader;
    @Autowired
    private TattooArtistRepository tattooArtistRepository;


    public TattoArtist saveTattooArtist(GenericDTO payload) {
        this.genericRepository.findByEmail(payload.email()).ifPresent(generic -> {
            throw new BadRequestException("L'email: " + payload.email() + " è gia in uso");
        });
        this.genericRepository.findByUsername(payload.username()).ifPresent(generic -> {
            throw new BadRequestException("L'username: " + payload.username() + " è gia in uso");
        });
        TattoArtist tattoArtist = new TattoArtist(
                payload.username(),
                payload.email(),
                bCrypt.encode(payload.password()),
                payload.name(),
                payload.surname(),
                payload.age(),
                "https://ui-avatars.com/api/" + payload.name() + payload.surname(),
                payload.description(),
                payload.phoneNumber()
        );
        return this.genericRepository.save(tattoArtist);
    }

    public TattoArtist findTattooArtistById(UUID tattooArtistId) {
        return this.tattooArtistRepository.findById(tattooArtistId).orElseThrow(
                () -> new NotFoundException("Il tatuatore con id: " + tattooArtistId + " non è stato trovato"));
    }

    public Page<TattoArtist> getAllTattooArtists(int page, int size, String sortedBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return tattooArtistRepository.findAll(pageable);
    }


//    public Page<Generic> getAllUsers(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 20) pageSize = 20;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return genericRepository.findAll(pageable);
//    }

    public Generic findById(UUID id) {
        return this.genericRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public Generic findByEmail(String email) {
        return genericRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }


    public String uploadImage(MultipartFile file, UUID genericId) throws IOException {
        String img = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        Generic found = this.findById(genericId);
        found.setAvatarURL(img);
        this.genericRepository.save(found);
        return img;
    }


    public Generic findByIdAndUpdate(UUID id, GenericDTO payload) {

        Generic found = this.findById(id);
        found.setUsername(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());

        if (found instanceof TattoArtist) {
            ((TattoArtist) found).setDescription(payload.description());
            ((TattoArtist) found).setPhoneNumber(payload.phoneNumber());
        }
        return genericRepository.save(found);
    }

    public void findByIdAndDelete(UUID userid) {
        Generic found = this.findById(userid);
        this.genericRepository.delete(found);
    }

}
