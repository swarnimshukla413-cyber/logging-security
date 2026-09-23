// service/AuthService.java
package com.example.loggingsecurity.service;

import com.example.loggingsecurity.dto.LoginRequest;
import com.example.loggingsecurity.dto.SignupRequest;
import com.example.loggingsecurity.entity.User;
import com.example.loggingsecurity.exception.UserAlreadyExistsException;
import com.example.loggingsecurity.repository.UserRepository;
import com.example.loggingsecurity.security.JwtService;

import org.apache.logging.log4j.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

private static final Logger logger =
LogManager.getLogger(AuthService.class);

private final UserRepository userRepository;

private final PasswordEncoder passwordEncoder;

private final AuthenticationManager
authenticationManager;

private final CustomUserDetailsService
userDetailsService;

private final JwtService jwtService;

public AuthService(
UserRepository userRepository,
PasswordEncoder passwordEncoder,
AuthenticationManager authenticationManager,
CustomUserDetailsService userDetailsService,
JwtService jwtService) {

this.userRepository = userRepository;

this.passwordEncoder =
passwordEncoder;

this.authenticationManager =
authenticationManager;

this.userDetailsService =
userDetailsService;

this.jwtService = jwtService;
}

public String signUp(
SignupRequest request) {

logger.info(
"Sign-up request received for username: {}",
request.getUsername()
);

if (userRepository.existsByUsername(
request.getUsername())) {

logger.warn(
"Username already exists: {}",
request.getUsername()
);

throw new UserAlreadyExistsException(
"Username already exists: "
+ request.getUsername()
);
}

String encodedPassword =
passwordEncoder.encode(
request.getPassword()
);

User user =
new User(
request.getUsername(),
encodedPassword
);

User savedUser =
userRepository.save(user);

logger.info(
"User registered successfully. ID: {}",
savedUser.getId()
);

return "User registered successfully";
}

public String login(
LoginRequest request) {

logger.info(
"Login request received for username: {}",
request.getUsername()
);

authenticationManager.authenticate(
new UsernamePasswordAuthenticationToken(
request.getUsername(),
request.getPassword()
)
);

UserDetails userDetails =
userDetailsService
.loadUserByUsername(
request.getUsername()
);

String token =
jwtService.generateToken(
userDetails
);

logger.info(
"Login successful for username: {}",
request.getUsername()
);

return token;
}
}