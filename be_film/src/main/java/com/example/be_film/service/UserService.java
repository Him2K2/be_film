package com.example.be_film.service;

import com.example.be_film.dtos.UserDTO;
import com.example.be_film.model.Role;
import com.example.be_film.model.User;
import com.example.be_film.repositories.RoleRepository;
import com.example.be_film.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        //kiem tra xem username ton tau chua
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("user already exists ");
        }
        Role defaultRole = roleRepository.findById(1L)
                .orElseThrow(()-> new RuntimeException("default role not found"));
        //convert from userDTO => user
        User newUser = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .DateOfBirth(userDTO.getBirth())
                .build();
        newUser.getRole().add(defaultRole);

        return null;
    }

    @Override
    public String login(String username, String password) {
        //lam trong lien quan security
        return null;
    }
}
