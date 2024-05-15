package dbp.connect.Comentarios.Domain;

import dbp.connect.Comentarios.Infrastructure.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
    @Autowired
    private final ComentarioRepository comentarioRepository;



}
