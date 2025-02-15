package com.example.be_film.controller;

import com.example.be_film.dtos.*;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.User;
import com.example.be_film.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            if (!userDTO.getPassword().equals(userDTO.getRetypePassword()))
                return ResponseEntity.ok("Register fail");
            userService.createUser(userDTO);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        //kiem tra thong tin dang nhap va sinh token
        try {
            String token = userService.login(userLoginDTO.getUsername(),userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{username}" )
    /*  @CrossOrigin("http://localhost:3000/")*/
    public ResponseEntity<User> getUserByUserName(@PathVariable String username){
        try{
            User user = userService.getUserByUserName(username);
            return ResponseEntity.ok(user);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping()
    /*  @CrossOrigin("http://localhost:3000/")*/
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userService.getAllUser(pageRequest);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/edit/{username}")
    public UserEditDTO editUser(@Valid @RequestBody UserEditDTO userEditDTO, @PathVariable("username") String username) throws Exception {
        User userUpdated = userService.editUser(userEditDTO, username);
        UserEditDTO responseDTO = new UserEditDTO(
                userUpdated.getName(),
                userUpdated.getUsername(),
                userUpdated.getDateOfBirth(),
                userUpdated.getBudget()
        );

        return responseDTO;
    }
    @PostMapping("/recharge")
    public ResponseEntity<User> recharge(@RequestParam int budget, @RequestParam String username) {
        try {
            User updatedUser = userService.recharge(budget, username);
            return ResponseEntity.ok(updatedUser);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Delete User Successfully with id: " + userId);
    }


}

