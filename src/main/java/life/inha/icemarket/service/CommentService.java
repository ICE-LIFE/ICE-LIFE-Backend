package life.inha.icemarket.service;

import life.inha.icemarket.domain.Comment;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.GetCommentRes;
import life.inha.icemarket.dto.PostCommentReq;
import life.inha.icemarket.dto.PostCommentRes;
import life.inha.icemarket.exception.UserNotFoundException;
import life.inha.icemarket.respository.CommentRepository;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public PostCommentRes createComment(PostCommentReq dto) {

        User authorUser = userRepository.findById(dto.getAuthorIdx()).orElseThrow(()->new UserNotFoundException(dto.getAuthorIdx()));
//        Post post postRepository.findById(dto.getPostIdx()).orElse(null);
        Comment comment = Comment.createComment(dto,
              // , post
                authorUser
                );

        commentRepository.save(comment);

        return new PostCommentRes(comment.getId());
    }

    @Transactional(readOnly = true)
    public List<GetCommentRes> readComment(Integer postIdx){
        List<Comment> commentList = commentRepository.findByPostId(postIdx);
        List<GetCommentRes> commentResList = new ArrayList<>();
        for(Comment comment : commentList){
            commentResList.add(GetCommentRes.createCommentRes(comment));
        }
        return commentResList;
    }

    public void deleteComment(Integer id) {
        commentRepository.delete(
                commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + id))
        );
    }
}
