package com.example.be_film.service;

import com.example.be_film.components.JwtTokenUtil;
import com.example.be_film.dtos.UserDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Role;
import com.example.be_film.model.User;
import com.example.be_film.repositories.RoleRepository;
import com.example.be_film.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO){
        String username = userDTO.getUsername();
        // Kiểm tra xem username đã tồn tại chưa
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("User already exists");
        }


        // Lấy role mặc định từ bảng role


        // Convert từ userDTO => user
        User newUser = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .DateOfBirth(userDTO.getBirth())
                .budget(userDTO.getBudget())
                .build();

        // Khởi tạo Set role nếu là null (tránh lỗi)
        if (newUser.getRole() == null) {
            Role defaultRole = roleRepository.findById(1L).orElse(null);
            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
            newUser.setRole(roles);

        }


        // Thêm role mặc định vào người dùng


        // Mã hóa mật khẩu
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        // Lưu người dùng vào cơ sở dữ liệu
        return userRepository.save(newUser);
    }


    @Override
    public String login(String username, String password) throws DataNotFoundException {
        Optional<User> optionalUser= userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("can't find username");
        }
        User existingUser = optionalUser.get();
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new BadCredentialsException("wrong password");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

        username,password
                ,existingUser.getAuthorities()

                );
        //authenticate with java spring security;
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
