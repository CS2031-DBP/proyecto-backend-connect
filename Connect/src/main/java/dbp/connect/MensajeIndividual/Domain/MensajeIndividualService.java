package dbp.connect.MensajeIndividual.Domain;

import dbp.connect.ChatIndividual.Domain.ChatIndividualService;
import dbp.connect.ChatIndividual.Infrastructure.ChatIndividualRepository;
import dbp.connect.MensajeIndividual.Infrastructure.MensajeIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MensajeIndividualService {
    @Autowired
    ChatIndividualService chatIndividualService;
    @Autowired
    ChatIndividualRepository chatIndividualRepository;
    @Autowired
    MensajeIndividualRepository mensajeIndividualRepository;


}
