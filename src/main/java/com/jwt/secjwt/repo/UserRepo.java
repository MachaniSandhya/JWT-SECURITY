package com.jwt.secjwt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jwt.secjwt.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
}
