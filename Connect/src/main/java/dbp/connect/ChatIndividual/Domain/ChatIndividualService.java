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

}