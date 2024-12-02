package com.example.be_film.service;

import com.example.be_film.dtos.UserDTO;
import com.example.be_film.model.Role;
import com.example.be_film.model.User;
import com.example.be_film.repositories.RoleRepository;
import com.example.be_film.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User createUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        //kiem tra xem username ton tau chua
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("user already exists ");
        }

        User newUser = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .DateOfBirth(userDTO.getBirth())
                .build();

        Role defaultRole = roleRepository.findById(1L)
                .orElse(null);
        if (defaultRole != null) {
            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
            newUser.setRole(roles);
        }
        userRepository.save(newUser);
        return null;
    }

    @Override
    public String login(String username, String password) {
        //lam trong lien quan security
        return null;
    }
}
