package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already registered!";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "User registered successfully!";
    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        throw new RuntimeException("Invalid email or password!");
    }
}
