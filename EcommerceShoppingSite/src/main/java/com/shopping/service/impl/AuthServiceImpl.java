package com.shopping.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.dto.LoginRequestDTO;
import com.shopping.dto.LoginResponseDTO;
import com.shopping.entity.User;
import com.shopping.repository.UserRepository;
import com.shopping.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	

	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {

		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		if (optionalUser.isEmpty()) {

			return new LoginResponseDTO(null, null, null, "User Not Found", null);
		}

		User user = optionalUser.get();

		if (!user.getPassword().equals(request.getPassword())) {

			return new LoginResponseDTO(null, null, null, "Invalid Password", null);
		}

		// Update login time
		user.setLastLoginTime(LocalDateTime.now());

		userRepository.save(user);

		return new LoginResponseDTO(user.getUserId(), user.getFullName(), user.getRole(), "Login Successful",
				user.getLastLoginTime());
	}
}
