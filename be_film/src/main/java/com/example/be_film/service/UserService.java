package com.example.be_film.service;

import com.example.be_film.components.JwtTokenUtil;
import com.example.be_film.dtos.UserDTO;
import com.example.be_film.dtos.UserEditDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.exceptions.PermissonDenyException;
import com.example.be_film.model.Role;
import com.example.be_film.model.User;
import com.example.be_film.repositories.RoleRepository;
import com.example.be_film.repositories.UserRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Override

    public User createUser(UserDTO userDTO) throws DataNotFoundException, PermissonDenyException {
            String username = userDTO.getUsername();
        // Kiểm tra xem username đã tồn tại chưa
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("User already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("role not found"));
        if(role.getRoleName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissonDenyException("you can't resgiser admin account");
        }
        User newUser = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .DateOfBirth(userDTO.getBirth())
                .budget(userDTO.getBudget())
                .build();

         newUser.setRole(role);

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);


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

    @Override
    public User getUserByUserName(String username) throws DataNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(()->new DataNotFoundException("khong thay user trong database"));
    }

    @Override
    public User recharge(int budget,String username) throws DataNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy người dùng với username: " + username));

        if (budget <= 0) {
            throw new IllegalArgumentException("Số tiền nạp phải lớn hơn 0");
        }
        int currentBudget = user.getBudget();
        user.setBudget(currentBudget + budget);
        return userRepository.save(user);
    }


    @Override
    public Page<User> getAllUser(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User editUser(UserEditDTO userDTO, String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(()->new DataNotFoundException("khong thay user trong database"));

        modelMapper.typeMap(UserEditDTO.class, User.class);

        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBudget(userDTO.getBudget());
        user.setDateOfBirth(userDTO.getBirth());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
    }


}
