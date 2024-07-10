package dbp.connect.Security.Auth;

import dbp.connect.Security.Auth.DTOS.AuthJwtResponse;
import dbp.connect.Security.Auth.DTOS.AuthLoginRequest;
import dbp.connect.Security.Auth.DTOS.AuthRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173/")
    public ResponseEntity<AuthJwtResponse> login(@RequestBody @Valid AuthLoginRequest authLoginRequest) {
        return ResponseEntity.ok(authService.login(authLoginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthJwtResponse> register(@RequestBody @Valid AuthRegisterRequest authRegisterRequest) throws Exception {
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
