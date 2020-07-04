package com.jwt.secjwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.secjwt.jwt.JWTUtil;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsService userDeatilsService;

	@Autowired
	JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String auth_hed = request.getHeader("Authorization");
		String userName = null;
		String jwt = null;
		if(auth_hed != null && auth_hed.startsWith("Bearer ")) {
			jwt = auth_hed.substring(7);
			userName = jwtUtil.extractUsername(jwt);
		}
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userDeatilsService.loadUserByUsername(userName);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken userNamePwdAuthTkn = 
						new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
				userNamePwdAuthTkn.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println(userDetails);
				SecurityContextHolder.getContext().setAuthentication(userNamePwdAuthTkn);
				System.out.println("JWTFILETER_1");
			}
		}
		System.out.println("JWTFILETER_2");
		filterChain.doFilter(request, response);
	}
}
