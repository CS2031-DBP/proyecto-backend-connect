package dbp.connect.MultimediaMensaje.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.Mensaje.DTOS.MensajeResponseDTO;
import dbp.connect.Mensaje.Domain.Mensaje;
import dbp.connect.Mensaje.Infrastructure.MensajeRepository;
import dbp.connect.MultimediaMensaje.DTO.MensajeMultimediaDTO;
import dbp.connect.MultimediaMensaje.Infrastructure.MultimediaMensajeIndividualRepositorio;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MultimediaMensajeServicio {
    @Autowired
    private MultimediaMensajeIndividualRepositorio multimediaMensajeIndividualRepositorio;
    @Autowired
    private StorageService storageService;
    @Autowired
    private MensajeRepository mensajeRepository;

    private static Long idCounter =0L;


    public MultimediaMensaje saveMultimedia(MultipartFile file) {
        try {
            MultimediaMensaje archivoMultimedia = new MultimediaMensaje();
            archivoMultimedia.setId(serializarId(generationId()));

            if (Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                archivoMultimedia.setTipo(Tipo.FOTO);
            } else if (Objects.requireNonNull(file.getContentType()).startsWith("video/")) {
                archivoMultimedia.setTipo(Tipo.VIDEO);
            } else {
                throw new IllegalArgumentException("Tipo de archivo no soportado");
            }
            archivoMultimedia.setFecha(ZonedDateTime.now(ZoneId.systemDefault()));
            String key = storageService.subiralS3File(file, archivoMultimedia.getId());
            archivoMultimedia.setUrl(storageService.obtenerURL(key));
            return archivoMultimedia;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo",e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarArchivo(Long mensajeId, String imagenId) {
        Optional<Mensaje> mensajeOptional = mensajeRepository.findById(mensajeId);
        if (mensajeOptional.isPresent()) {
            Mensaje mensaje= mensajeOptional.get();
            for(MultimediaMensaje multimedia: mensaje.getMultimediaMensaje()){
                if(multimedia.getId().equals(imagenId)){
                    storageService.deleteFile(multimedia.getId());
                    multimedia.getMensaje().re(multimedia);
                     multimediaMensajeIndividualRepositorio.delete(multimedia);
                    alojamientoRepositorio.save(aloj);
                }
            }
        } else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }


    public void modificarArchivo(Long alojamientoId, String imagenId, MultipartFile archivo) throws Exception {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento aloj= alojamientoOptional.get();
            for(AlojamientoMultimedia multimedia: aloj.getAlojamientoMultimedia()){
                if(multimedia.getId().equals(imagenId)){
                    if (Objects.requireNonNull(archivo.getContentType()).startsWith("image/")) {
                        multimedia.setTipo(Tipo.FOTO);
                    } else if (Objects.requireNonNull(archivo.getContentType()).startsWith("video/")) {
                        multimedia.setTipo(Tipo.VIDEO);
                    } else {
                        throw new IllegalArgumentException("Tipo de archivo no soportado");
                    }
                    String key = storageService.subiralS3File(archivo, multimedia.getId());
                    multimedia.setUrlContenido(storageService.obtenerURL(key));
                    multimedia.setFechaCreacion(ZonedDateTime.now(ZoneId.systemDefault()));
                    alojamientoMultimediaRepositorio.save(multimedia);
                    alojamientoRepositorio.save(aloj);
                }
            }
        }
        else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public ResponseMultimediaDTO obtenerMultimedia(Long alojamientoId, String imagenId) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            ResponseMultimediaDTO multimediaDTO = new ResponseMultimediaDTO();
            for (AlojamientoMultimedia multimedia : alojamiento.getAlojamientoMultimedia()) {
                if (multimedia.getId().equals((imagenId))) {
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
    public Long generationId() {
        return ++idCounter;
    }
}
