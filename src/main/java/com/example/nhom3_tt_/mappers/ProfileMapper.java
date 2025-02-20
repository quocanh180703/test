package com.example.nhom3_tt_.mappers;

import com.example.nhom3_tt_.dtos.User.UserDTO;
import com.example.nhom3_tt_.dtos.requests.profile.EditProfileRequest;
import com.example.nhom3_tt_.dtos.response.profile.EditProfileResponse;
import com.example.nhom3_tt_.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

  User toUser(UserDTO userDTO);

  EditProfileResponse toProfileResponse(User user);

  void updateProfile(@MappingTarget User user, EditProfileRequest editProfileRequest);
}
