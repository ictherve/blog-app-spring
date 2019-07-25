package io.learning.blogappspring.dataaccess.mapper;

import io.learning.blogappspring.dataaccess.entity.RoleEntity;
import io.learning.blogappspring.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toModel(RoleEntity roleEntity);

    RoleEntity toEntity(Role role);
}
