package com.blog.backend.services.serviceImpl;

import com.blog.backend.controllers.exceptions.ErrorCode;
import com.blog.backend.controllers.exceptions.GeneralException;
import com.blog.backend.entities.Post;
import com.blog.backend.entities.React;
import com.blog.backend.entities.ReactId;
import com.blog.backend.entities.User;
import com.blog.backend.entities.enums.Reacts;
import com.blog.backend.repos.PostRepository;
import com.blog.backend.repos.ReactRepository;
import com.blog.backend.repos.UserRepository;
import com.blog.backend.services.serviceInterface.ReactsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Setter
@Getter
public class ReactsServiceImpl implements ReactsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactRepository reactRepository;

    @Override
    public ResponseEntity<?> addReact(Integer user1Id, Integer postId, Reacts react) throws GeneralException {


        User user = userRepository.findById(user1Id).orElseThrow(() -> new GeneralException(ErrorCode.USER_DOESNT_EXIST, "User does not exist"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorCode.POST_DOESNT_EXIST, "Post does not exist"));
        ReactId reactId = new ReactId(user1Id,postId);
        Optional<React> reactToCheck = reactRepository.findById(reactId);
        if (reactToCheck.isPresent()) {
            if (reactToCheck.get().getEmoji().equals(react)){
                throw new GeneralException(ErrorCode.REACT_ALREADY_EXISTS, "React already exists ");
            }else {
                reactToCheck.get().setEmoji(react);
                reactRepository.save(reactToCheck.get());
                return new ResponseEntity<>("React Updated to "+reactToCheck.get().getEmoji()+" Successfully", HttpStatus.OK);
            }
        } else {
            React react1 = React.builder()
                    .id(reactId)
                    .emoji(react)
                    .user(user)
                    .post(post)
                    .build();
            post.getReacts().add(react1);
            reactRepository.save(react1);
            postRepository.save(post);


            return new ResponseEntity<>("React Set to "+react1.getEmoji()+" Successfully", HttpStatus.OK);
        }
    }


    @Override
    public List<React> getAllReacts() {
        return reactRepository.findAll();
    }


}
