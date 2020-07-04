package com.jwt.secjwt.confg;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.secjwt.entity.User;
import com.jwt.secjwt.repo.UserRepo;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByUserName(username);
		user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return user.map(MyUserDetals::new).get();
	}

}
