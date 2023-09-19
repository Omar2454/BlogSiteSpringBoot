package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.DTOs.FriendDTO;
import com.blog.backend.controllers.DTOs.UserDTO;
import com.blog.backend.controllers.exceptions.ErrorCode;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import com.blog.backend.repos.FriendshipRepository;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceInterface.UserService;
import com.blog.backend.utils.HelperFunctions;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Setter
@Getter
public class UserServiceImplementation implements UserService {
    final Logger logger = Logger.getLogger(UserServiceImplementation.class.getName());



    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FriendshipRepository friendshipRepository;



    @Override
    public ResponseEntity<?> deleteUser(Integer userId) throws GeneralException {

        Optional<User> user =userRepository.findById(userId);
        if (user.isEmpty()){
            throw new GeneralException(ErrorCode.USER_DOESNT_EXIST,"Invalid User Id");
        }else {
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully" , HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(Integer userId, UserDTO newUserDTO)throws GeneralException {
        //find User by its ID
        User user = userRepository.findById(userId).orElseThrow(()->new GeneralException(ErrorCode.USER_DOESNT_EXIST,"User does not exist"));
        //update User values on (UserDTO);
        user.setFirstName(newUserDTO.getName());
        user.setLastName(newUserDTO.getName());
        user.setEmail(newUserDTO.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        user.setImage(newUserDTO.getImage());
        userRepository.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> updateImageByUserId(Integer userId, UserDTO newImage) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        user.setImage(HelperFunctions.setBase64(userId,newImage.getImage(),"user"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




    @Override
    public User getSpecificUser(Integer userId) throws GeneralException{
        User user =  userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User is not Found"));
        user.setImage(HelperFunctions.getBase64(userId , "user"));
        return user;
    }


    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> getAllFriends(Integer userId,Pageable pageable)throws GeneralException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()){
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }else {
                List<Integer> listOfFriendsIds = new ArrayList<>();
                List<FriendDTO> friendsDto=new ArrayList<>();

                Set<Friendship> friendships= user.get().getFriendships1();
                for (Friendship friendship:
                    friendships ) {
                    listOfFriendsIds.add(friendship.getFriendId());
                }
                int page = pageable.getPageNumber();
                int size = pageable.getPageSize();
                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, listOfFriendsIds.size());

                for (int i = startIndex; i < endIndex; i++) {
                    Integer userIds = listOfFriendsIds.get(i);
                    User friend = userRepository.findById(userIds).get();
                    FriendDTO friendDTO = FriendDTO.builder()
                            .id(friend.getId())
                            .name(friend.getFirstName())
                            .pic(friend.getImage())
                            .build();
                    friendsDto.add(friendDTO);
                }

                return new ResponseEntity<>(friendsDto, HttpStatus.OK);

            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
