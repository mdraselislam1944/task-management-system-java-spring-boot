package com.example.task_management.Service;

import com.example.task_management.Model.PostModel;
import com.example.task_management.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostModel> getAllPostsForUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public PostModel createPost(PostModel post) {
        return postRepository.save(post);
    }
}
