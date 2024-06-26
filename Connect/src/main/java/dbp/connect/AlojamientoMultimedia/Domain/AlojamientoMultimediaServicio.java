package dbp.connect.AlojamientoMultimedia.Domain;

import dbp.connect.Alojamiento.Domain.Alojamiento;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;
import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Infrastructure.AlojamientoMultimediaRepositorio;
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
public class AlojamientoMultimediaServicio {
    @Autowired
    private StorageService storageService;
    private static Long idCounter =0L;
    @Autowired
    private AlojamientoMultimediaRepositorio alojamientoMultimediaRepositorio;
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    public void guardarArchivo(MultipartFile archivo,Long alojamientoid) {
        try {
            AlojamientoMultimedia archivoMultimedia = new AlojamientoMultimedia();
            archivoMultimedia.setId(serializarId(generationId()));

            if(archivo.getContentType().equalsIgnoreCase("image")){
                archivoMultimedia.setTipo(Tipo.FOTO);
            }
            else {
                archivoMultimedia.setTipo(Tipo.VIDEO);
            }
            String key = storageService.subiralS3File(archivo, archivoMultimedia.getId());
            archivoMultimedia.setUrlContenido(storageService.obtenerURL(key));
            Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoid);
            if (alojamientoOptional.isPresent()) {
                Alojamiento alojamiento = alojamientoOptional.get();
                archivoMultimedia.setAlojamiento(alojamiento);
                alojamiento.getAlojamientoMultimedia().add(archivoMultimedia);
                alojamientoRepositorio.save(alojamiento);
            }
            alojamientoMultimediaRepositorio.save(archivoMultimedia);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo",e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarArchivo(Long alojamientoId, String imagenId) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Optional<AlojamientoMultimedia> multimediaOptional = alojamientoMultimediaRepositorio.findById(imagenId);
            if (multimediaOptional.isPresent()) {
                AlojamientoMultimedia multimedia = multimediaOptional.get();
                if (multimedia.getAlojamiento().getId().equals(alojamientoId)) {
                    storageService.deleteFile(multimedia.getId());
                    alojamientoMultimediaRepositorio.delete(multimedia);
                    alojamientoRepositorio.save(alojamientoOptional.get());
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


    public void modificarImagen(Long alojamientoId, String imagenId, byte[] imagen) {
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

    public Page<ResponseMultimediaDTO> obtenerMultimediaPaginacion(Long alojamientoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Alojamiento alojamiento = alojamientoRepositorio.findById(alojamientoId).orElseThrow(()->new EntityNotFoundException("No se encontro el alojamiento"));
        Page <AlojamientoMultimedia> multimediaPage = alojamientoMultimediaRepositorio.findByAlojamiento_Id(alojamientoId, pageable);

        if (multimediaPage.isEmpty()){
            throw new EntityNotFoundException("No se encontraron imagenes para el alojamiento con id: "+alojamientoId);
        }
        List<ResponseMultimediaDTO> multimediaDTOList = multimediaPage.getContent().stream()
                .map(multimedia -> mapResponseMultimediaDTO(multimedia))
                .toList();
        return new PageImpl<>(multimediaDTOList, pageable, multimediaPage.getTotalElements());


    }

    public ResponseMultimediaDTO obtenerMultimedia(Long alojamientoId, Long imagenId) {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
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
