package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.UserRequestDTO;
import com.project.TaskFlow.model.User;

public class UserMapper {

    public static User dtoToEntity(UserRequestDTO requestDTO){
        User user = new User();
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setPassword(requestDTO.password());
        return user;
    }

}
