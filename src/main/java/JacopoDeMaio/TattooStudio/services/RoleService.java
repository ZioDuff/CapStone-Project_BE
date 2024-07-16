package JacopoDeMaio.TattooStudio.services;


import JacopoDeMaio.TattooStudio.entities.Role;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.roleDTO.NewRoleDTO;
import JacopoDeMaio.TattooStudio.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(NewRoleDTO body) {
        Role user = new Role(body.name());
        return this.roleRepository.save(user);
    }

    public Role findById(UUID id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Role findByRoleName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new NotFoundException("nessun parametro torvato"));
    }
}
