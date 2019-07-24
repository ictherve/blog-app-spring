package io.learning.blogappspring.service;

import io.learning.blogappspring.dataaccess.entity.UserEntity;
import io.learning.blogappspring.dataaccess.mapper.UserMapper;
import io.learning.blogappspring.dataaccess.repository.UserRepository;
import io.learning.blogappspring.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.learning.blogappspring.model.Constant.USER_DOESNT_EXIST;
import static io.learning.blogappspring.model.Constant.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll().stream().map(userMapper::toModel).collect(Collectors.toSet());
    }

    @Override
    public User findById(Long id) throws Exception {

        if(Objects.isNull(id))
            throw new IllegalArgumentException(USER_DOESNT_EXIST);

        Optional<UserEntity> userFromDataBase = userRepository.findById(id);

        if(!userFromDataBase.isPresent())
            throw new Exception(USER_NOT_FOUND);

        return userMapper.toModel(userFromDataBase.get());
    }

    @Override
    public User Save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }
}
