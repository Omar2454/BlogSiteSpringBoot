package com.blog.backend.controllers;


import com.blog.backend.constants.BlogConstants;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.services.serviceImpl.FriendshipServiceImpl;
import com.blog.backend.utils.BlogUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/blog/friendship")
public class FriendshipController {
    private final FriendshipServiceImpl friendshipService;




    private static final Logger logger = LoggerFactory.getLogger(FriendshipController.class);






    @PostMapping("add/{user1Id}/{user2Id}")
    public ResponseEntity<String> addFriend(@PathVariable("user1Id") Integer user1Id,@PathVariable("user2Id") Integer user2Id ) {
        try {
             friendshipService.addFriend(user1Id,user2Id);
         return friendshipService.acceptOrDeclineFriendRequest(user1Id,user2Id, true);

        } catch (Exception e) {
            logger.error("Error Logging user", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PutMapping("decide/{user1Id}/{user2Id}/{decision}")
    public ResponseEntity<String> acceptFriendOrDecline(@PathVariable("user1Id") Integer user1Id,@PathVariable("user2Id") Integer user2Id,@PathVariable("decision") boolean decision ) {
        try {
            return friendshipService.acceptOrDeclineFriendRequest(user1Id,user2Id,decision);
        } catch (Exception e) {
            logger.error("Error Logging user", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("remove/{user1Id}/{user2Id}")
    public ResponseEntity<String> removeFriend(@PathVariable("user1Id") Integer remover,@PathVariable("user2Id") Integer gettingRemoved) {
        try {
            return friendshipService.removeFriend(remover,gettingRemoved);
        } catch (Exception e) {
            logger.error("Error Logging user", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("isFriends/{user1Id}/{user2Id}")
    public ResponseEntity<?>isFriends(@PathVariable("user1Id") Integer user1Id,@PathVariable("user2Id") Integer user2Id) throws GeneralException {
        return friendshipService.isFriendsCheck(user1Id,user2Id);

    }







}
