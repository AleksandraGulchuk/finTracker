package org.nure.fintracker.mapper;

import org.nure.fintracker.dto.user.UserSetupDto;
import org.nure.fintracker.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserAccount createUserFromDto(UserSetupDto userSetupDto){
        return UserAccount.builder()
                .name(userSetupDto.getName())
                .email(userSetupDto.getEmail())
                .password(userSetupDto.getPassword())
                .build();
    }
}
