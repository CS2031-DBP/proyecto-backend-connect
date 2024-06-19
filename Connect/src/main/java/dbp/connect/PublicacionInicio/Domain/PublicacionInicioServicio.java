package dbp.connect.PublicacionInicio.Domain;

import dbp.connect.Comentarios.Excepciones.PublicacionNoEncontradoException;
import dbp.connect.Excepciones.NoEncontradoException;
import dbp.connect.PublicacionInicio.DTOS.PostInicioDTO;
import dbp.connect.PublicacionInicio.DTOS.PublicacionInicioResponseDTO;
import dbp.connect.PublicacionInicio.Exceptions.UsuarioNoCoincideId;
import dbp.connect.PublicacionInicio.Infrastructure.PublicacionInicioRepositorio;
import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimedia;
import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimediaServicio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PublicacionInicioServicio {
    @Autowired
    private PublicacionInicioRepositorio publicacionInicioRepositorio;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicacionInicioMultimediaServicio publicacionInicioMultimediaServicio;

    public void createPostInicioDTO(PostInicioDTO postInicioDTO){
        User user = userRepository.findById(postInicioDTO.getAutorPId()).orElseThrow(()->new EntityNotFoundException("El usuario no existe"));
        PublicacionInicio publicacionInicio = new PublicacionInicio();
        publicacionInicio.setAutorP(user);
        publicacionInicio.setCantidadComentarios(0);
        publicacionInicio.setCantidadLikes(0);
        if(!postInicioDTO.getMultimediaList().isEmpty()){
            for(MultipartFile file : postInicioDTO.getMultimediaList()){
                PublicacionInicioMultimedia multimediaInicio = publicacionInicioMultimediaServicio.guardarMultimedia(file);
                publicacionInicio.getPublicacionMultimedia().add(multimediaInicio);
            }
        }
        publicacionInicio.setCuerpo(postInicioDTO.getCuerpo());
        publicacionInicio.setFechaPublicacion(ZonedDateTime.now(ZoneId.systemDefault()));
        publicacionInicioRepositorio.save(publicacionInicio);
        user.getPublicacionInicio().add(publicacionInicio);
        userRepository.save(user);
    }
    public PublicacionInicioResponseDTO obtenerPublciacionesInicio(Long publicacionId){
        PublicacionInicio inicio = publicacionInicioRepositorio.findById(publicacionId).
                orElseThrow(()-> new EntityNotFoundException("Publicacion no encontrada"));

        return converToDto(inicio);
    }
    public void eliminarPublicacionInicio(Long id){
        Optional<PublicacionInicio> inicio = publicacionInicioRepositorio.findById(id);
        if(inicio.isPresent()) {
            publicacionInicioRepositorio.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No existe la publicacion");
        }
    }
    public Page<PublicacionInicioResponseDTO> obtenerPublicacionByUsuario(Long usuarioId, Integer page, Integer size){
        User usuario = userRepository.findById(usuarioId).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
        Pageable pageable = PageRequest.of(page, size);
        Page<PublicacionInicio> publicaciones = publicacionInicioRepositorio.findByAutorP_Id(usuarioId, pageable);
        if(publicaciones.isEmpty()){
            throw new RuntimeException(usuario.getUsername()+ "No tiene publicaciones");
        }
        List<PublicacionInicioResponseDTO> publicacionesInicio = publicaciones.getContent().stream()
                .map(this::converToDto)
                .collect(Collectors.toList());

        while (publicacionesInicio.size() < size && !publicacionesInicio.isEmpty()) {
            PublicacionInicioResponseDTO defaultPublicacion = publicacionesInicio.get(publicacionesInicio.size() - 1);
            publicacionesInicio.add(defaultPublicacion);
        }

        return new PageImpl<>(publicacionesInicio, pageable, publicaciones.getTotalElements());
    }
    public PublicacionInicioResponseDTO actualizarContenido(Long usuarioId, Long publicacionId, String contenido){
        User usuario = userRepository.findById(usuarioId).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
        PublicacionInicio publicacionInicio = publicacionInicioRepositorio.findById(publicacionId).orElseThrow(()->new EntityNotFoundException("Publicacion no encontrada"));
        if(publicacionInicio.getAutorP().getId()!=usuario.getId()){
            throw new UsuarioNoCoincideId("No es el autor de esta publicacion");
        }
        publicacionInicio.setCuerpo(contenido);
        publicacionInicioRepositorio.save(publicacionInicio);
        return converToDto(publicacionInicio);
    }
    public PublicacionInicioResponseDTO actualizarMultimedia(Long usuarioId, Long publicacionId, List<MultipartFile> multimedia) {
        PublicacionInicio publicacion = publicacionInicioRepositorio.findById(publicacionId)
                .orElseThrow(() -> new PublicacionNoEncontradoException("Publicacion no encontrada con id: " + publicacionId));

        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new NoEncontradoException("Usuario no encontrado con id: " + usuarioId));

        if (!publicacion.getAutorP().getId().equals(usuarioId)) {
            throw new UsuarioNoCoincideId("Usuario no autorizado para modificar esta publicacion");
        }
        List<PublicacionInicioMultimedia> nuevasMultimedia = publicacionInicioMultimediaServicio.guardarMultimedia(multimedia);
        publicacion.setPublicacionMultimedia(nuevasMultimedia);

        publicacionInicioRepositorio.save(publicacion);

        return converToDto(publicacion);
    }

    private PublicacionInicioResponseDTO converToDto(PublicacionInicio inicio){
        PublicacionInicioResponseDTO dto = new PublicacionInicioResponseDTO();
        dto.setCantidadComentarios(inicio.getCantidadComentarios());
        dto.setCantidadLikes(inicio.getCantidadLikes());
        dto.setUserFullName(inicio.getAutorP().getPrimerNombre() + " " + inicio.getAutorP().getSegundoNombre()
                + " "+ inicio.getAutorP().getPrimerApellido() + " " + inicio.getAutorP().getSegundoApellido());
        dto.setContenido(inicio.getCuerpo());
        for(PublicacionInicioMultimedia multimedia: inicio.getPublicacionMultimedia()){
            dto.getMultimedia().add(multimedia.getContenido());
        }
        if(inicio.getAutorP().getFoto() != null){
            dto.setFotPerfil(inicio.getAutorP().getFoto());
        }
        else{
            dto.setFotPerfil(null);
        }
        return dto;
    }
}
