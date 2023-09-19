package com.blog.backend.services.serviceImpl.auth;

import com.blog.backend.constants.BlogConstants;
import com.blog.backend.controllers.DTOs.AuthenticationRequest;
import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.entities.enums.Role;
import com.blog.backend.entities.User;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.security.config.JwtService;
import com.blog.backend.security.responses.AuthenticationResponse;
import com.blog.backend.utils.BlogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    final Logger logger = Logger.getLogger(AuthenticationService.class.getName());






    public ResponseEntity<String> register(UserDTO userDTO) {
        try {
            if (validateUserDto(userDTO)){
                var user = userRepository.findByEmail(userDTO.getEmail());
                //if the email doesn't exist before
                if (user.isEmpty()){
                    User userToSave = buildUser(userDTO);
                    userRepository.save(userToSave);

                    var jwtToken= jwtService.generateToken(userToSave);

                    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                            .token(jwtToken)
                            .build();

                    return BlogUtils.getResponseEntity("Successfully registered "+authenticationResponse.toString(),HttpStatus.OK);
                }else{
                    return BlogUtils.getResponseEntity("Email already exists",HttpStatus.BAD_REQUEST);
                }
            }else {
                return BlogUtils.getResponseEntity(BlogConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private User buildUser(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getName())
                .lastName(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .bio(userDTO.getBio())
                .facebook(userDTO.getFacebookUsername())
                .phoneNumber(userDTO.getPhoneNumber())
                .pic(userDTO.getPic())
                .roles(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }



    private boolean validateUserDto(UserDTO userDTO) {
        return userDTO.getEmail() != null
                && userDTO.getPassword() != null
                && userDTO.getName() != null;
    }


    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            Optional<User> userToCheckEmailValidity = userRepository.findByEmail(request.getEmail());
            if (userToCheckEmailValidity.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }else {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
                var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
                var jwtToken= jwtService.generateToken(user);
                AuthenticationResponse authenticationResponse=AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();

                return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
            }
        } catch (AuthenticationException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
