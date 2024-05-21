package dbp.connect.PublicacionAlojamiento.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.PublicacionAlojamiento.DTOS.PostPublicacionAlojamientoDTO;
import dbp.connect.PublicacionAlojamiento.DTOS.ResponsePublicacionAlojamiento;
import dbp.connect.PublicacionAlojamiento.Exceptions.PublicacionAlojamientoNotFoundException;
import dbp.connect.PublicacionAlojamiento.Infrastructure.PublicacionAlojamientoRespositorio;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityExistsException;
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
    private AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    private UserRepository userRepository;

    public ResponsePublicacionAlojamiento guardarPublicacionAlojamiento(PostPublicacionAlojamientoDTO publicacionAlojamientoDTO){

        Optional<PublicacionAlojamiento> publicacionAlojamiento = publicacionAlojamientoRepositorio.findById(publicacionAlojamientoDTO.getId());
        if(publicacionAlojamiento.isPresent()) {
            throw new EntityExistsException("La publicacion ya existe");
        }

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
    public ResponsePublicacionAlojamiento getPublicacionId(Long publicacionId) {
        Optional<PublicacionAlojamiento> publicacionOpt = publicacionAlojamientoRepositorio.findById(publicacionId);
        if (publicacionOpt.isPresent()) {
            PublicacionAlojamiento publicacion = publicacionOpt.get();
            return converToDTO(publicacion);
        } else {
            throw new PublicacionAlojamientoNotFoundException("PublicacionAlojamiento not found with id " + publicacionId);
        }
    }

   /* public Page<ResponsePublicacionAlojamiento> getPublicacionRecomendadas(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PublicacionAlojamiento> publicaciones = publicacionAlojamientoRepositorio.findByUserId(u, );
        return publicaciones.map(this::converToDTO);
    }*/ //Mmmmm puede ser encontrar por publicaciones hechas por el autor del alojamiento?
    //tambien implementar un sistema de recomendaciones
    public void actualizarTituloAlojamiento(Long publicacionId, String titulo){
        Optional<PublicacionAlojamiento> p = publicacionAlojamientoRepositorio.findById(publicacionId);
        if(p.isEmpty()) {
            throw new PublicacionAlojamientoNotFoundException("Publicacion no existe");
        }
        PublicacionAlojamiento publicacion = p.get();
        publicacion.setTitulo(titulo);
        publicacionAlojamientoRepositorio.save(publicacion);


    }
    public void eliminarPublicacion(Long publicacionId) {
        Optional<PublicacionAlojamiento> publi = publicacionAlojamientoRepositorio.findById(publicacionId);
        if(publi.isPresent()) {
            publicacionAlojamientoRepositorio.delete(publi.get());
        }
        else{
            throw new PublicacionAlojamientoNotFoundException("Publicacion no existe");
        }
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
