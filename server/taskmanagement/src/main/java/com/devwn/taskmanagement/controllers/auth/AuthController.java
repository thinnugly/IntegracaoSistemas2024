package com.devwn.taskmanagement.controllers.auth;


import com.devwn.taskmanagement.dto.SigninRequest;
import com.devwn.taskmanagement.dto.SigninResponse;
import com.devwn.taskmanagement.dto.SignupRequest;
import com.devwn.taskmanagement.dto.UserDTO;
import com.devwn.taskmanagement.entities.User;
import com.devwn.taskmanagement.infra.security.JWToken;
import com.devwn.taskmanagement.repositories.UserRepository;
import com.devwn.taskmanagement.services.auth.AuthService;
import com.devwn.taskmanagement.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWToken jwToken;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists.");
        }
        UserDTO userDTO = authService.signup(signupRequest);
        if(userDTO == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinRequest.getEmail(),
                            signinRequest.getPassword())
            );

            final String generateToken = jwToken.generateJWToken((User) auth.getPrincipal());

            SigninResponse signinResponse = new SigninResponse();
            signinResponse.setToken(generateToken);
            signinResponse.setUserId(((User) auth.getPrincipal()).getId());
            signinResponse.setUsername(((User) auth.getPrincipal()).getEmail());
            signinResponse.setUserRole(((User) auth.getPrincipal()).getUserRole());

            return ResponseEntity.status(HttpStatus.OK).body(signinResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during authentication");
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }
}
