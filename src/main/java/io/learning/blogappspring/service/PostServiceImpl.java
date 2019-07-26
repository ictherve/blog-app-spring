package io.learning.blogappspring.service;

import io.learning.blogappspring.dataaccess.entity.PostEntity;
import io.learning.blogappspring.dataaccess.mapper.PostMapper;
import io.learning.blogappspring.dataaccess.repository.PostRepository;
import io.learning.blogappspring.model.Post;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static io.learning.blogappspring.model.Constant.*;

@Service
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostServiceImpl(PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    @Override
    public Collection<Post> findAll() {
        return postMapper.toCollection(postRepository.findAll());
    }

    @Override
    public Post findById(Long id) throws Exception {

        if(Objects.isNull(id))
            throw new IllegalArgumentException(POST_CANNOT_BE_NULL);

        PostEntity postFromDatabase = postRepository.findById(id).orElseThrow(() -> new Exception(POST_DOESNT_EXISTS));

        return postMapper.toModel(postFromDatabase);
    }

    @Override
    public Post save(Post post) throws Exception {

        if(Objects.isNull(post))
            throw new IllegalArgumentException(POST_CANNOT_BE_NULL);

        Optional<PostEntity> optional = postRepository.findById(post.getId());

        if(optional.isPresent())
            throw new Exception(POST_ALREADY__EXISTS);

        PostEntity postToSave = postMapper.toEntity(post);

        postToSave = postRepository.save(postToSave);

        return postMapper.toModel(postToSave);
    }

    @Override
    public Post update(Post post) throws Exception {

        if(Objects.isNull(post))
            throw new IllegalArgumentException(POST_CANNOT_BE_NULL);

        if(Objects.isNull(post.getId()))
            throw new Exception(POST_DOESNT_EXISTS);

        Optional<PostEntity> optional = postRepository.findById(post.getId());

        if(!optional.isPresent())
            throw new Exception(POST_DOESNT_EXISTS);

        PostEntity postToUpdate = optional.get();

        postToUpdate.setTitle(post.getTitle());

        postToUpdate = postRepository.save(postToUpdate);

        return postMapper.toModel(postToUpdate);
    }

    @Override
    public void delete(Long id) {

        PostEntity postToDelete = null;

        if(Objects.nonNull(id))
            postToDelete = postRepository.findById(id).orElse(null);

        if(Objects.nonNull(postToDelete))
            postRepository.delete(postToDelete);
    }
}
