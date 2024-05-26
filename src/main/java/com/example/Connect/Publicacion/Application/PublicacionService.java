package com.example.Connect.Publicacion.Application;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;

import com.example.Connect.Publicacion.Dto.PublicacionCreateDto;
import com.example.Connect.Publicacion.Dto.PublicacionDto;
import com.example.Connect.Publicacion.Dto.P_AlojamientoCreateDto;
import com.example.Connect.Publicacion.Dto.P_AlojamientoDto;
import com.example.Connect.Comentarios.Dto.ComentariosDto;
import com.example.Connect.Review.Dto.ReviewDto;

import com.example.Connect.Exception.CustomException;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import com.example.Connect.Publicacion.Domain.Publicacion;
import com.example.Connect.Publicacion.Domain.P_Alojamiento;
import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Publicacion.Infraestructure.PublicacionRepository;
import com.example.Connect.Publicacion.Infraestructure.P_AlojamientoRepository;
import com.example.Connect.Comentarios.Infraestructure.ComentariosRepository;
import org.springframework.data.domain.Pageable;

import com.example.Connect.Security.JwtUtil;

@Service
public class PublicacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private P_AlojamientoRepository alojamientoRepository;

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public PublicacionDto createPublicacion(
      PublicacionCreateDto publicacionCreateDto,
      Long userId
    ) {

      Usuario usuario = usuarioRepository.findById(userId)
              .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

      Publicacion publicacion = new Publicacion();
      publicacion.setTitulo(publicacionCreateDto.getTitulo());
      publicacion.setBody(publicacionCreateDto.getBody());
      publicacion.setUsuario(usuario);

      try {
        Publicacion publicacionGuardada = publicacionRepository.save(publicacion);
        return new PublicacionDto(
          publicacionGuardada.getId(),
          publicacionGuardada.getUsuario().getId(),
          publicacionGuardada.getTitulo(),
          publicacionGuardada.getBody(),
          publicacionGuardada.getFecha_publicacion()
        );
      } catch (DataIntegrityViolationException e) {
        throw new CustomException(400, "Error al guardar la publicacion");
      }
    }

    public P_AlojamientoDto createAlojamiento(
      P_AlojamientoCreateDto alojamientoCreateDto,
      Long userId
    ) {
      Usuario usuario = usuarioRepository.findById(userId)
              .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

      Publicacion publicacion = new Publicacion();
      publicacion.setTitulo(alojamientoCreateDto.getTitulo());
      publicacion.setBody(alojamientoCreateDto.getBody());
      publicacion.setUsuario(usuario);

      P_Alojamiento alojamiento = new P_Alojamiento();
      alojamiento.setPublicacion(publicacion);
      alojamiento.setPrecio(alojamientoCreateDto.getPrecio());
      alojamiento.setDireccion(alojamientoCreateDto.getDireccion());
      alojamiento.setReferencia(alojamientoCreateDto.getReferencia());

      try {
        Publicacion publicacionGuardada = publicacionRepository.save(publicacion);
        P_Alojamiento alojamientoGuardado = alojamientoRepository.save(alojamiento);

        return new P_AlojamientoDto(
          publicacionGuardada.getId(),
          publicacionGuardada.getUsuario().getId(),
          publicacionGuardada.getTitulo(),
          publicacionGuardada.getBody(),
          publicacionGuardada.getFecha_publicacion(),
          alojamiento.getPrecio(),
          alojamiento.getDireccion(),
          alojamiento.getReferencia(),
          alojamiento.getDisponible()
        );
      } catch (DataIntegrityViolationException e) {
        throw new CustomException(400, "Error la publicacion");
      }

    }

    public Object getPublicacion(Long id) {
        P_Alojamiento alojamiento = alojamientoRepository.findById(id).orElse(null);
        if (alojamiento != null) {
            return convertToDto(alojamiento);
        }

        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));
        return convertToDto(publicacion);
    }

    public List<Object> getPublicacionesPorRango(int start, int count) {
        Pageable pageable = PageRequest.of(start, count);
        return publicacionRepository.findAll(pageable).getContent().stream()
                .map(this::convertToDto_)
                .collect(Collectors.toList());
    }

    private Object convertToDto_(Publicacion publicacion) {
        if (publicacion.getAlojamiento() != null) {
            return convertToDto(publicacion.getAlojamiento());
        }
        return convertToDto(publicacion);
    }

    private PublicacionDto convertToDto(Publicacion publicacion) {
      List<ComentariosDto> comentarios = comentariosRepository.findByPublicacionId(publicacion.getId()).stream()
              .map(comentario -> new ComentariosDto(
                      comentario.getId(),
                      comentario.getBody(),
                      comentario.getFecha_publicacion(),
                      comentario.getPublicacion().getId(),
                      comentario.getUsuario().getId()))
              .collect(Collectors.toList());
      
      return new PublicacionDto(
          publicacion.getId(),
          publicacion.getUsuario().getId(),
          publicacion.getTitulo(),
          publicacion.getBody(),
          publicacion.getFecha_publicacion(),
          comentarios
      );
    }

    public void deletePublicacion(Long publicacionId, String token) {
        Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));

        if (!publicacion.getUsuario().getId().equals(userId)) {
            throw new CustomException(403, "No tienes permiso para eliminar esta publicacion");
        }

        publicacionRepository.delete(publicacion);
    }

    private P_AlojamientoDto convertToDto(P_Alojamiento alojamiento) {
      List<ComentariosDto> comentarios = comentariosRepository.findByPublicacionId(alojamiento.getId()).stream()
              .map(comentario -> new ComentariosDto(
                      comentario.getId(),
                      comentario.getBody(),
                      comentario.getFecha_publicacion(),
                      comentario.getPublicacion().getId(),
                      comentario.getUsuario().getId()))
              .collect(Collectors.toList());

      List<ReviewDto> reviews = alojamiento.getReviews().stream()
              .map(review -> new ReviewDto(
                      review.getId(),
                      review.getUsuario().getId(),
                      review.getBody(),
                      review.getCalificacion(),
                      review.getPAlojamiento().getId(),
                      review.getFecha_publicacion()
              ))
              .collect(Collectors.toList()); 

        return new P_AlojamientoDto(
            alojamiento.getId(),
            alojamiento.getPublicacion().getUsuario().getId(),
            alojamiento.getPublicacion().getTitulo(),
            alojamiento.getPublicacion().getBody(),
            alojamiento.getPublicacion().getFecha_publicacion(),
            alojamiento.getPrecio(),
            alojamiento.getDireccion(),
            alojamiento.getReferencia(),
            alojamiento.getDisponible(),
            comentarios,
            reviews
        );
    }

    public PublicacionDto updatePublicacion(Long id, PublicacionCreateDto publicacionCreateDto, String token) {
        Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));

        if (!publicacion.getUsuario().getId().equals(userId)) {
            throw new CustomException(403, "No tienes permiso para actualizar esta publicacion");
        }

        publicacion.setTitulo(publicacionCreateDto.getTitulo());
        publicacion.setBody(publicacionCreateDto.getBody());

        try {
            Publicacion publicacionGuardada = publicacionRepository.save(publicacion);
            return new PublicacionDto(
                publicacionGuardada.getId(),
                publicacionGuardada.getUsuario().getId(),
                publicacionGuardada.getTitulo(),
                publicacionGuardada.getBody(),
                publicacionGuardada.getFecha_publicacion()
            );
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(400, "Error al guardar la publicacion");
        }
    }
 
    public P_AlojamientoDto updateAlojamiento(Long id, P_AlojamientoCreateDto alojamientoCreateDto, String token) {
      Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
      P_Alojamiento alojamiento = alojamientoRepository.findById(id)
              .orElseThrow(() -> new CustomException(404, "Alojamiento no encontrado"));

      if (!alojamiento.getPublicacion().getUsuario().getId().equals(userId)) {
          throw new CustomException(403, "No tienes permiso para actualizar este alojamiento");
      }
      alojamiento.getPublicacion().setTitulo(alojamientoCreateDto.getTitulo());
      alojamiento.getPublicacion().setBody(alojamientoCreateDto.getBody());
      alojamiento.setPrecio(alojamientoCreateDto.getPrecio());
      alojamiento.setDireccion(alojamientoCreateDto.getDireccion());
      alojamiento.setReferencia(alojamientoCreateDto.getReferencia());
      try {
          P_Alojamiento alojamientoGuardado = alojamientoRepository.save(alojamiento);
          return new P_AlojamientoDto(
              alojamientoGuardado.getId(),
              alojamientoGuardado.getPublicacion().getUsuario().getId(),
              alojamientoGuardado.getPublicacion().getTitulo(),
              alojamientoGuardado.getPublicacion().getBody(),
              alojamientoGuardado.getPublicacion().getFecha_publicacion(),
              alojamientoGuardado.getPrecio(),
              alojamientoGuardado.getDireccion(),
              alojamientoGuardado.getReferencia(),
              alojamientoGuardado.getDisponible()
          );
      } catch (DataIntegrityViolationException e) {
          throw new CustomException(400, "Error al guardar el alojamiento");
      }
    }
}
