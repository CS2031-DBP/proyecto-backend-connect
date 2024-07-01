package dbp.connect.ChatGrupal.Aplication;

import dbp.connect.ChatGrupal.Domain.ChatGrupalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/chatGrupal")
public class ChatGrupalController {
    @Autowired
    private ChatGrupalServicio chatGrupalServicio;
    //TODO
}
