package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.User;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceinterface.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Setter
@Getter
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public User addUser(UserDTO userDTO) {

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User ID Not Found"));
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(Integer userId, UserDTO newUserDTO) {
        //find User by its ID
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        //update User values on (UserDTO)
        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setEmail(newUserDTO.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    public User getSpecificUser(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User is not Found"));
        return userRepository.getReferenceById(userId);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
