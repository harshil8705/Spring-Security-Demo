package com.web.Security.service;

import com.web.Security.dto.LoginRequestDTO;
import com.web.Security.dto.LoginResponseDTO;
import com.web.Security.dto.SignupRequestDTO;
import com.web.Security.dto.SignupResponseDTO;
import com.web.Security.entity.User;
import com.web.Security.repository.UserRepository;
import com.web.Security.type.AuthProviderType;
import com.web.Security.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO userLogin(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());

    }

    public User signup(SignupRequestDTO signupRequestDTO) throws IllegalAccessException {

        User user = userRepository.findByUsername(signupRequestDTO.getUsername())
                .orElse(null);

        if (user != null) {
            throw new IllegalAccessException("User with username: " + user.getUsername() + " already exists!!");
        }

        return userRepository.save(User.builder()
                .username(signupRequestDTO.getUsername())
                .email(signupRequestDTO.getEmail())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .build()
        );

    }

    public SignupResponseDTO userSignup(SignupRequestDTO signupRequestDTO) throws IllegalAccessException {

        User user = signup(signupRequestDTO);
        return new SignupResponseDTO(user.getId(), user.getUsername());

    }

    @Transactional
    public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) throws IllegalAccessException {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null) {
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signup(new SignupRequestDTO(username, null, null));
        } else if (user != null) {
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            throw new BadCredentialsException("This email is already registered with provider: " + emailUser.getProviderType());
        }

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(authUtil.generateAccessToken(user), user.getId());

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);

    }
}
