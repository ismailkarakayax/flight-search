package com.aviation.flightsearch.service;

import java.util.List;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.dto.request.RegisterRequest;

public interface UserService {
	UserDTO createUser(RegisterRequest newUserRequest);
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long userId);
	UserDTO getUserByUsername(String userName);
	UserDTO updateUser(Long userId, RegisterRequest newUser);
	void deleteUserById(Long userId);
}
