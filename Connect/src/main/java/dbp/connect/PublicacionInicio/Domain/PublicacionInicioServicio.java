package dbp.connect.PublicacionInicio.Domain;

import dbp.connect.PublicacionInicio.DTOS.PostInicioDTO;
import dbp.connect.PublicacionInicio.Infrastructure.PublicacionInicioRepositorio;
import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimedia;
import dbp.connect.PublicacionInicioMultimedia.Domain.PublicacionInicioMultimediaServicio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;


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
        publicacionInicio.setAutor(user);
        publicacionInicio.setCantidadComentarios(0);
        publicacionInicio.setCantidadLikes(0);
        if(!postInicioDTO.getMultimediaList().isEmpty()){
            for(MultipartFile file : postInicioDTO.getMultimediaList()){
                publicacionInicioMultimediaServicio.guardarMultimedia(file);
            }
        }
        publicacionInicio.setCuerpo(postInicioDTO.getCuerpo());
        publicacionInicio.setFechaPublicacion(ZonedDateTime.now(ZoneId.systemDefault()));
    }

}
