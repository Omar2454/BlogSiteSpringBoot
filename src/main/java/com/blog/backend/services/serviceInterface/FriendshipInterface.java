package com.blog.backend.services.serviceInterface;

import com.blog.backend.controllers.exceptions.GeneralException;
import org.springframework.http.ResponseEntity;

public interface FriendshipInterface {
    ResponseEntity<String> addFriend(Integer Sender, Integer Receiver);
    ResponseEntity<String> acceptOrDeclineFriendRequest(Integer sender, Integer receiver, boolean decision);
    ResponseEntity<String> removeFriend(Integer remover, Integer gettingRemoved);

    ResponseEntity<?> isFriendsCheck(Integer user1Id, Integer user2Id) throws GeneralException;
}
