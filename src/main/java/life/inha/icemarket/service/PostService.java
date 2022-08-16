package life.inha.icemarket.service;

import life.inha.icemarket.domain.Category;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.PostDto;
import life.inha.icemarket.dto.PostSaveDto;
import life.inha.icemarket.exception.UserNotFoundException;
import life.inha.icemarket.respository.CategoryRepository;
import life.inha.icemarket.respository.PostRepository;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public String save(User user, String category, PostSaveDto postSaveDto) {
        Category categoryEntity = categoryRepository.findByName(category);

        Long postId = postRepository.save(postSaveDto.toEntity(categoryEntity, user)).getId();

        String result = "게시글 등록이 완료되었습니다. postId = " + postId;
        return result;
    }

    public Page<Post> getPosts(String category, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByCategory(categoryRepository.findByName(category), pageable);

        return posts;
    }

    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow();
        PostDto postDto = PostDto.getPost(post);

        return postDto;
    }

    public String updatePost(String category, Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.update(post.getTitle(), post.getContent());

        return "게시글 수정이 완료되었습니다.";
    }

    public String deletePost(String category, Long id) {
        postRepository.deleteById(id);

        return "게시글 삭제가 완료되었습니다.";
    }



}
