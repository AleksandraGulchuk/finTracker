package org.nure.fintracker.model.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSetupDto {

    private String name;
    private String email;
    private String password;

}
