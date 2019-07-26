package io.learning.blogappspring.dataaccess.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean isRead;
    private Boolean sentAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, updatable = false, referencedColumnName = "id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false, updatable = false, referencedColumnName = "id")
    private PostEntity post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
