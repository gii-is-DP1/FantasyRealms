package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateProfileDTO {
    
    private String username;

    private String password;

    @Email
    private String email;

    private String avatar;

    private String authority;

    public UserUpdateProfileDTO() {}

    public UserUpdateProfileDTO(String username, String password, String email, String avatar, String authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.authority = authority;
    }


}
