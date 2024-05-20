package dbp.connect.Comentarios.Domain;

import dbp.connect.Comentarios.DTOS.ComentarioDto;
import dbp.connect.Comentarios.DTOS.ComentarioRespuestaDTO;
import dbp.connect.Comentarios.Excepciones.ComentarioNoEncontradoException;
import dbp.connect.Comentarios.Excepciones.PublicacionNoEncontradoException;
import dbp.connect.Comentarios.Infrastructure.ComentarioRepository;
import dbp.connect.ComentariosMultimedia.Domain.ComentarioMultimediaServicio;
import dbp.connect.Excepciones.RecursoNoEncontradoException;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Infrastructure.PublicacionInicioRepositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicacionInicioRepositorio publicacionInicioRepositorio;
    @Autowired
    private ComentarioMultimediaServicio comentarioMultimediaServicio;

    public Comentario createNewComentario(Long publicacionID, ComentarioDto comentarioDTO) {
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.
                findById(publicacionID);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publicacion = publicacionInicio.get();
            Comentario comentario = new Comentario();
            comentario.setMessage(comentarioDTO.getMessage());

            User autor = userRepository.findById(comentarioDTO.getAutorId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + comentarioDTO.getAutorId()));
            comentario.setAutor(autor);
            comentario.setPublicacion(publicacion);
            if (comentarioDTO.getMultimedia() != null && !comentarioDTO.getMultimedia().isEmpty()) {
                comentarioMultimediaServicio.saveMultimedia(comentario, comentarioDTO.getMultimedia());
            } else {
                throw new RecursoNoEncontradoException("Multimedia no encontrada");
            }
            comentario.setLikes(0);
            comentarioRepository.save(comentario);
            return comentario;
        } else {
            throw new PublicacionNoEncontradoException("Publicacion no encontrada");
        }

    }

    public Comentario createNewComentarioHijo(Long publicacionID, Long parentId, ComentarioDto comentarioDTO) {
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionID);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publicacion = publicacionInicio.get();
            Comentario comentario = new Comentario();
            comentario.setPublicacion(publicacion);
            Optional<Comentario> parentComentario = comentarioRepository.findById(parentId);
            if (parentComentario.isPresent()) {
                Comentario parentComentarioParent = parentComentario.get();
                comentario.setMessage(comentarioDTO.getMessage());

                User autor = userRepository.findById(comentarioDTO.getAutorId())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + comentarioDTO.getAutorId()));
                comentario.setAutor(autor);
                comentario.setLikes(0);

                if (comentarioDTO.getMultimedia() != null && !comentarioDTO.getMultimedia().isEmpty()) {
                    comentarioMultimediaServicio.saveMultimedia(comentario, comentarioDTO.getMultimedia());
                }
                comentario.setParentId(parentComentarioParent.getId());
                parentComentarioParent.addCommentReplies(comentario);
                comentarioRepository.save(parentComentarioParent);
                comentarioRepository.save(comentario);
                return comentario;
            } else {
                throw new ComentarioNoEncontradoException("Comentario no encontrado");
            }
        }
        else{
            throw new PublicacionNoEncontradoException("Publicacion no encontrada");
        }
    }
    public Page<ComentarioRespuestaDTO> getComentario(Long publicacionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId, pageable);

        if (comentarios.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron comentario s para esta publicación");
        }

        List<ComentarioRespuestaDTO> comentariosContent = comentarios.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        while (comentariosContent.size() < size && !comentariosContent.isEmpty()) {
            ComentarioRespuestaDTO defaultComentario = comentariosContent.get(comentariosContent.size() - 1);
            comentariosContent.add(defaultComentario);
        }

        return new PageImpl<>(comentariosContent, pageable, comentarios.getTotalElements());
    }

    public Page<ComentarioRespuestaDTO> getResponseComentarios(Long publicacionId, Long parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> respuestas = comentarioRepository.findByPublicacionIdAndParentId(publicacionId, parentId, pageable);

        if (respuestas.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron respuestas para este comentario");
        }

        List<ComentarioRespuestaDTO> respuestasContent = respuestas.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        while (respuestasContent.size() < size && !respuestasContent.isEmpty()) {
            ComentarioRespuestaDTO defaultRespuesta = respuestasContent.get(respuestasContent.size() - 1);
            respuestasContent.add(defaultRespuesta);
        }
        return new PageImpl<>(respuestasContent, pageable, respuestas.getTotalElements());
    }

    private ComentarioRespuestaDTO convertToDto(Comentario comentario) {
        ComentarioRespuestaDTO dto = new ComentarioRespuestaDTO();
        dto.setAutorNombreCompleto(comentario.getAutor().getFullname());
        dto.setMessage(comentario.getMessage());

        if (comentario.getAutor().getFoto() != null) {
            dto.setAutorImagen(comentario.getAutor().getFoto());
        } else {
            dto.setAutorImagen(null);
        }

        dto.setLikes(comentario.getLikes());

        if (comentario.getMultimedia() != null) {
            dto.setMulimedia(comentario.getMultimedia().getContenido());
        } else {
            dto.setMulimedia(null);
        }

        return dto;
    }
    public void deleteComentarioById(Long publicacionID,Long comentarioId){
        Comentario comentario = comentarioRepository.findByIdAndPublicacionId(comentarioId, publicacionID)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado para la publicación dada"));

        comentarioRepository.delete(comentario);
    }

}
