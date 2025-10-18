package com.web.Security.service;

import com.web.Security.dto.LoginRequestDTO;
import com.web.Security.dto.LoginResponseDTO;
import com.web.Security.dto.SignupRequestDTO;
import com.web.Security.dto.SignupResponseDTO;
import com.web.Security.entity.User;
import com.web.Security.repository.UserRepository;
import com.web.Security.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;

    public LoginResponseDTO userLogin(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());

    }

    public SignupResponseDTO userSignup(SignupRequestDTO signupRequestDTO) throws IllegalAccessException {

        User user = userRepository.findByUsername(signupRequestDTO.getUsername())
                .orElse(null);

        if (user != null) {
             throw new IllegalAccessException("User with username: " + user.getUsername() + " already exists!!");
        }

        user = userRepository.save(User.builder()
                        .username(signupRequestDTO.getUsername())
                        .email(signupRequestDTO.getEmail())
                        .password(signupRequestDTO.getPassword())
                        .build()
        );

        return new SignupResponseDTO(user.getId(), user.getUsername());

    }
}
