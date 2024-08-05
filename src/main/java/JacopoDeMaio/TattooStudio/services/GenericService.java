package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.entities.Tattoo;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UpdateEmailDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UpdatePasswordDTO;
import JacopoDeMaio.TattooStudio.repositories.GenericRepository;
import JacopoDeMaio.TattooStudio.repositories.TattooArtistRepository;
import JacopoDeMaio.TattooStudio.repositories.TattooRepository;
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
import java.time.LocalDate;
import java.time.Period;
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
    @Autowired
    private TattooRepository tattooRepository;

    public int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, currentDate).getYears();
        } else {
            return 0;
        }
    }


    public TattoArtist saveTattooArtist(GenericDTO payload) {
        this.genericRepository.findByEmail(payload.email()).ifPresent(generic -> {
            throw new BadRequestException("L'email: " + payload.email() + " è gia in uso");
        });
        this.genericRepository.findByUsername(payload.username()).ifPresent(generic -> {
            throw new BadRequestException("L'username: " + payload.username() + " è gia in uso");
        });
        this.tattooArtistRepository.findByPhoneNumber(payload.phoneNumber()).ifPresent(tattoArtist -> {
            throw new BadRequestException("Il numero: " + payload.phoneNumber() + " è gia associato ad un account");
        });
        TattoArtist tattoArtist = new TattoArtist(
                payload.username(),
                payload.email(),
                bCrypt.encode(payload.password()),
                payload.name(),
                payload.surname(),
                calculateAge(payload.dateOfBirth()),
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


        this.genericRepository.findByUsername(payload.username()).ifPresent(generic -> {
            if (!generic.getId().equals(id)) {
                throw new BadRequestException("L'username: " + payload.username() + " è gia in uso");
            }
        });


        if (found instanceof TattoArtist) {
            ((TattoArtist) found).setDescription(payload.description());
            ((TattoArtist) found).setPhoneNumber(payload.phoneNumber());

            this.tattooArtistRepository.findByPhoneNumber(payload.phoneNumber()).ifPresent(tattoArtist -> {
                if (!tattoArtist.getId().equals(id)) {
                    throw new BadRequestException("Il numero: " + payload.phoneNumber() + " è gia associato ad un account");
                }
            });
        }
        return genericRepository.save(found);
    }

    public void findByIdAndDelete(UUID userid) {
        Generic found = this.findById(userid);
        this.genericRepository.delete(found);
    }


    public Generic findByIdAndUpdateEmail(UUID genericId, UpdateEmailDTO payload) {

        Generic found = this.findById(genericId);

        this.genericRepository.findByEmail(payload.email()).ifPresent(generic -> {
            throw new BadRequestException("L'email: " + payload.email() + " è gia in uso");
        });

        found.setEmail(payload.email());

        return this.genericRepository.save(found);
    }

    public Generic findByIdAndUpdatePassword(UUID genericId, UpdatePasswordDTO payload) {

        Generic found = this.findById(genericId);

        found.setPassword(bCrypt.encode(payload.password()));

        return this.genericRepository.save(found);
    }

    public void findTattooArtistAndDeleteTattoo(UUID tattooArtistId, UUID tattooId) {
        TattoArtist artistFound = this.findTattooArtistById(tattooArtistId);
        Tattoo tattooFound = this.tattooRepository.findById(tattooId)
                .orElseThrow(() -> new NotFoundException("Il tatuaggio con id:" + tattooId + " non è stato trovato"));

        if (artistFound != null && tattooFound != null) {
            if (tattooFound.getTattooArtist().getId().equals(artistFound.getId())) {
                tattooRepository.delete(tattooFound);
            } else {
                throw new IllegalArgumentException("L'artista non può eliminare i tatuaggi di un altro artista");
            }
        } else {
            throw new IllegalArgumentException("Artista o tatuaggio non trovato");
        }

    }

    public TattoArtist findByUsername(String username) {
        return (TattoArtist) this.genericRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("non è stato trovato nessuno con userName: " + username));
    }
}

