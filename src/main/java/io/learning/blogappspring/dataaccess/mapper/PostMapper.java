package io.learning.blogappspring.dataaccess.mapper;

import io.learning.blogappspring.dataaccess.entity.PostEntity;
import io.learning.blogappspring.model.Post;
import org.mapstruct.Mapper;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toModel(PostEntity postEntity);

    PostEntity toEntity(Post post);

    Collection<Post> toCollection(Collection<PostEntity> posts);

    Collection<PostEntity> toEntityCollection(Collection<Post> posts);
}
