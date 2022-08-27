package life.inha.icemarket.service;

import life.inha.icemarket.domain.*;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.exception.UserNotFoundException;
import life.inha.icemarket.respository.CommentRepository;
import life.inha.icemarket.respository.PostRepository;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<UserListDto> getUserList(){
        return userRepository.getUserList();
    }

    public Integer convertGuestToUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        if(user.getRole().equals(UserRole.GUEST)) {
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
        return user.getId();
    }

    public Integer rejectUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        user.setStatus(Status.DENIED);
        userRepository.save(user);
        return user.getId();
    }
    public Integer grantAdmin(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
        return user.getId();
    }

    public Integer depriveAdmin(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getRole().equals(UserRole.ADMIN)) {
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
        return user.getId();
    }

    public List<Comment> getCommentsByUser(Integer userId) throws IllegalArgumentException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return commentRepository.getCommentsByAuthorUser(userId);
    }
    public List<Post> getPostsByUser(Integer userId) throws IllegalArgumentException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return postRepository.findByUserId(userId);
    }
}
