package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts.stream().toList());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostsById(@PathVariable String id) {
        Optional<Post> post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        return ResponseEntity.of(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> editPost(@PathVariable String id, @RequestBody Post post) {
        var postBeforeEdit = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (postBeforeEdit.isPresent()) {
            Post postForEdit = postBeforeEdit.get();
            postForEdit.setTitle(post.getTitle());
            postForEdit.setBody(post.getBody());
            postForEdit.setId(post.getId());

            return ResponseEntity.status(200).body(post);
        } else {
            return ResponseEntity.status(204).body(null);
        }
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
