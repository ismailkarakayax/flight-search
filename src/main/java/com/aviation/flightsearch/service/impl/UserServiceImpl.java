package com.aviation.flightsearch.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.dto.request.RegisterRequest;
import com.aviation.flightsearch.entity.User;
import com.aviation.flightsearch.repository.UserRepository;
import com.aviation.flightsearch.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public UserDTO createUser(RegisterRequest newUserRequest) {
		Optional<User> user = userRepository.findByUsername(newUserRequest.getUsername());
		if (user.isPresent()) {
			throw new RuntimeException("User with this username already exists");
		}
		//newUser.setRoles(Collections.singleton(Role.USER));
		
		User newUser = modelMapper.map(newUserRequest, User.class);
		newUser.setCreatedDate(LocalDateTime.now());
        userRepository.save(newUser);

		return modelMapper.map(newUser, UserDTO.class);
	}
	
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> flights = userRepository.findAll();
		List<UserDTO> DTOs = flights.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
		return DTOs;
	}
	
	@Override
	public UserDTO getUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return modelMapper.map(user.get(), UserDTO.class);
		}
		return null;
	}
	
	@Override
	public UserDTO getUserByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return modelMapper.map(user.get(), UserDTO.class);
		}
		return null;

	}
	
	@Override
	public UserDTO updateUser(Long userId, RegisterRequest newUser) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User foundUser = user.get();
			foundUser.setEmail(newUser.getEmail());
			foundUser.setPassword(newUser.getPassword());
			
			userRepository.save(foundUser);
			return modelMapper.map(foundUser, UserDTO.class);
		} 
		return null;	
	}
	
	@Override
	public void deleteUserById(Long userId) {
		try {
			userRepository.deleteById(userId);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("User " + userId + "does not exist");
		}
	}
}
