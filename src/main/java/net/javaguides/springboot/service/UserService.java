package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.RegisterRequest;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Pair<Integer, String> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new Pair<>(0, "Email already exists!");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return new Pair<>(0, "Name cannot be empty!");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new Pair<>(0, "Email cannot be empty!");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new Pair<>(0, "Passwords do not match!");
        }

        if (request.getPassword().length() < 6) {
            return new Pair<>(0, "Password must be at least 6 characters long!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        userRepository.save(user);
        return new Pair<>(user.getId().intValue(), "Registration successful!");
    }

    public Pair<User, String> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new Pair<>(user.get(), "Login successful!");
        }

        if (email.isEmpty() || password.isEmpty()) {
            return new Pair<>(null, "Email and password cannot be empty!");
        }

        return new Pair<>(null, "Invalid email or password!");
    }
}
