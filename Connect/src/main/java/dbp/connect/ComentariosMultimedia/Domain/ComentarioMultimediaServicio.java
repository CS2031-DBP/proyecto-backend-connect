package dbp.connect.ComentariosMultimedia.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.AlojamientoMultimedia.Infrastructure.AlojamientoMultimediaRepositorio;
import dbp.connect.Comentarios.Domain.Comentario;
import dbp.connect.Comentarios.Infrastructure.ComentarioRepository;
import dbp.connect.ComentariosMultimedia.DTOS.ResponseComMultimediaDTO;
import dbp.connect.ComentariosMultimedia.Infrastructure.ComentarioMultimediaRepositorio;
import dbp.connect.S3.StorageService;
import dbp.connect.Tipo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioMultimediaServicio {
    @Autowired
    ComentarioMultimediaRepositorio comentariosMultimediaRepositorio;
    @Autowired
    private StorageService storageService;
    private static Long idCounter =0L;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public void guardarArchivo(MultipartFile archivo,Long comentarioId) {
        try {
            ComentarioMultimedia archivoMultimedia = new ComentarioMultimedia();
            archivoMultimedia.setId(serializarId(generationId()));

            if(archivo.getContentType().equals("image")){
                archivoMultimedia.setTipo(Tipo.FOTO);
            }
            else {
                archivoMultimedia.setTipo(Tipo.VIDEO);
            }
            String key = storageService.subiralS3File(archivo, archivoMultimedia.getId());
            archivoMultimedia.setUrlContenido(storageService.obtenerURL(key));
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(comentarioId);
            if (comentarioOptional.isPresent()) {
                Comentario comentario = comentarioOptional.get();
                archivoMultimedia.setComentario(comentario);
                comentariosMultimediaRepositorio.save(archivoMultimedia);
                comentario.getComentarioMultimedia();
                comentarioRepository.save(comentario);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo",e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarArchivo(Long comentarioId, String imagenId) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(comentarioId);
        if (comentarioOptional.isPresent()) {
            Optional<ComentarioMultimedia> multimediaOptional = comentariosMultimediaRepositorio.findById(imagenId);
            if (multimediaOptional.isPresent()) {
                ComentarioMultimedia multimedia = multimediaOptional.get();
                if (multimedia.getComentario().getId().equals(comentarioId)) {
                    storageService.deleteFile(multimedia.getId());
                    comentariosMultimediaRepositorio.delete(multimedia);
                    comentarioRepository.save(comentarioOptional.get());
                } else {
                    throw new EntityNotFoundException("La imagen no pertenece al alojamiento con id: " + comentarioId);
                }
            } else {
                throw new EntityNotFoundException("No se encontró la imagen con id: " + imagenId);
            }
        } else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + comentarioId);
        }
    }


    public void modificarImagen(Long alojamientoId, String imagenId,  imagen) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Optional<AlojamientoMultimedia> multimediaOptional = alojamientoMultimediaRepositorio.findById(imagenId);
            if (multimediaOptional.isPresent()) {
                AlojamientoMultimedia multimedia = multimediaOptional.get();
                if (multimedia.getAlojamiento().getId().equals(alojamientoId)) {

                    alojamientoMultimediaRepositorio.save(multimedia);
                } else {
                    throw new EntityNotFoundException("La imagen no pertenece al alojamiento con id: " + alojamientoId);
                }
            } else {
                throw new EntityNotFoundException("No se encontró la imagen con id: " + imagenId);
            }
        } else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }



    public ResponseComMultimediaDTO obtenerMultimedia(Long comentarioId, Long imagenId) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(comentarioId);
        if (comentarioOptional.isPresent()) {
            Comentario comentario = comentarioOptional.get();
            ResponseMultimediaDTO multimediaDTO = new ResponseMultimediaDTO();
            for (AlojamientoMultimedia multimedia : alojamiento.getAlojamientoMultimedia()) {
                if (multimedia.getId().equals(serializarId(imagenId))) {
                    multimediaDTO.setId(multimedia.getId());
                    multimediaDTO.setTipo(multimedia.getTipo());
                    multimediaDTO.setUrl_contenido(multimedia.getUrlContenido());
                    return multimediaDTO;
                }
            }
            throw new EntityNotFoundException("No se encontró la imagen con id: " + imagenId);
        } else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }
    private ResponseMultimediaDTO mapResponseMultimediaDTO(AlojamientoMultimedia multimedia){
        ResponseMultimediaDTO multimediaDTO = new ResponseMultimediaDTO();
        multimediaDTO.setId(multimedia.getId());
        multimediaDTO.setTipo(multimedia.getTipo());
        multimediaDTO.setUrl_contenido(multimedia.getUrlContenido());
        return multimediaDTO;
    }

    private String serializarId(Long imagenId){
        return "imagen-" + imagenId;
    }
    public Long generationId(){
        return ++idCounter;
    }
}
