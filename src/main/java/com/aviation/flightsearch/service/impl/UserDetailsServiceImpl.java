package com.aviation.flightsearch.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aviation.flightsearch.entity.User;
import com.aviation.flightsearch.repository.UserRepository;
import com.aviation.flightsearch.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(userName).get();
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).get();
		return JwtUserDetails.create(user);
	}

}
