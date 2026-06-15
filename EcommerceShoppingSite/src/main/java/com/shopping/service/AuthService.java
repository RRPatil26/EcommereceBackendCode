package com.shopping.service;

import com.shopping.dto.LoginRequestDTO;
import com.shopping.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

}