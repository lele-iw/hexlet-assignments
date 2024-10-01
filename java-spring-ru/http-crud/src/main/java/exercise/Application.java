package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts")
    public List<Post> showAllPosts(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit) {
        if (limit != null) {
            return posts.stream()
                    .skip(limit * (page - 1))
                    .limit(limit)
                    .toList();
        } else {
            return posts.stream().toList();
        }
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> showPostById(@PathVariable String id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/posts/{id}")
    public Post editPost(@PathVariable String id, @RequestBody Post post) {
        var postBeforeEdit = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (postBeforeEdit.isPresent()) {
            Post postForEdit = postBeforeEdit.get();
            postForEdit.setTitle(post.getTitle());
            postForEdit.setBody(post.getBody());
            postForEdit.setId(post.getId());
        }

        return post;
    }

    @DeleteMapping("/posts/{id}") // Удаление страницы
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
