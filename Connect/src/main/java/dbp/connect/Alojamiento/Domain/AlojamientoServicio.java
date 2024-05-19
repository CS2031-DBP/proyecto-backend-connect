package dbp.connect.Alojamiento.Domain;

import dbp.connect.Alojamiento.DTOS.AlojamientoRequest;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimediaServicio;
import dbp.connect.Excepciones.RecursoNoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class AlojamientoServicio {
    @Autowired
    AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    AlojamientoMultimediaServicio alojamientoMultimediaServicio;

    @Transactional
    public void guardarAlojamiento(AlojamientoRequest alojamiento) {
        Alojamiento alojamientoAux = new Alojamiento();
        if(alojamiento.getId()==null || alojamiento.getDescripcion()==null ||
        alojamiento.getLongitude()==null || alojamiento.getLatitude()==null  ){
            throw new IllegalArgumentException("Los argumentos no deben ser nulos");
        }
        alojamientoAux.setId(alojamiento.getId());
        alojamientoAux.setDescripcion(alojamiento.getDescripcion());
        alojamientoAux.setLongitude(alojamiento.getLongitude());
        alojamientoAux.setLatitude(alojamiento.getLatitude());
        alojamientoAux.setEstado(Estado.DISPONIBLE);
        for (MultipartFile archivo : alojamiento.getMultimedia()) {
            alojamientoMultimediaServicio.guardarArchivo(archivo, alojamientoAux.getId());
        }
        alojamientoRepositorio.save(alojamientoAux);
    }

    public Alojamiento obtenerAlojamiento(Long Id) {
        Optional<Alojamiento> currentAlojamiento = alojamientoRepositorio.findById(Id);
        return currentAlojamiento.orElseThrow(() -> new RecursoNoEncontradoException("Alojamiento no encontrado con id: "));
    }

}
