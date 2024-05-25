package dbp.connect.AlojamientoMultimedia.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.AlojamientoMultimedia.Infrastructure.AlojamientoMultimediaRepositorio;
import dbp.connect.Excepciones.NoEncontradoException;
import dbp.connect.MultimediaMensajeIndividual.Domain.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AlojamientoMultimediaServicio {
    @Autowired
    private AlojamientoMultimediaRepositorio alojamientoMultimediaRepositorio;
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;
    public void guardarArchivo(MultipartFile archivo,Long id) {
        try {
            AlojamientoMultimedia archivoMultimedia = new AlojamientoMultimedia();
            archivoMultimedia.setContenido(archivo.getBytes());
            archivoMultimedia.setTipoContenido(archivo.getContentType());
            if(archivoMultimedia.getTipoContenido().equalsIgnoreCase("image")){
                archivoMultimedia.setTipo(Tipo.FOTO);
            }
            else if(archivoMultimedia.getTipoContenido().equalsIgnoreCase("video")){
                archivoMultimedia.setTipo(Tipo.VIDEO);
            }
            else{
                archivoMultimedia.setTipo(Tipo.OTROS);
            }
            Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(id);
            if (alojamientoOptional.isPresent()) {
                Alojamiento alojamiento = alojamientoOptional.get();
                alojamiento.agregarArchivoMultimedia(archivoMultimedia);
                alojamientoRepositorio.save(alojamiento);
            }
            alojamientoMultimediaRepositorio.save(archivoMultimedia);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo",e);
        }
    }
    public void modificarImagen(Long alojamientoId, Long imagenId, byte[] imagen) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Optional<AlojamientoMultimedia> multimediaOptional = alojamientoMultimediaRepositorio.findById(imagenId);
            if (multimediaOptional.isPresent()) {
                AlojamientoMultimedia multimedia = multimediaOptional.get();
                if (multimedia.getAlojamiento().getId().equals(alojamientoId)) {
                    multimedia.setContenido(imagen);
                    alojamientoMultimediaRepositorio.save(multimedia);
                } else {
                    throw new NoEncontradoException("La imagen no pertenece al alojamiento con id: " + alojamientoId);
                }
            } else {
                throw new NoEncontradoException("No se encontró la imagen con id: " + imagenId);
            }
        } else {
            throw new NoEncontradoException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

}
