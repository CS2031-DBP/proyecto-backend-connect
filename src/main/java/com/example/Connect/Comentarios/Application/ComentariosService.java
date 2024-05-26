package com.example.forutec2.Comentarios.Application;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.forutec2.Security.JwtUtil;

import com.example.forutec2.Exception.CustomException;

import com.example.forutec2.Usuario.Domain.Usuario;
import com.example.forutec2.Publicacion.Domain.Publicacion;

import com.example.forutec2.Comentarios.Domain.Comentarios;
import com.example.forutec2.Comentarios.Dto.ComentariosDto;
import com.example.forutec2.Comentarios.Dto.ComentariosCreateDto;
import com.example.forutec2.Comentarios.Infraestructure.ComentariosRepository;
import com.example.forutec2.Publicacion.Infraestructure.PublicacionRepository;
import com.example.forutec2.Usuario.Infraestructure.UsuarioRepository;

@Service
public class ComentariosService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ComentariosRepository comentariosRepository;

  @Autowired
  private PublicacionRepository publicacionRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public ComentariosDto createComentarios(
    ComentariosCreateDto comentariosCreateDto,
    Long userId,
    Long publicacionId
  ) {
    
    Publicacion publicacion = publicacionRepository.findById(publicacionId)
        .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));

    Usuario usuario = usuarioRepository.findById(userId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    Comentarios comentario = new Comentarios();
    comentario.setBody(comentariosCreateDto.getBody());
    comentario.setPublicacion(publicacion);

    comentario.setUsuario(usuario);

    try {
      Comentarios comentarioGuardado = comentariosRepository.save(comentario);
      return new ComentariosDto(
        comentarioGuardado.getId(),
        comentarioGuardado.getBody(),
        comentarioGuardado.getFecha_publicacion(),
        comentarioGuardado.getPublicacion().getId(),
        comentarioGuardado.getUsuario().getId()
      );
    }
    catch (DataIntegrityViolationException e) {
      throw new CustomException(400, "Comentario no creado");
    }
  }

  public ComentariosDto getComentarios(Long id) {
    Comentarios comentario = comentariosRepository.findById(id)
        .orElseThrow(() -> new CustomException(404, "Comentario no encontrado"));

    return new ComentariosDto(
      comentario.getId(),
      comentario.getBody(),
      comentario.getFecha_publicacion(),
      comentario.getPublicacion().getId(),
      comentario.getUsuario().getId()
    );
  }

  public List<ComentariosDto> getAllComentarios(Long publicacionId) {

    Publicacion publicacion = publicacionRepository.findById(publicacionId)
        .orElseThrow(() -> new CustomException(404, "Publicacion no encontrada"));

    List<Comentarios> comentarios = comentariosRepository.findByPublicacionId(publicacion.getId());
    return comentarios.stream().map(
      comentario -> new ComentariosDto(
        comentario.getId(),
        comentario.getBody(),
        comentario.getFecha_publicacion(),
        comentario.getPublicacion().getId(),
        comentario.getUsuario().getId()
      )).collect(Collectors.toList());
  }

  public List<ComentariosDto> getAllComentariosByUsuario(Long usuarioId) {

    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

    List<Comentarios> comentarios = comentariosRepository.findByUsuarioId(usuario.getId());
    return comentarios.stream().map(
      comentario -> new ComentariosDto(
        comentario.getId(),
        comentario.getBody(),
        comentario.getFecha_publicacion(),
        comentario.getPublicacion().getId(),
        comentario.getUsuario().getId())
      ).collect(Collectors.toList());
  }

  public void deleteComentarios(Long id, String token) {
    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    Comentarios comentario = comentariosRepository.findById(id)
      .orElseThrow(() -> new CustomException(404, "Comentario no encontrado"));

    if (!comentario.getUsuario().getId().equals(userId)) {
      throw new CustomException(403, "No tienes permiso para eliminar esta comentario");
    }
    comentariosRepository.deleteById(id);
  }

  public ComentariosDto updateComentarios(Long id, ComentariosCreateDto comentariosCreateDto, String token) {

    Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
    Comentarios comentario = comentariosRepository.findById(id)
      .orElseThrow(() -> new CustomException(404, "Comentario no encontrado"));

    if (!comentario.getUsuario().getId().equals(userId)) {
      throw new CustomException(403, "No tienes permiso para eliminar esta comentario");
    }
    comentario.setBody(comentariosCreateDto.getBody());
    try {
      Comentarios comentarioGuardado = comentariosRepository.save(comentario);
      return new ComentariosDto(
          comentarioGuardado.getId(),
          comentarioGuardado.getBody(),
          comentarioGuardado.getFecha_publicacion(),
          comentarioGuardado.getPublicacion().getId(),
          comentarioGuardado.getUsuario().getId()
        );
    } catch (DataIntegrityViolationException e) {
      throw new CustomException(400, "Error al guardar el comentario");
    }
  }
}
