package com.devwn.taskmanagement.services.auth;


import com.devwn.taskmanagement.dto.SignupRequest;
import com.devwn.taskmanagement.dto.UserDTO;
import com.devwn.taskmanagement.entities.User;
import com.devwn.taskmanagement.enums.UserRole;
import com.devwn.taskmanagement.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            User user = new User();
            user.setUserRole(UserRole.ADMIN);
            user.setUsername("Renato Madeia Muiambo");
            user.setPassword(new BCryptPasswordEncoder().encode("zidelynt"));
            user.setEmail("renatomuiambo@admin.com");
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            System.out.println("Admin account created successfully.");
        }else{
            System.out.println("Admin account already exists.");
        }
    }


    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDTO signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUserRole(UserRole.EMPLOYEE);
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUsername(signupRequest.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user).getUserTDO();
    }


}
