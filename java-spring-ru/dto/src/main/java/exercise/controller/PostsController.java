package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> getPosts() {

        List<PostDTO> postDTOList = new ArrayList<>();
        postRepository.findAll()
                .forEach(p -> postDTOList.add(toPostDto(p)));

        return postDTOList;
    }

    @GetMapping("/{id}")
    public PostDTO getPosts(@PathVariable long id) {

        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return toPostDto(post);
    }

    private PostDTO toPostDto(Post post) {
        var postDto = new PostDTO();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        postDto.setComments(toCommentListDto(post.getId()));

        return postDto;
    }

    private CommentDTO toCommentDto(Comment comment) {
        var commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    private List<CommentDTO> toCommentListDto(long postId) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentRepository.findByPostId(postId)
                .forEach(c -> commentDTOList.add(toCommentDto(c)));

        return commentDTOList;
    }
}
