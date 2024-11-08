package org.nure.fintracker.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private String password;

}
