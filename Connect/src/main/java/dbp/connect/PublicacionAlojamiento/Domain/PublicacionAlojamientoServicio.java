package dbp.connect.PublicacionAlojamiento.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.AlojamientoMultimedia.Infrastructure.AlojamientoMultimediaRepositorio;
import dbp.connect.PublicacionAlojamiento.DTOS.PostPublicacionAlojamientoDTO;
import dbp.connect.PublicacionAlojamiento.DTOS.ResponsePublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Infrastructure.PublicacionAlojamientoRespositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class PublicacionAlojamientoServicio {
    @Autowired
    private PublicacionAlojamientoRespositorio publicacionAlojamientoRepositorio;
    @Autowired
    private AlojamientoMultimediaRepositorio alojamientoMultimediaRepositorio;
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    private UserRepository userRepository;

    public ResponsePublicacionAlojamiento guardarPublicacionAlojamiento(PostPublicacionAlojamientoDTO publicacionAlojamientoDTO){
        // Verificar si la publicación de alojamiento ya existe
        Optional<PublicacionAlojamiento> publicacionAlojamiento = publicacionAlojamientoRepositorio.findById(publicacionAlojamientoDTO.getId());
        if(publicacionAlojamiento.isPresent()) {
            throw new EntityExistsException("La publicacion ya existe");
        }

        // Verificar si el alojamiento existe
        Optional<Alojamiento> alojamiento = alojamientoRepositorio.findById(publicacionAlojamientoDTO.getAlojamientoId());
        if(alojamiento.isEmpty()) {
            throw new EntityExistsException("El alojamiento no existe");
        }
        Alojamiento alojamientoResponse = alojamientoRepositorio.save(alojamiento.get());
        PublicacionAlojamiento nuevaPublicacion = new PublicacionAlojamiento();

        nuevaPublicacion.setAlojamiento(alojamientoResponse);
        nuevaPublicacion.setId(alojamientoResponse.getId());
        nuevaPublicacion.setFecha(ZonedDateTime.now(ZoneId.systemDefault()));
        nuevaPublicacion.setCantidadReseñas(0);
        nuevaPublicacion.setTitulo(publicacionAlojamientoDTO.getTitulo());
        nuevaPublicacion.setPromedioRating(0.0);
        publicacionAlojamientoRepositorio.save(nuevaPublicacion);
        PublicacionAlojamiento createdPublicacionAlojamiento = publicacionAlojamientoRepositorio.save(nuevaPublicacion);

        ResponsePublicacionAlojamiento response = converToDTO(createdPublicacionAlojamiento);

        return response;


    }
    private ResponsePublicacionAlojamiento converToDTO(PublicacionAlojamiento publicacionAlojamiento){
        ResponsePublicacionAlojamiento response = new ResponsePublicacionAlojamiento();
        response.setId(publicacionAlojamiento.getId());
        response.setTitulo(publicacionAlojamiento.getTitulo());
        response.setDescripcion(publicacionAlojamiento.getAlojamiento().getDescripcion());
        response.setLatitue(publicacionAlojamiento.getAlojamiento().getLatitude());
        response.setLongitud(publicacionAlojamiento.getAlojamiento().getLongitude());
        response.setCantidadReviews(publicacionAlojamiento.getCantidadReseñas());
        response.setPromedioRating(publicacionAlojamiento.getPromedioRating());
        response.setAutorFullName(publicacionAlojamiento.getAlojamiento().getPropietario().getFullname());
        response.setFechaPublicacion(publicacionAlojamiento.getFecha());
        if (publicacionAlojamiento.getAlojamiento().getPropietario().getFoto() != null) {
            response.setAutorPhoto(publicacionAlojamiento.getAlojamiento().getPropietario().getFoto());
        } else {
            response.setAutorPhoto(null);
        }
        if (publicacionAlojamiento.getAlojamiento().getAlojamientoMultimedia() != null) {
            response.setAlojamientoMultimedia(publicacionAlojamiento.getAlojamiento().getAlojamientoMultimedia());
        } else {
            response.setAlojamientoMultimedia(null);
        }
        return response;
    }

}
