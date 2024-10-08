package dbp.connect.Security.Auth;

import dbp.connect.Security.Auth.DTOS.AuthJwtResponse;
import dbp.connect.Security.Auth.DTOS.AuthLoginRequest;
import dbp.connect.Security.Auth.DTOS.AuthRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthJwtResponse> login(@RequestBody AuthLoginRequest authLoginRequest) {
        return ResponseEntity.ok(authService.login(authLoginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthJwtResponse> register(@RequestBody AuthRegisterRequest authRegisterRequest) throws Exception {
        return ResponseEntity.ok(authService.register(authRegisterRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authService.logout(authorizationHeader)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Authorization header missing or invalid");
        }
    }
}
