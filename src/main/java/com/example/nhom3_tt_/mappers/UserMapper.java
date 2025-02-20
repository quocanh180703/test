package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.User.UserDTO;
import com.example.nhom3_tt_.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  User toUser(UserDTO userDTO);

  UserDTO toUserDTO(User user);
}
