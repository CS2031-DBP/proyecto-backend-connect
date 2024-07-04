package dbp.connect.Alojamiento.Domain;

import dbp.connect.Alojamiento.DTOS.*;
import dbp.connect.Alojamiento.Excepciones.AlojamientoNotFound;
import dbp.connect.Alojamiento.Excepciones.DescripcionIgualException;
import dbp.connect.Alojamiento.Infrastructure.AlojamientoRepositorio;

import dbp.connect.AlojamientoMultimedia.DTOS.ResponseMultimediaDTO;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimedia;
import dbp.connect.AlojamientoMultimedia.Domain.AlojamientoMultimediaServicio;
import dbp.connect.AlojamientoMultimedia.Infrastructure.AlojamientoMultimediaRepositorio;
import dbp.connect.Security.Utils.AuthorizationUtils;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlojamientoServicio {
    @Autowired
    AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    AlojamientoMultimediaServicio alojamientoMultimediaServicio;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlojamientoMultimediaRepositorio alojamientoMultimediaRepositorio;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    @Transactional
    public ResponseAlojamientoDTO guardarAlojamiento(AlojamientoRequest alojamiento) throws AlojamientoNotFound, AccessDeniedException {
        Alojamiento alojamientoAux = new Alojamiento();
        if(alojamiento.getId()==null || alojamiento.getDescripcion()==null ||
        alojamiento.getLongitude()==null || alojamiento.getLatitude()==null  ){
            throw new IllegalArgumentException("Los argumentos no deben ser nulos");
        }
        User currentPropietario = userRepository.findById(alojamiento.getPropietarioId()).
                orElseThrow(()-> new RuntimeException("Propietario no encontrado"));
        authorizationUtils.verifyUserAuthorization(currentPropietario.getEmail(), alojamiento.getPropietarioId());
        alojamientoAux.setId(alojamiento.getId());
        alojamientoAux.setPropietario(currentPropietario);
        alojamientoAux.setFechaPublicacion(LocalDateTime.now(ZoneId.systemDefault()));
        alojamientoAux.setDescripcion(alojamiento.getDescripcion());
        alojamientoAux.setLongitude(alojamiento.getLongitude());
        alojamientoAux.setLatitude(alojamiento.getLatitude());
        alojamientoAux.setEstado(Estado.DISPONIBLE);
        alojamientoAux.setPrecio(alojamiento.getPrecio());

        for (MultipartFile archivo : alojamiento.getMultimedia()) {
            AlojamientoMultimedia multimedia = alojamientoMultimediaServicio.guardarArchivo(archivo);
            multimedia.setAlojamiento(alojamientoAux);
            alojamientoMultimediaRepositorio.save(multimedia);
            alojamientoAux.getAlojamientoMultimedia().add(multimedia);

        }
        alojamientoRepositorio.save(alojamientoAux);
        ResponseAlojamientoDTO responseAlojamientoDTO = mapResponseAlojamientoDTO(alojamientoAux.getId());
        return responseAlojamientoDTO;
    }

    public ResponseAlojamientoDTO obtenerAlojamiento(Long alojamientoId) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            return mapResponseAlojamientoDTO(alojamientoId);
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public void eliminarById(Long alojamientoId) throws AlojamientoNotFound {
        if (alojamientoRepositorio.existsById(alojamientoId)) {
            alojamientoRepositorio.deleteById(alojamientoId);
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public void modificarPrecio(Long alojamientoId, PriceDTO precio) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            alojamiento.setPrecio(precio.getPrecio());
            alojamiento.setTipoMoneda(precio.getTipoMoneda());
            alojamientoRepositorio.save(alojamiento);
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public void actualizarEstadoAlojamiento(Long alojamientoId) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            alojamiento.setEstado(Estado.NODISPONIBLE);
            alojamientoRepositorio.save(alojamiento);
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public void actualizarDescripcionAlojamiento(Long alojamientoId, ContenidoDTO contenidoDTO) throws AlojamientoNotFound {
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
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public void actualizarUbicacionAlojamiento(Long id, UbicacionDTO ubicacionDTO) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(id);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            if (ubicacionDTO.getLatitude() == null || ubicacionDTO.getLongitude() == null) {
                alojamiento.setLatitude(alojamiento.getLatitude());
                alojamiento.setLongitude(alojamiento.getLongitude());

            } else {
                alojamiento.setLatitude(ubicacionDTO.getLatitude());
                alojamiento.setLongitude(ubicacionDTO.getLongitude());
            }
            alojamiento.setUbicacion(ubicacionDTO.getUbicacion());
            alojamientoRepositorio.save(alojamiento);
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + id);
        }
    }

    public ResponseAlojamientoDTO actualizarAlojamiento(Long alojamientoId,
                                                        AlojamientoRequest alojamientoRequest) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoId);
        if (alojamientoOptional.isPresent()) {
            Alojamiento alojamiento = alojamientoOptional.get();
            alojamiento.setDescripcion(alojamientoRequest.getDescripcion());
            alojamiento.setPrecio(alojamientoRequest.getPrecio());
            alojamiento.setTipoMoneda(alojamientoRequest.getTipoMoneda());
            alojamiento.setLatitude(alojamientoRequest.getLatitude());
            alojamiento.setLongitude(alojamientoRequest.getLongitude());
            alojamiento.setUbicacion(alojamientoRequest.getUbicacion());
            if(!alojamientoRequest.getMultimedia().isEmpty()){
                for(AlojamientoMultimedia multimedia: alojamiento.getAlojamientoMultimedia()){
                    alojamientoMultimediaRepositorio.delete(multimedia);
                }
                for (MultipartFile archivo : alojamientoRequest.getMultimedia()) {
                    AlojamientoMultimedia multimedia = alojamientoMultimediaServicio.guardarArchivo(archivo);
                    multimedia.setAlojamiento(alojamiento);
                    alojamientoMultimediaRepositorio.save(multimedia);
                    alojamiento.getAlojamientoMultimedia().add(multimedia);
                }

            }
            alojamientoRepositorio.save(alojamiento);
            ResponseAlojamientoDTO responseAlojamientoDTO = mapResponseAlojamientoDTO(alojamientoId);
            return responseAlojamientoDTO;
        } else {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoId);
        }
    }

    public Page<ResponseAlojamientoDTO> obtenerAlojamientoPaginacion(Long propietarioId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User propietario = userRepository.findById(propietarioId).orElseThrow(()-> new RuntimeException("Propietario no encontrado"));
        Page<Alojamiento> alojamientos = alojamientoRepositorio.findByPropietarioId(propietarioId, pageable);
        if(alojamientos.isEmpty()){
            throw new RuntimeException(propietario.getUsername()+ "No tiene alojamientos");
        }
        List<ResponseAlojamientoDTO> alojamientoDTOList = alojamientos.getContent().stream()
                .map(alojamiento -> {
                    try {
                        return mapResponseAlojamientoDTO(alojamiento.getId());
                    } catch (AlojamientoNotFound e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return new PageImpl<>(alojamientoDTOList, pageable, alojamientos.getTotalElements());
    }

    public Page<ResponseAlojamientoDTO> obtenerAlojamientoPaginacionNoDisponibles(Long propietarioId, int page, int size ){
        Pageable pageable = PageRequest.of(page, size);
        User propietario = userRepository.findById(propietarioId).orElseThrow(()-> new RuntimeException("Propietario no encontrado"));
        Page<Alojamiento> alojamientos = alojamientoRepositorio.findByPropietarioIdAndEstado(propietarioId, Estado.NODISPONIBLE,pageable);
        if(alojamientos.isEmpty()){
            throw new RuntimeException(propietario.getUsername()+ "No tiene alojamientos");
        }
        List<ResponseAlojamientoDTO> alojamientoDTOList = alojamientos.getContent().stream()
                .map(alojamiento -> {
                    try {
                        return mapResponseAlojamientoDTO(alojamiento.getId());
                    } catch (AlojamientoNotFound e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return new PageImpl<>(alojamientoDTOList, pageable, alojamientos.getTotalElements());
    }

    public Page<ResponseAlojamientoDTO> obtenerAlojamientosPaginacionDisponibles(Long propietarioid, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        User propietario = userRepository.findById(propietarioid).orElseThrow(()-> new RuntimeException("Propietario no encontrado"));
        Page<Alojamiento> alojamientos = alojamientoRepositorio.findByPropietarioIdAndEstado(propietarioid, Estado.DISPONIBLE, pageable);
        if(alojamientos.isEmpty()){
            throw new RuntimeException(propietario.getUsername()+ "No tiene alojamientos");
        }
        List<ResponseAlojamientoDTO> alojamientoDTOList = alojamientos.getContent().stream()
                .map(alojamiento -> {
                    try {
                        return mapResponseAlojamientoDTO(alojamiento.getId());
                    } catch (AlojamientoNotFound e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return new PageImpl<>(alojamientoDTOList, pageable, alojamientos.getTotalElements());
    }

    private ResponseAlojamientoDTO mapResponseAlojamientoDTO(Long alojamientoid) throws AlojamientoNotFound {
        Optional<Alojamiento> alojamientoOptional = alojamientoRepositorio.findById(alojamientoid);
        if (!alojamientoOptional.isPresent()) {
            throw new AlojamientoNotFound("Alojamiento no encontrado con id: " + alojamientoid);
        }
        Alojamiento alojamiento = alojamientoOptional.get();
        ResponseAlojamientoDTO responseAlojamientoDTO = new ResponseAlojamientoDTO();
        responseAlojamientoDTO.setId(alojamiento.getId());
        responseAlojamientoDTO.setPropietarioId(alojamiento.getPropietario().getId());
        responseAlojamientoDTO.setDescripcion(alojamiento.getDescripcion());
        responseAlojamientoDTO.setPrecio(alojamiento.getPrecio());
        responseAlojamientoDTO.setTipoMoneda(alojamiento.getTipoMoneda());
        responseAlojamientoDTO.setLatitude(alojamiento.getLatitude());
        responseAlojamientoDTO.setLongitude(alojamiento.getLongitude());
        responseAlojamientoDTO.setUbicacion(alojamiento.getUbicacion());
        List<ResponseMultimediaDTO> multimediaDTOList = new ArrayList<>();
        for(AlojamientoMultimedia multimedia: alojamiento.getAlojamientoMultimedia()){
            ResponseMultimediaDTO multimediaDTO = new ResponseMultimediaDTO();
            multimediaDTO.setId(multimedia.getId());
            multimediaDTO.setTipo(multimedia.getTipo());
            multimedia.setUrlContenido(multimedia.getUrlContenido());
            multimediaDTOList.add(multimediaDTO);
        }
        responseAlojamientoDTO.setMultimedia(multimediaDTOList);
        return responseAlojamientoDTO;
    }

}
