package com.shopping.dto;

import java.time.LocalDateTime;

public class LoginResponseDTO {

    private Long userId;
    private String fullName;
    private String role;
    private String message;
    private LocalDateTime loginTime;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(
            Long userId,
            String fullName,
            String role,
            String message,
            LocalDateTime loginTime) {

        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
        this.message = message;
        this.loginTime = loginTime;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

    // Getters and Setters
}