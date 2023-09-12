package com.blog.backend.services.serviceImpl;

import com.blog.backend.constants.BlogConstants;
import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import com.blog.backend.repos.FriendshipRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceinterface.FriendshipInterface;
import com.blog.backend.utils.BlogUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.blog.backend.entities.enums.FriendshipStatus.*;


@Service
@AllArgsConstructor
@Setter
@Getter
public class FriendshipServiceImpl implements FriendshipInterface {

    final Logger logger = Logger.getLogger(FriendshipServiceImpl.class.getName());



    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;


    public ResponseEntity<String> addFriend(Integer Sender, Integer Receiver) {
        try {
            Optional<User> sender = userRepository.findById(Sender);
            Optional<User> receiver = userRepository.findById(Receiver);

            if (sender.isEmpty() || receiver.isEmpty()) {
                return BlogUtils.getResponseEntity("User 1 or User 2 Doesn't Exist",HttpStatus.BAD_REQUEST);
            }else {
                Optional<Friendship> friendship =friendshipRepository.findByUserID1AndUserID2(sender.get(),receiver.get());
                if (friendship.isPresent())
                {
                    return BlogUtils.getResponseEntity("Friend Request already sent",HttpStatus.BAD_REQUEST);
                }else {

                    List<Friendship> friendships=buildFriendship(Sender, Receiver, sender, receiver);
                    friendshipRepository.saveAll(friendships);
                    return BlogUtils.getResponseEntity("Friend Request Sent by " + sender.get().getFirstName()+" to "+receiver.get().getFirstName()
                            ,HttpStatus.OK);
                }
            }
        } catch (AuthenticationException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private List<Friendship> buildFriendship(Integer user1Id, Integer user2Id, Optional<User> user1, Optional<User> user2) {
       List<Friendship> friendships = new ArrayList<Friendship>();
        Friendship friendship1 = Friendship.builder()
                .userID1(user1.get())
                .userID2(user2.get())
                .friendId(user2Id)
                .requestedAt(LocalDateTime.now())
                .status(PENDING)
                .build();
        Friendship friendship2 = Friendship.builder()
                .userID1(user2.get())
                .userID2(user1.get())
                .friendId(user1Id)
                .requestedAt(LocalDateTime.now())
                .status(PENDING)
                .build();

        friendships.add(friendship1);
        friendships.add(friendship2);
        return friendships;
    }

    public ResponseEntity<String> acceptOrDeclineFriendRequest(Integer sender, Integer receiver, boolean decision) {
        try {
            Optional<User> receiver1= userRepository.findById(receiver);
            Optional<User> sender1 = userRepository.findById(sender);

            if (receiver1.isEmpty() || sender1.isEmpty()) {
                return BlogUtils.getResponseEntity("User 1 or User 2 Doesn't Exist",HttpStatus.BAD_REQUEST);
            }else {
                Optional<Friendship> friendship1 =friendshipRepository.findByUserID1AndUserID2(receiver1.get(),sender1.get());

                Optional<Friendship> friendship2 =friendshipRepository.findByUserID1AndUserID2(sender1.get(),receiver1.get());

                if (friendship1.isEmpty() || friendship2.isEmpty())
                {
                    return BlogUtils.getResponseEntity("No Existing Friend Request ",HttpStatus.BAD_REQUEST);
                }else {
                    if (friendship1.get().getStatus()==ACCEPTED){
                        return BlogUtils.getResponseEntity("Friend Request Already Accepted",HttpStatus.BAD_REQUEST);
                    }else {
                        friendship2.get().setAcceptedAt(LocalDateTime.now());
                        friendship1.get().setAcceptedAt(LocalDateTime.now());
                        if (decision){
                            friendship1.get().setStatus(ACCEPTED);
                            friendship2.get().setStatus(ACCEPTED);
                            friendshipRepository.save(friendship1.get());
                            friendshipRepository.save(friendship2.get());
                            return BlogUtils.getResponseEntity("Friend Request Accepted By " + receiver1.get().getFirstName()+" to "+sender1.get().getFirstName()
                                    ,HttpStatus.OK);
                        }else {
                            friendshipRepository.delete(friendship1.get());
                            friendshipRepository.delete(friendship2.get());
                            return BlogUtils.getResponseEntity("Friend Request Rejected by " + receiver1.get().getFirstName()+" to "+sender1.get().getFirstName()
                                    ,HttpStatus.OK);
                        }
                    }

                }
            }
        } catch (AuthenticationException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntity(BlogConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
