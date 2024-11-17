package org.nure.fintracker.mapper;

import org.nure.fintracker.model.dto.user.UserSetupDto;
import org.nure.fintracker.model.entity.UserAccount;
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
