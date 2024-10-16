package com.devwn.taskmanagement.services.auth;

import com.devwn.taskmanagement.dto.SignupRequest;
import com.devwn.taskmanagement.dto.UserDTO;

public interface AuthService {

    boolean hasUserWithEmail(String email);
    UserDTO signup(SignupRequest signupRequest);
}
