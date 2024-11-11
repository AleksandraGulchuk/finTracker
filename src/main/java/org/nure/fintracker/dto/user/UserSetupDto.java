package org.nure.fintracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSetupDto {

    @NotBlank(message = "Invalid Name: cannot be empty")
    private String name;
    @Email(message = "Invalid Email")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "Invalid Password: " +
                    "must contains at least 8 characters, " +
                    "one uppercase letter, one lowercase letter, " +
                    "one number and one special character")
    private String password;

}
