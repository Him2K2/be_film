package com.example.be_film.service;

import com.example.be_film.dtos.UserDTO;
import com.example.be_film.exceptions.DataNotFoundException;
import com.example.be_film.exceptions.PermissonDenyException;
import com.example.be_film.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IUserService{
    User createUser(UserDTO userDTO) throws DataNotFoundException, PermissonDenyException;
    String login(String username,String password) throws DataNotFoundException;
    User getUserByUserName(String username)throws DataNotFoundException;

    Page<User> getAllUser(PageRequest pageRequest);
}
