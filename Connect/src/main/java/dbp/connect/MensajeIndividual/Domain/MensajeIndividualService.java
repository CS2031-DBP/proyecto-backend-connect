package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MensajeIndividualService {
    @Autowired
    private final MensajeIndividualRepository mensajeIndividualRepository;
    private final ChatIndividualService chatIndividualService;
    public MensajeIndividual save(MensajeIndividual chatMessage) {
        var chatId = chatIndividualService
                .getChatIndividualId(chatMessage.getSenderId(),
                        chatMessage.getRecipientId(),
                        true)
                .orElseThrow(); // Hcer mi propia excepcion
        chatMessage.setChatId(chatId);
        mensajeIndividualRepository.save(chatMessage);
        return chatMessage;
    }

    public List<MensajeIndividual> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatIdOptional = chatIndividualService.getChatIndividualId(senderId, recipientId, false);
        return chatIdOptional.map(chatId -> mensajeIndividualRepository.findByChatId(chatId))
                .orElse(new ArrayList<>());
    }

}
