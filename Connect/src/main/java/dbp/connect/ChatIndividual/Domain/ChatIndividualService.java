package dbp.connect.ChatIndividual.Domain;

import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatIndividualService {
    @Autowired
    private final ChatIndividualRepository chatIndividualRepository;
    public Optional<String> getChatIndividualId(String senderId, String recipientId,
                                                     boolean createNewChatRoomIfNotExists) {
        return chatIndividualRepository.findBySenderIdAndRecipientId(senderId, recipientId).
        map(ChatIndividual::getChatId)
                .or(() -> {
                        if (createNewChatRoomIfNotExists){
                            String chatId = createChat(senderId, recipientId);
                            return Optional.of(chatId);
                        }
                        return Optional.empty();
                });
    }
    private String createChat(String senderId, String recipientId) {
        String chatId = String.format("%s_%s", senderId, recipientId);
        ChatIndividual senderRecipient = ChatIndividual.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatIndividual recipientSender = ChatIndividual.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        chatIndividualRepository.save(senderRecipient);
        chatIndividualRepository.save(recipientSender);
        return chatId;
    }
}