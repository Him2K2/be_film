package com.example.be_film.repositories;

import com.example.be_film.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User>findByRolesId(Long roleid);
}
