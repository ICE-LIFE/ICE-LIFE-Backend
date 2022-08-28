package life.inha.icemarket.service;

import life.inha.icemarket.domain.Comment;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.CommentResDto;
import life.inha.icemarket.dto.CommentSaveReqDto;
import life.inha.icemarket.dto.CommentSaveResDto;
import life.inha.icemarket.respository.CommentRepository;
import life.inha.icemarket.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentSaveResDto createComment(User user, CommentSaveReqDto dto) {

        Post post = postRepository.findById(Long.valueOf(dto.getPostIdx())).orElse(null);
        Comment comment = Comment.createComment(dto, post, user);

        commentRepository.save(comment);

        return new CommentSaveResDto(comment.getId());
    }

    @Transactional(readOnly = true)
    public List<CommentResDto> readCommentList(Integer postIdx, Pageable pageable) {
        // 게시글
        Post post = postRepository.findById(Long.valueOf(postIdx)).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        // 해당 게시글에 달린 댓글 찾기
        List<Comment> commentList = commentRepository.findByPost(post, pageable);

        List<CommentResDto> commentResList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResList.add(CommentResDto.createCommentRes(comment));
        }
        return commentResList;
    }

    @Transactional(readOnly = true)
    public CommentResDto readComment(Integer commentIdx) {
        Comment comment = commentRepository.findById(commentIdx).orElseThrow(() -> new IllegalArgumentException("댓글의 인덱스 번호를 확인해주세요."));
        return CommentResDto.createCommentRes(comment);
    }

    public void deleteComment(Integer id) {
        commentRepository.delete(
                commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. id=" + id))
        );
    }
}
