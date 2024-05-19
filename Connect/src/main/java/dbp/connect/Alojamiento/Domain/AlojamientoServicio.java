package dbp.connect.Alojamiento.Domain;

import dbp.connect.Alojamiento.DTOS.AlojamientoRequest;
import dbp.connect.Alojamiento.DTOS.ContenidoDTO;
import dbp.connect.Alojamiento.Excepciones.DescripcionIgualException;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;

import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimediaServicio;
import dbp.connect.Excepciones.RecursoNoEncontradoException;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Optional;

@Service
public class AlojamientoServicio {
    @Autowired
    AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    AlojamientoMultimediaServicio alojamientoMultimediaServicio;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Alojamiento guardarAlojamiento(AlojamientoRequest alojamiento) {
        Alojamiento alojamientoAux = new Alojamiento();
        if(alojamiento.getId()==null || alojamiento.getDescripcion()==null ||
        alojamiento.getLongitude()==null || alojamiento.getLatitude()==null  ){
            throw new IllegalArgumentException("Los argumentos no deben ser nulos");
        }
        User currentPropietario = userRepository.findById(alojamiento.getPropietarioId()).
                orElseThrow(()-> new RuntimeException("Propietario no encontrado"));
        alojamientoAux.setId(alojamiento.getId());
        alojamientoAux.setPropietario(currentPropietario);
        alojamientoAux.setFechaPublicacion(LocalDateTime.now(ZoneId.systemDefault()));
        alojamientoAux.setDescripcion(alojamiento.getDescripcion());
        alojamientoAux.setLongitude(alojamiento.getLongitude());
        alojamientoAux.setLatitude(alojamiento.getLatitude());
        alojamientoAux.setEstado(Estado.DISPONIBLE);
        alojamientoAux.setPrecio(alojamiento.getPrecio());
        for (MultipartFile archivo : alojamiento.getMultimedia()) {
            alojamientoMultimediaServicio.guardarArchivo(archivo, alojamientoAux.getId());
        }
        alojamientoRepositorio.save(alojamientoAux);
        return alojamientoAux;
    }
    public Alojamiento obtenerAlojamiento(Long alojamientoId) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            return alojamientoOptional.get();
        } else {
            throw new RecursoNoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }
    public void eliminarById(Long alojamientoId) {
        if (alojamientoRepositorio.existsById(alojamientoId)) {
            alojamientoRepositorio.deleteById(alojamientoId);
        } else {
            throw new RecursoNoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }
    public void modificarPrecio(Long alojamientoId, Double precio) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            alojamiento.setPrecio(precio);
            alojamientoRepositorio.save(alojamiento);
        } else {
            throw new RecursoNoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }
    public void actualizarEstadoAlojamiento(Long alojamientoId) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            alojamiento.setEstado(Estado.NODISPONIBLE);
            alojamientoRepositorio.save(alojamiento);
        } else {
            throw new RecursoNoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }
    public void actualizarDescripcionAlojamiento(Long alojamientoId, ContenidoDTO contenidoDTO) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            if (alojamiento.getDescripcion().equals(contenidoDTO.getDescripcion())) {
                throw new DescripcionIgualException("La descripcion debe de ser diferente a la proporcionada anteriormente.");
            } else {
                alojamiento.setDescripcion(contenidoDTO.getDescripcion());
                alojamientoRepositorio.save(alojamiento);
            }
        } else {
            throw new RecursoNoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

}
