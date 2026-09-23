package com.example.loggingsecurity.controller;
import com.example.loggingsecurity.dto.LoginRequest;
import com.example.loggingsecurity.dto.SignupRequest;
import com.example.loggingsecurity.service.AuthService;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

private final AuthService authService;

public AuthController(
AuthService authService) {

this.authService = authService;
}

@PostMapping("/signup")
public ResponseEntity<Map<String, String>>
signup(
@RequestBody SignupRequest request) {

String message =
authService.signUp(request);

return ResponseEntity
.status(HttpStatus.CREATED)
.body(
Map.of(
"message",
message
)
);
}

@PostMapping("/login")
public ResponseEntity<Map<String, String>>
login(
@RequestBody LoginRequest request) {

String token =
authService.login(request);

return ResponseEntity.ok(
Map.of(
"token",
token
)
);
}

@GetMapping("/profile")
public ResponseEntity<Map<String, String>>
profile(
Authentication authentication) {

return ResponseEntity.ok(
Map.of(
"username",
authentication.getName(),
"message",
"Protected API accessed"
)
);
}
}


 