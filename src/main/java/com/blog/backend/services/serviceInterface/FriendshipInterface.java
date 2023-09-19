package com.blog.backend.services.serviceInterface;

import org.springframework.http.ResponseEntity;

public interface FriendshipInterface {
    ResponseEntity<String> addFriend(Integer Sender, Integer Receiver);
    ResponseEntity<String> acceptOrDeclineFriendRequest(Integer sender, Integer receiver, boolean decision);
    ResponseEntity<String> removeFriend(Integer remover, Integer gettingRemoved);
}
