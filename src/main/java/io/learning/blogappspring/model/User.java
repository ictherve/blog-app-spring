package io.learning.blogappspring.model;

import lombok.Data;

import java.util.Objects;

@Data
public class User {

    private Long Id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String birthDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Id, user.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
