package dbp.connect.Comentarios.Domain;

import dbp.connect.Comentarios.DTOS.ComentarioDto;
import dbp.connect.Comentarios.Infrastructure.ComentarioRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class ComentarioService {
    @Autowired
    private final ComentarioRepository comentarioRepository;
    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }
    public Page<ComentarioDto> getComentario(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        comentarioRepository.findById(id);
        Page<Comentario> =


    }


}
