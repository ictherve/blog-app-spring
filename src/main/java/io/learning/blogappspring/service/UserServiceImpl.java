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

/**
 * This Class consists of methods for the business Logic of the {@link User} Class. At this moment these methods
 * correspond to the CRUD.
 */

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * This method return a {@link Collection} of users which can be empty or not
     * @return Collection<User>
     */
    @Override
    public Collection<User> findAll() {
        return userRepository.findAll().stream().map(userMapper::toModel).collect(Collectors.toSet());
    }

    /**
     * This method return a {@link User} given an id if it exists or throw an exception if not
     * @param id
     * @return user, the {@link User} corresponding to the id parameter
     * @throws Exception if the user doesn't exists
     */
    @Override
    public User findById(Long id) throws Exception {

        if(Objects.isNull(id))
            throw new IllegalArgumentException(USER_DOESNT_EXIST);

        Optional<UserEntity> userFromDataBase = userRepository.findById(id);

        if(!userFromDataBase.isPresent())
            throw new Exception(USER_NOT_FOUND);

        return userMapper.toModel(userFromDataBase.get());
    }

    /**
     * This method check if
     * <ul>
     *     <li>user is null></li>
     *     <li>username or email is null </li>
     *     <li>username or email already exists</li>
     * </ul>
     * If any of those rules is respected we throw an exception with a message corresponding to the rule.
     * Otherwise we convert the user to an entity({@link UserEntity},  save the entity, retrieve it and convert it to
     * a user and return it
     * @param user the {@link User} to be saved
     * @return the {@link User} saved given the user parameter
     * @throws Exception
     */
    @Override
    public User Save(User user) throws Exception {

        if(Objects.isNull(user))
            throw new IllegalArgumentException(USER_CANNOT_BE_NULL);

        if(Objects.isNull(user.getUsername()) || Objects.isNull(user.getEmail()))
            throw new Exception(USERNAME_OR_EMAIL_CANNOT_BE_NULL);

        Collection<UserEntity> usersFounded = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if(!usersFounded.isEmpty())
            throw new Exception(USER_ALREADY_EXISTS);

        UserEntity userEntity = userMapper.toEntity(user);

        userEntity = userRepository.save(userEntity);

        return userMapper.toModel(userEntity);
    }

    /**
     * This method check if
     * <ul>
     *    <li>user is null></li>
          <li>user do not exists</li>
     *    <li>username or email already exists</li>
     * </ul>
     * If any of those rules is respected we throw an exception with a message corresponding to the rule.
     * @param user the {@link User} to be updated
     * @return a the {@link User} updated
     * @throws Exception if we fall in one of the conditions on the top
     */
    @Override
    public User update(User user) throws Exception {

        if(Objects.isNull(user))
            throw new IllegalArgumentException(USER_CANNOT_BE_NULL);

        if(Objects.isNull(user.getUsername()) || Objects.isNull(user.getEmail()))
            throw new IllegalArgumentException(USERNAME_OR_EMAIL_CANNOT_BE_NULL);

        Optional<UserEntity> optionalOfUserToUpdate = userRepository.findById(user.getId());

        if(!optionalOfUserToUpdate.isPresent())
            throw new Exception(USER_DOESNT_EXIST);

        Collection<UserEntity> usersFounded = userRepository.findByUsernameOrEmailExceptCurrentUser(user.getUsername(), user.getEmail(), user.getId());

        if(!usersFounded.isEmpty())
            throw new Exception((USERNAME_OR_EMAIL_ALREADY_EXISTS));

        // TODO: 2019-07-25 I think it's better to update the user from database rather than saving the object comming from the UserForm
        user.setId(optionalOfUserToUpdate.get().getId());
        UserEntity userToUpdate = userMapper.toEntity(user);
        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toModel(userToUpdate);
    }

    /**
     * This method delete a {@link User} given an existing id.
     * @param id the id of the {@link UserEntity} in the database
     */
    @Override
    public void delete(Long id) {

        UserEntity userToSave = null;

        if(Objects.nonNull(id))
            userToSave = userRepository.findById(id).orElse(null);

        if(Objects.nonNull(userToSave))
            userRepository.delete(userToSave);
    }
}
