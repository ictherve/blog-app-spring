package io.learning.blogappspring.service;

import io.learning.blogappspring.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    User findById(Long id) throws Exception;

    User Save(User user);

    User update(User user);

    User delete(User user);

}
