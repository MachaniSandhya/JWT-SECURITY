package com.jwt.secjwt.confg;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.jwt.secjwt.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetals implements UserDetails {
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;

	public MyUserDetals() {

	}

	public MyUserDetals(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		this.active = Boolean.parseBoolean(user.getActivated());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	@Override
	public String toString() {
		return "MyUserDetals [userName=" + userName + ", password=" + password + ", active=" + active + ", authorities="
				+ authorities + "]";
	}

}
