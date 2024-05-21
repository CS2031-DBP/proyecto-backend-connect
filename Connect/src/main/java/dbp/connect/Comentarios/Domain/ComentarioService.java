package dbp.connect.Comentarios.Domain;

import dbp.connect.Comentarios.DTOS.CambioContenidoDTO;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
            comentario.setDate(LocalDateTime.now(ZoneId.systemDefault()));
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
                comentario.setDate(LocalDateTime.now(ZoneId.systemDefault()));

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
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionId);
        if (publicacionInicio.isEmpty()) {
            throw new PublicacionNoEncontradoException("No se encontraron respuestas para este comentario");
        }
        Optional<Comentario> comentarioPadreOptional = comentarioRepository.findById(parentId);
        if (comentarioPadreOptional.isEmpty()) {
            throw new ComentarioNoEncontradoException("Comentario padre no encontrado con ID: " + parentId);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> respuestasPage = comentarioRepository.findByParentId(parentId, pageable);


        List<ComentarioRespuestaDTO> respuestasDTOs = respuestasPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        while (respuestasDTOs.size() < size && !respuestasDTOs.isEmpty()) {
            ComentarioRespuestaDTO defaultRespuesta = respuestasDTOs.get(respuestasDTOs.size() - 1);
            respuestasDTOs.add(defaultRespuesta);
        }
        return new PageImpl<>(respuestasDTOs, pageable, respuestasPage.getTotalElements());
    }

    public void deleteComentarioById(Long publicacionID,Long comentarioId){
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionID);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publicacion = publicacionInicio.get();
            Optional<Comentario> comentario = comentarioRepository.findById(comentarioId);
            if (comentario.isPresent()) {
                Comentario comentarioInicio = comentario.get();
                publicacion.getComentarios().remove(comentarioInicio);
                comentarioRepository.deleteById(comentarioInicio.getId());
                publicacionInicioRepositorio.save(publicacion);
            }
            else{
                throw new ComentarioNoEncontradoException("Comentario no encontrado");
            }
        }
        else{
            throw new PublicacionNoEncontradoException("Publicacion no encontrado");
        }

    }

    public void deleteComentarioRespuestaById(Long publicacionID,Long parentId,Long comentarioId){
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionID);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publicacion = publicacionInicio.get();
            Optional<Comentario> comentarioPadreOptional = publicacion.getComentarios().stream()
                    .filter(comentario -> comentario.getId().equals(parentId))
                    .findFirst();

            if (comentarioPadreOptional.isPresent()) {
                Comentario comentarioPadre = comentarioPadreOptional.get();

                Optional<Comentario> respuestaOptional = comentarioPadre.getReplies().stream()
                        .filter(respuesta -> respuesta.getId().equals(comentarioId))
                        .findFirst();
                if (respuestaOptional.isPresent()) {
                    Comentario respuesta = respuestaOptional.get();
                    comentarioPadre.getReplies().remove(respuesta);
                    comentarioRepository.deleteById(respuesta.getId());
                    publicacionInicioRepositorio.save(publicacion);
                    comentarioRepository.save(comentarioPadre);
                } else {
                    throw new ComentarioNoEncontradoException("Respuesta de comentario no encontrado");
                }
            } else {
                throw new ComentarioNoEncontradoException("Comentario no encontrado");
            }
        }
        else{
            throw new PublicacionNoEncontradoException("Publicacion no encontrado");
        }
    }

    public ComentarioRespuestaDTO actualizarComentario(Long publicacionId, Long comentarioId,
                                                       CambioContenidoDTO cambioContenidoDTO){
        Optional<PublicacionInicio> publicacionInicio= publicacionInicioRepositorio.findById(publicacionId);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publicacion = publicacionInicio.get();
            Optional<Comentario> comentario = comentarioRepository.findById(comentarioId);
            if (!comentario.isPresent()) {
                throw new ComentarioNoEncontradoException("Comentario no encontrado");
            }
            Comentario comentarioInicio = comentario.get();
            comentarioInicio.setMessage(cambioContenidoDTO.getContenido());
            comentarioRepository.save(comentarioInicio);
            return convertToDto(comentarioInicio);
        }
        else{
            throw new PublicacionNoEncontradoException("Publicacion no encontrado");
        }
    }

    public ComentarioRespuestaDTO actualizarContenidoDeComentarioRespuesta(Long publicacionId, Long parentID,Long
            comentarioId,CambioContenidoDTO cambioContenidoDTO){
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionId);
        if (publicacionInicio.isEmpty()) {
            throw new PublicacionNoEncontradoException("Publicacion no encontrado");
        }
        Optional<Comentario> comentarioP = comentarioRepository.findById(parentID);
        if (comentarioP.isEmpty()) {
            throw new ComentarioNoEncontradoException("Comentario no encontrado");
        }
        PublicacionInicio publicacion = publicacionInicio.get();
        Comentario comentarioPadre = comentarioP.get();
        Optional<Comentario> comentarioHijoOptional = comentarioPadre.getReplies().stream()
                .filter(comentario -> comentario.getId().equals(comentarioId))
                .findFirst();
        if (comentarioHijoOptional.isPresent()) {
            Comentario comentarioHijo = comentarioHijoOptional.get();
            comentarioHijo.setMessage(cambioContenidoDTO.getContenido());
            comentarioRepository.save(comentarioHijo);

            return convertToDto(comentarioHijo);
        } else {
            throw new ComentarioNoEncontradoException("Comentario hijo no encontrado con ID: " + comentarioId);
        }
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
        dto.setFechaCreacion(comentario.getDate());
        if (comentario.getMultimedia() != null) {
            dto.setMulimedia(comentario.getMultimedia().getContenido());
        } else {
            dto.setMulimedia(null);
        }

        return dto;
    }

    @Transactional
    public void actualizarComentariolikes(Long publicacionId,Long comentarioId){
        PublicacionInicio publicacionInicio = publicacionInicioRepositorio.findById(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradoException("Publicación no encontrada"));

        Comentario comentarioInicio = publicacionInicio.getComentarios().stream()
                .filter(comentario -> comentario.getId().equals(comentarioId))
                .findFirst()
                .orElseThrow(() -> new ComentarioNoEncontradoException("Comentario no encontrado con ID: " + comentarioId));
        if (!comentarioInicio.getPublicacion().getId().equals(publicacionId)) {
            throw new ComentarioNoEncontradoException("El comentario no pertenece a la publicación especificada");
        }
        comentarioInicio.setLikes(comentarioInicio.getLikes()+1);
        comentarioRepository.save(comentarioInicio);
    }

    @Transactional
    public void actualizarContenidoDeComentarioRespuestaLikes(Long publicacionId,Long parentID,Long comentarioId){
        PublicacionInicio publicacionInicio = publicacionInicioRepositorio.findById(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradoException("Publicación no encontrada"));

        Comentario comentarioPadre = publicacionInicio.getComentarios().stream()
                .filter(comentario -> comentario.getId().equals(parentID))
                .findFirst()
                .orElseThrow(() -> new ComentarioNoEncontradoException("Comentario no encontrado con ID: " + parentID));

        if (!comentarioPadre.getPublicacion().getId().equals(publicacionId)) {
            throw new ComentarioNoEncontradoException("El comentario no pertenece a la publicación especificada");
        }
        Comentario comentarioHijo = comentarioPadre.getReplies().stream()
                .filter(comentario -> comentario.getId().equals(comentarioId))
                .findFirst()
                .orElseThrow(() -> new ComentarioNoEncontradoException("Respuesta no encontrado con ID: " + comentarioId));

        comentarioHijo.setLikes(comentarioHijo.getLikes()+1);
        comentarioRepository.save(comentarioHijo);
    }
}
