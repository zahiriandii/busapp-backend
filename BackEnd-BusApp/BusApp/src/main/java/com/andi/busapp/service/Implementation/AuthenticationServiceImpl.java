package com.andi.busapp.service.Implementation;

import com.andi.busapp.dto.logInDTO.AuthResponse;
import com.andi.busapp.dto.logInDTO.LoginRequest;
import com.andi.busapp.dto.logInDTO.RegisterRequest;
import com.andi.busapp.entity.User;
import com.andi.busapp.entity.enums.UserType;
import com.andi.busapp.exceptions.UserNotFoundException;
import com.andi.busapp.repository.UserRepository;
import com.andi.busapp.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = this.userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.email()));
        if (!passwordEncoder.matches(loginRequest.password(),user.getPasswordHash())) {
            throw new RuntimeException("Wrong password");
        }
      return toAuthResponse(user);
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email()))
        {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .email(registerRequest.email())
                .passwordHash(passwordEncoder.encode(registerRequest.password()))
                .phoneNumber(registerRequest.phoneNumber())
                .gender(registerRequest.gender())
                .birthDate(registerRequest.birthDate())
                .country(registerRequest.country())
                .userType(UserType.CUSTOMER)
                .build();

        User savedUser = this.userRepository.save(user);
        return toAuthResponse(savedUser);
    }

    private AuthResponse toAuthResponse(User user)
    {
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
