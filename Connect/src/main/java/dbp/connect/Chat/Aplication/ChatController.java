package dbp.connect.Chat.Aplication;

import dbp.connect.Chat.DTO.GroupChatRequestDTO;
import dbp.connect.Chat.DTO.SingleChatRequestDTO;
import dbp.connect.Chat.Domain.Chat;
import dbp.connect.Chat.Domain.ChatService;
import dbp.connect.Chat.Exceptions.ChatNotFound;
import dbp.connect.Chat.Exceptions.NotAllowedPermissionChat;
import dbp.connect.User.DTO.UserProfileDTO;
import dbp.connect.User.Domain.UserService;
import dbp.connect.User.Exceptions.BadCredentialException;
import dbp.connect.User.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequestDTO singleChatRequestDTO,
                                           @RequestHeader ("Authorization") String token
    ) throws UserException, BadCredentialException {
        UserProfileDTO user = userService.finddUserProfile(token);
        Chat chat = chatService.createChat(user.getId(), singleChatRequestDTO.getUserId());
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }
    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody GroupChatRequestDTO groupChatRequestDTO,
                                                @RequestHeader ("Authorization") String token
    ) throws Exception {
        UserProfileDTO user = userService.finddUserProfile(token);
        Chat chat = chatService.createChatGroup(user.getId(),groupChatRequestDTO);
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable Long chatId, @RequestHeader (
            "Authorization") String token){
        Chat chat = chatService.getChat(chatId);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatsOfUser(@RequestHeader ("Authorization") String token) throws BadCredentialException, UserException {
        UserProfileDTO user = userService.finddUserProfile(token);
        List<Chat> chats = chatService.findAllChatByUserId(user.getId());
        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addToGroup(@PathVariable Long chatId,
            @PathVariable Long userId,@RequestHeader ("Authorization") String token) throws Exception {
        UserProfileDTO user = userService.finddUserProfile(token);
        Chat chat = chatService.addUserToChat(chatId, chatId, user.getId());
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeFromGroup(@PathVariable Long chatId,
            @PathVariable Long userId,@RequestHeader ("Authorization") String token) throws Exception {
        UserProfileDTO user = userService.finddUserProfile(token);
        Chat chat = chatService.removeFromGroup(chatId, chatId, user.getId());
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId, @RequestHeader("Authorization") String token) throws BadCredentialException, UserException, ChatNotFound, NotAllowedPermissionChat {
        UserProfileDTO user = userService.finddUserProfile(token);
        chatService.deleteChat(chatId, user.getId());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/update/{chatId}/")
    public ResponseEntity<Void> updateChat(@PathVariable Long chatId,@RequestBody String newName, @RequestHeader("Authorization") String token) throws BadCredentialException, UserException, ChatNotFound, NotAllowedPermissionChat {
        UserProfileDTO user = userService.finddUserProfile(token);
        chatService.renameGroup(chatId, newName, user.getId());
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    // Listar todos los chats
    @GetMapping("/all")
    public ResponseEntity<List<Chat>> getAllChats() {
        List<Chat> chats = chatService.findAllChats();
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    // Buscar chats por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Chat>> searchChatsByName(@RequestParam String name) {
        List<Chat> chats = chatService.searchChatsByName(name);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    // Obtener miembros de un chat
    @GetMapping("/{chatId}/members")
    public ResponseEntity<List<UserProfileDTO>> getChatMembers(@PathVariable Long chatId) {
        List<UserProfileDTO> members = chatService.getChatMembers(chatId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // Enviar mensaje a un chat
    @PostMapping("/{chatId}/message")
    public ResponseEntity<Void> sendMessageToChat(@PathVariable Long chatId, @RequestBody String message, @RequestHeader("Authorization") String token) throws UserException {
        UserProfileDTO user = userService.finddUserProfile(token);
        chatService.sendMessageToChat(chatId, user.getId(), message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Obtener mensajes de un chat
    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<String>> getChatMessages(@PathVariable Long chatId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<String> messages = chatService.getChatMessages(chatId, page, size);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
