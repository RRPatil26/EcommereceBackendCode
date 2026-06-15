package com.shopping.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


//@Getter
//@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    private String mobileNo;

    private String address;

    private String role; // CUSTOMER, ADMIN

    private LocalDateTime createdDate;
    private LocalDateTime lastLoginTime;

	/*
	 * public LocalDateTime getLastLoginTime() { return lastLoginTime; }
	 * 
	 * public void setLastLoginTime(LocalDateTime lastLoginTime) {
	 * this.lastLoginTime = lastLoginTime; }
	 * 
	 * public Long getUserId() { return userId; }
	 * 
	 * public void setUserId(Long userId) { this.userId = userId; }
	 * 
	 * public String getFullName() { return fullName; }
	 * 
	 * public void setFullName(String fullName) { this.fullName = fullName; }
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * public String getPassword() { return password; }
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 * 
	 * public String getMobileNo() { return mobileNo; }
	 * 
	 * public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
	 * 
	 * public String getAddress() { return address; }
	 * 
	 * public void setAddress(String address) { this.address = address; }
	 * 
	 * public String getRole() { return role; }
	 * 
	 * public void setRole(String role) { this.role = role; }
	 * 
	 * public LocalDateTime getCreatedDate() { return createdDate; }
	 * 
	 * public void setCreatedDate(LocalDateTime createdDate) { this.createdDate =
	 * createdDate; }
	 */
}