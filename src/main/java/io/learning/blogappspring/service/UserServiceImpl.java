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

import static io.learning.blogappspring.model.Constant.*;

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
    public User Save(User user) throws Exception {

        if(Objects.isNull(user))
            throw new IllegalArgumentException(USER_CANNOT_BE_NULL);

        if(Objects.nonNull(userRepository.findById(user.getId())))
            throw new Exception(USER_ALREADY_EXISTS);

        UserEntity userEntity = userMapper.toEntity(user);

        userEntity = userRepository.save(userEntity);

        return userMapper.toModel(userEntity);
    }

    @Override
    public User update(User user) throws Exception {

        if(Objects.isNull(user))
            throw new IllegalArgumentException(USER_CANNOT_BE_NULL);

        Optional<UserEntity> optional = userRepository.findById(user.getId());

        if(!optional.isPresent())
            throw new Exception(USER_DOESNT_EXIST);

        User userFromDatabase = userMapper.toModel(optional.get());
        userFromDatabase.setId(user.getId());
        UserEntity userToUpdate = userMapper.toEntity(userFromDatabase);
        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toModel(userToUpdate);
    }

    @Override
    public void delete(Long id) {

        UserEntity userToSave = null;

        if(Objects.nonNull(id))
            userToSave = userRepository.findById(id).get();

        userRepository.delete(userToSave);
    }
}
