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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> deleteUser(Integer userId) {
        Optional<User> user =userRepository.findById(userId);
        if (user.isEmpty()){
            return new ResponseEntity<>("User ID not found" , HttpStatus.BAD_REQUEST);
        }else {
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully" , HttpStatus.OK);
        }
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
       return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User is not Found"));
    }


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
