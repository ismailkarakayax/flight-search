package com.aviation.flightsearch.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aviation.flightsearch.dto.UserDTO;
import com.aviation.flightsearch.dto.request.RegisterRequest;
import com.aviation.flightsearch.service.UserService;

@RestController
@RequestMapping(path = "/api/user")
@Tag(name = "User Controller", description = "Endpoints for User Management")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get All Users", description = "Get a list of all users.")
	@GetMapping
	public List<UserDTO> getAllUsers(){
		return userService.getAllUsers();
	}

	@Operation(summary = "Create User", description = "Create a new user.")
	@PostMapping
	public UserDTO createUser(@RequestBody RegisterRequest newUser) {
		return userService.createUser(newUser);
	}

	@Operation(summary = "Get User by ID", description = "Get user details by user ID.")
	@GetMapping("/{userId}")
	public UserDTO getOneUser(@PathVariable Long userId) {
		return userService.getUserById(userId);
	}

	@Operation(summary = "Update User by ID", description = "Update user details by user ID.")
	@PutMapping("/{userId}")
	public UserDTO updateUser(@PathVariable Long userId, @RequestBody RegisterRequest newUser) {
		return userService.updateUser(userId, newUser);
	}

	@Operation(summary = "Delete User by ID", description = "Delete user by user ID.")
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUserById(userId);
	}
}
