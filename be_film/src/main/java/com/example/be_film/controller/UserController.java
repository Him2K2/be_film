package com.example.be_film.controller;

import com.example.be_film.dtos.*;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.model.Film;
import com.example.be_film.model.User;
import com.example.be_film.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
}

