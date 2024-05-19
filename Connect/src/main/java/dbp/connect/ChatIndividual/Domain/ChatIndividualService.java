package dbp.connect.ChatIndividual.Domain;

import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeIndividual.Domain.MensajeIndividual;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatIndividualService {
    @Autowired
    private final ChatIndividualRepository chatIndividualRepository;
    @Autowired
    private UserService userService;

    public ChatIndividualService(ChatIndividualRepository chatIndividualRepository) {
        this.chatIndividualRepository = chatIndividualRepository;
    }

    public ChatIndividual crearChat(Long usuario1, Long usuario2) {
        ChatIndividual existingChat = chatIndividualRepository.findByUsuarios(usuario1, usuario2);
        if (existingChat != null) {
            return existingChat;
        }
        User user1 = userService.getUserById(usuario1)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario1));

        User user2 = userService.getUserById(usuario2)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuario2));
        ChatIndividual chat = new ChatIndividual();
        chat.setUsuario1(user1);
        chat.setUsuario2(user2);
        return chatIndividualRepository.save(chat);
    }

    public void eliminarChat(Long id) {
        if (chatIndividualRepository.existsById(id)) {

            chatIndividualRepository.deleteById(id);
        } else {

            throw new RuntimeException("Chat individual no encontrado con el ID: " + id);
        }
    }
    public ChatIndividual obtenerChatPorId(Long id, Long autor) {

        return chatIndividualRepository.findById(id).orElse(null);
    }

    public ChatIndividual guardarChat(ChatIndividual chat) {
        return chatIndividualRepository.save(chat);
    }
    public List<MensajeIndividual> obtenerMensajesDeChatPaginados(Long chatId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<MensajeIndividual> page = chatIndividualRepository.findAllMensajesByChatId(chatId, pageRequest);
        return page.getContent();
    }

}