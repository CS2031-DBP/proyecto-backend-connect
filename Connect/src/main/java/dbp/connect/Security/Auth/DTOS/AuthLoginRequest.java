package dbp.connect.Security.Auth.DTOS;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}
