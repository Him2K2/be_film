package com.example.be_film.service;

import com.example.be_film.dtos.UserDTO;
import com.example.be_film.model.User;

public interface IUserService{
    User createUser(UserDTO userDTO);
    String login(String username,String password);

}
