package org.nure.fintracker.mapper;

import org.nure.fintracker.dto.user.UserDto;
import org.nure.fintracker.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserAccount userToDto(UserDto userDto){
        return UserAccount.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public UserDto dtoToUser(UserAccount user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserAccount createUserFromDto(UserDto userDto){
        return UserAccount.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
