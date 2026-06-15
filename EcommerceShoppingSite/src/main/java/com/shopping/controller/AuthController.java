package com.shopping.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.shopping.dto.LoginRequestDTO;
import com.shopping.dto.LoginResponseDTO;
import com.shopping.entity.User;
import com.shopping.repository.UserRepository;
import com.shopping.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private AuthService authService;
	
	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private UserRepository userRepository;
	
	

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

		return authService.login(request);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
////////////////////////////////////////////////////////////////////////////
 
		SimpleMailMessage sm = new SimpleMailMessage();
/////////////////////////////////////////////////////////////////////////////		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
		}
////////////////////////////////////////////////////////////////////////////
		sm.setFrom(fromEmail);
		sm.setTo(user.getEmail());
		sm.setSubject("LaptopHub Registration Success....!!");
		//sm.setText("Welcome to our Institute "+user.getFullName()+"....!!");
		sm.setText(
			    "Hi " + user.getFullName() + ",\n\n" +
			    "Welcome to LaptopHub! 🎉\n\n" +
			    "Your account has been successfully created. You are now ready to explore the latest laptops, exclusive deals, and exciting offers.\n\n" +
			    "Happy Shopping!\n\n" +
			    "Regards,\n" +
			    "LaptopHub Team"
			);
		sender.send(sm);
////////////////////////////////////////////////////////////////////////////

		
		user.setRole("CUSTOMER");
		user.setCreatedDate(LocalDateTime.now());

		userRepository.save(user);

		// return ResponseEntity.ok("Registration Successful");
////////////////////////////////////////////////////////////////////////////
		return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
////////////////////////////////////////////////////////////////////////////

	}
	
	@GetMapping("/users")
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }
}