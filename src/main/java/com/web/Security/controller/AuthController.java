package com.web.Security.controller;

import com.web.Security.dto.LoginRequestDTO;
import com.web.Security.dto.LoginResponseDTO;
import com.web.Security.dto.SignupRequestDTO;
import com.web.Security.dto.SignupResponseDTO;
import com.web.Security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginRequestDTO loginRequestDTO) {

        return new ResponseEntity<>(authService.userLogin(loginRequestDTO), HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> userSignUp(@RequestBody SignupRequestDTO signupRequestDTO) {

        return new ResponseEntity<>(authService.userSignup(signupRequestDTO), HttpStatus.OK);

    }

}
