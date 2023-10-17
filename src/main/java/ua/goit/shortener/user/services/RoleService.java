package ua.goit.shortener.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goit.shortener.user.entity.Role;
import ua.goit.shortener.user.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

}