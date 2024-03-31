package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.UserDto;
import com.estsoft13.matdori.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;
    public void saveUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    public User findByUsernameAndEmail(String email, String username) {
        return userRepository.findByUsernameAndEmail(email, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found by: " + email + " and "+ username));
    }

    public boolean isEmailUnique(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    public void resetPassword(User user, String newPassword){
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}