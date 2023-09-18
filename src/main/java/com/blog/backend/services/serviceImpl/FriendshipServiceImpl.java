package com.blog.backend.services.serviceImpl;

import com.blog.backend.constants.BlogConstants;
import com.blog.backend.entities.Friendship;
import com.blog.backend.entities.User;
import com.blog.backend.repos.FriendshipRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceInterface.FriendshipInterface;
import com.blog.backend.utils.BlogUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.blog.backend.entities.enums.FriendshipStatus.ACCEPTED;
import static com.blog.backend.entities.enums.FriendshipStatus.PENDING;


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
                return BlogUtils.getResponseEntityWithDecision("User 1 or User 2 Doesn't Exist" , false, HttpStatus.BAD_REQUEST);
            } else {
                Optional<Friendship> friendship = friendshipRepository.findByUserID1AndUserID2(sender.get(), receiver.get());
                if (friendship.isPresent()) {
                    return BlogUtils.getResponseEntityWithDecision("Friend Request already sent" , false, HttpStatus.BAD_REQUEST);
                } else {

                    List<Friendship> friendships = buildFriendship(Sender, Receiver, sender, receiver);
                    friendshipRepository.saveAll(friendships);
                    return BlogUtils.getResponseEntityWithDecision("Friend Request Sent by " + sender.get().getFirstName() + " to " + receiver.get().getFirstName() , false, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntityWithDecision(BlogConstants.SOMETHING_WENT_WRONG, false , HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private List<Friendship> buildFriendship(Integer user1Id, Integer user2Id, Optional<User> user1, Optional<User> user2) {
        List<Friendship> friendships = new ArrayList<Friendship>();
        Friendship friendship1 = Friendship.builder().userID1(user1.get()).userID2(user2.get()).friendId(user2Id).requestedAt(LocalDateTime.now()).status(PENDING).build();
        Friendship friendship2 = Friendship.builder().userID1(user2.get()).userID2(user1.get()).friendId(user1Id).requestedAt(LocalDateTime.now()).status(PENDING).build();

        friendships.add(friendship1);
        friendships.add(friendship2);
        return friendships;
    }

    public ResponseEntity<String> acceptOrDeclineFriendRequest(Integer sender, Integer receiver, boolean decision) {
        try {
            Optional<User> receiver1 = userRepository.findById(receiver);
            Optional<User> sender1 = userRepository.findById(sender);

            if (receiver1.isEmpty() || sender1.isEmpty()) {
                return BlogUtils.getResponseEntityWithDecision("User 1 or User 2 Doesn't Exist",decision, HttpStatus.BAD_REQUEST);
            } else {
                Optional<Friendship> friendship1 = friendshipRepository.findByUserID1AndUserID2(receiver1.get(), sender1.get());

                Optional<Friendship> friendship2 = friendshipRepository.findByUserID1AndUserID2(sender1.get(), receiver1.get());

                if (friendship1.isEmpty() || friendship2.isEmpty()) {
                    return BlogUtils.getResponseEntityWithDecision("No Existing Friend Request " , decision, HttpStatus.BAD_REQUEST);
                } else {
                    if (friendship1.get().getStatus() == ACCEPTED) {
                        return BlogUtils.getResponseEntityWithDecision("Friend Request Already Accepted" , decision, HttpStatus.BAD_REQUEST);
                    } else {
                        friendship2.get().setAcceptedAt(LocalDateTime.now());
                        friendship1.get().setAcceptedAt(LocalDateTime.now());
                        if (decision) {
                            friendship1.get().setStatus(ACCEPTED);
                            friendship2.get().setStatus(ACCEPTED);
                            friendshipRepository.save(friendship1.get());
                            friendshipRepository.save(friendship2.get());
                            return BlogUtils.getResponseEntityWithDecision(sender1.get().getFirstName() + "'s Friend Request Accepted By " + receiver1.get().getFirstName(), decision , HttpStatus.OK);
                        } else {
                            friendshipRepository.delete(friendship1.get());
                            friendshipRepository.delete(friendship2.get());
                            return BlogUtils.getResponseEntityWithDecision(sender1.get().getFirstName() + "'s Friend Request Rejected By " + receiver1.get().getFirstName() , decision, HttpStatus.OK);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntityWithDecision(BlogConstants.SOMETHING_WENT_WRONG, decision, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> removeFriend(Integer remover, Integer gettingRemoved) {
        try {
            Optional<User> remover1 = userRepository.findById(remover);
            Optional<User> gettingRemoved1 = userRepository.findById(gettingRemoved);
            if (remover1.isEmpty() || gettingRemoved1.isEmpty()) {
                return BlogUtils.getResponseEntityWithDecision("User 1 or User 2 Doesn't Exist",false, HttpStatus.BAD_REQUEST);
            } else {
                Optional<Friendship> friendship1 = friendshipRepository.findByUserID1AndUserID2(remover1.get(), gettingRemoved1.get());

                Optional<Friendship> friendship2 = friendshipRepository.findByUserID1AndUserID2(gettingRemoved1.get(), remover1.get());
                if (friendship1.isEmpty() || friendship2.isEmpty()) {
                    return BlogUtils.getResponseEntityWithDecision("No Existing Friend Request ",false, HttpStatus.BAD_REQUEST);
                } else {
                    friendshipRepository.delete(friendship1.get());
                    friendshipRepository.delete(friendship2.get());
                    return BlogUtils.getResponseEntityWithDecision("Friend " + gettingRemoved1.get().getFirstName() + " Removed by " + remover1.get().getFirstName(), false, HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
        return BlogUtils.getResponseEntityWithDecision(BlogConstants.SOMETHING_WENT_WRONG,false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
