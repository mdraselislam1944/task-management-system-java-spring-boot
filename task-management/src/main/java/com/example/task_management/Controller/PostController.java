package com.example.task_management.Controller;

import com.example.task_management.Model.PostModel;
import com.example.task_management.Model.UserModel;
import com.example.task_management.Service.PostService;
import com.example.task_management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/{userId}/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<PostModel> getAllPostsForUser(@PathVariable Long userId) {
        return postService.getAllPostsForUser(userId);
    }

    @PostMapping
    public ResponseEntity<PostModel> createPost(@PathVariable Long userId, @RequestBody PostModel post) {
        Optional<UserModel> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        post.setUser(user.get());
        PostModel createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }
}
