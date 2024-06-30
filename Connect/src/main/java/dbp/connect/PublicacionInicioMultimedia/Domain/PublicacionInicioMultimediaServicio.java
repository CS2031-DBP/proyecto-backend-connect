package dbp.connect.PublicacionInicioMultimedia.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.Comentarios.Excepciones.PublicacionNoEncontradoException;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Infrastructure.PublicacionInicioRepositorio;
import dbp.connect.PublicacionInicioMultimedia.Infrastructure.PublicacionInicioMultimediaRepositorio;
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
public class PublicacionInicioMultimediaServicio {
    @Autowired
    private PublicacionInicioMultimediaRepositorio publicacionInicioMultimediaRepositorio;
    @Autowired
    private PublicacionInicioRepositorio publicacionInicioRepositorio;
    @Autowired
    private StorageService storageService;
    private static Long idCounter =0L;


    public PublicacionInicioMultimedia guardarArchivo(MultipartFile archivo) {
        try {
            PublicacionInicioMultimedia archivoMultimedia = new PublicacionInicioMultimedia();
            archivoMultimedia.setId(serializarId(generationId()));

            if (Objects.requireNonNull(archivo.getContentType()).startsWith("image/")) {
                archivoMultimedia.setTipo(Tipo.FOTO);
            } else if (Objects.requireNonNull(archivo.getContentType()).startsWith("video/")) {
                archivoMultimedia.setTipo(Tipo.VIDEO);
            } else {
                throw new IllegalArgumentException("Tipo de archivo no soportado");
            }
            archivoMultimedia.setFechaCreacion(ZonedDateTime.now(ZoneId.systemDefault()));

            String key = storageService.subiralS3File(archivo, archivoMultimedia.getId());
            archivoMultimedia.setContenidoUrl(storageService.obtenerURL(key));
            return archivoMultimedia;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo",e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void eliminarArchivo(Long publicacionId, String archivoId){
        Optional<PublicacionInicio> publicacionInicio = publicacionInicioRepositorio.findById(publicacionId);
        if (publicacionInicio.isPresent()) {
            PublicacionInicio publiInicial= publicacionInicio.get();
            for(PublicacionInicioMultimedia multimedia: publiInicial.getPublicacionMultimedia()){
                if(multimedia.getId().equals(archivoId)){
                    storageService.deleteFile(multimedia.getId());
                    publiInicial.getPublicacionMultimedia().remove(multimedia);
                    publicacionInicioMultimediaRepositorio.delete(multimedia);
                    publicacionInicioRepositorio.save(publiInicial);
                }
            }
        } else {
            throw new EntityNotFoundException("Alojamiento no encontrado con id: " + publicacionId);
        }
    }

    public void modificarArchivo(Long publicacionId, String imagenId, MultipartFile archivo) throws Exception {
        PublicacionInicio publicacionInicio = publicacionInicioRepositorio.findById(publicacionId).
                orElseThrow(()->new PublicacionNoEncontradoException("No se encontro la publicacion"));
            for(PublicacionInicioMultimedia multimedia: publicacionInicio.getPublicacionMultimedia()){
                if(multimedia.getId().equals(imagenId)) {
                    if (Objects.requireNonNull(archivo.getContentType()).startsWith("image/")) {
                        multimedia.setTipo(Tipo.FOTO);
                    } else if (Objects.requireNonNull(archivo.getContentType()).startsWith("video/")) {
                        multimedia.setTipo(Tipo.VIDEO);
                    } else {
                        throw new IllegalArgumentException("Tipo de archivo no soportado");
                    }
                    String key = storageService.subiralS3File(archivo, multimedia.getId());
                    multimedia.setContenidoUrl(storageService.obtenerURL(key));
                    multimedia.setFechaCreacion(ZonedDateTime.now(ZoneId.systemDefault()));
                    publicacionInicioMultimediaRepositorio.save(multimedia);
                    publicacionInicioRepositorio.save(publicacionInicio);
                }else{
                    throw new EntityNotFoundException("La imagen no pertenece a la publicacion con id: " + publicacionId);
                    }
            }

    }

    public Page<ResponseMultimediaDTO> obtenerMultimediaPaginacion(Long publicacionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        PublicacionInicio publicacionInicio = publicacionInicioMultimediaRepositorio.findById(publicacionId).orElseThrow(
                ()->new EntityNotFoundException("No se encontro el alojamiento"));
        Page <AlojamientoMultimedia> multimediaPage = publicacionInicioMultimediaRepositorio.findBy_publicacionId(alojamientoId, pageable);

        if (multimediaPage.isEmpty()){
            throw new EntityNotFoundException("No se encontraron imagenes para el alojamiento con id: "+alojamientoId);
        }
        List<ResponseMultimediaDTO> multimediaDTOList = multimediaPage.getContent().stream()
                .map(multimedia -> mapResponseMultimediaDTO(multimedia))
                .toList();
        return new PageImpl<>(multimediaDTOList, pageable, multimediaPage.getTotalElements());

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

    private Long generationId() {
        return idCounter++;
    }
    private String serializarId(Long id) {
        return id.toString();
    }
}
