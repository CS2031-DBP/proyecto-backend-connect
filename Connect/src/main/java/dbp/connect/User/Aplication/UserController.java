package dbp.connect.User.Aplication;

import com.amazonaws.services.opsworks.model.UserProfile;
import dbp.connect.User.DTO.UpdateUserNameAndProfileDTO;
import dbp.connect.User.DTO.UserProfileDTO;
import dbp.connect.User.DTO.UserSearchDTO;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Domain.UserService;
import dbp.connect.User.Exceptions.BadCredentialException;
import dbp.connect.User.Exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private  UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@RequestHeader("Authorization") String token) throws BadCredentialException, UserException {
        UserProfileDTO userProfileDTO = userService.finddUserProfile(token);
        return new ResponseEntity<UserProfileDTO>(userProfileDTO, HttpStatus.ACCEPTED);
    }
    @GetMapping("{query}")
    public ResponseEntity<List<UserSearchDTO>> searchUser(@PathVariable  String query) {
        List<UserSearchDTO> userSearchDTOList = userService.searchUser(query);
        return new ResponseEntity<List<UserSearchDTO>>(userSearchDTOList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateProfile(@RequestHeader("Authorization") String token, @RequestBody UpdateUserNameAndProfileDTO update) throws Exception {
        UserProfileDTO userProfDTO = userService.finddUserProfile(token);
        userService.UpdateUser(userProfDTO.getId(), update);
        return ResponseEntity.accepted().build();
    }

}
