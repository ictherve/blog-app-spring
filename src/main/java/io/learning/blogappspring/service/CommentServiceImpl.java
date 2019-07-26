package io.learning.blogappspring.service;

import io.learning.blogappspring.dataaccess.entity.CommentEntity;
import io.learning.blogappspring.dataaccess.mapper.CommentMapper;
import io.learning.blogappspring.dataaccess.repository.CommentRepository;
import io.learning.blogappspring.model.Comment;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static io.learning.blogappspring.model.Constant.*;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public Collection<Comment> findAll() {
        return commentMapper.toCollection(commentRepository.findAll());
    }

    @Override
    public Comment findById(Long id) throws Exception {

        if(Objects.isNull(id))
            throw new IllegalArgumentException(COMMENT_CANNOT_BE_NULL);

        CommentEntity commentFromDatabase = commentRepository.findById(id).orElseThrow(() -> new Exception(COMMENT_DOESNT_EXISTS));

        return commentMapper.toModel(commentFromDatabase);
    }

    @Override
    public Comment save(Comment comment) throws Exception {

        if(Objects.isNull(comment))
            throw new IllegalArgumentException(COMMENT_CANNOT_BE_NULL);

        if(Objects.isNull(comment.getText()))
            throw new IllegalArgumentException(COMMENT_TEXT_CANNOT_BE_NULL);

        if(Objects.isNull(comment.getAuthor()))
            throw new IllegalArgumentException(AUTHOR_TEXT_CANNOT_BE_NULL);

        CommentEntity commentEntity = null;

        if(Objects.nonNull(comment.getId()))
            commentEntity = commentRepository.findById(comment.getId()).orElse(null);

        if(Objects.nonNull(commentEntity))
            throw new Exception(COMMENT_ALREADY_EXISTS);

        commentEntity = commentMapper.toEntity(comment);

        commentEntity = commentRepository.save(commentEntity);

        return commentMapper.toModel(commentEntity);
    }

    @Override
    public Comment update(Comment comment) throws Exception {

        if(Objects.isNull(comment))
            throw new IllegalArgumentException(COMMENT_CANNOT_BE_NULL);

        if(Objects.isNull(comment.getId()))
            throw new Exception(COMMENT_DOESNT_EXISTS);

        Optional<CommentEntity> optional = commentRepository.findById(comment.getId());

        if(!optional.isPresent())
            throw new Exception(COMMENT_DOESNT_EXISTS);

        CommentEntity commentToUpdate = optional.get();

        commentToUpdate.setText(comment.getText());

        commentToUpdate.setIsRead(true);

        commentToUpdate = commentRepository.save(commentToUpdate);

        return commentMapper.toModel(commentToUpdate);
    }

    @Override
    public void delete(Long id) {

        CommentEntity commentEntity = null;

        if(Objects.nonNull(id));
            commentEntity = commentRepository.findById(id).orElse(null);

        if(Objects.nonNull(commentEntity))
            commentRepository.delete(commentEntity);

    }
}
