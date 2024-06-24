package com.example.Connect.Usuario.Application;

import com.example.Connect.Usuario.Domain.Usuario;
import com.example.Connect.Usuario.Domain.DatosUsuario;
import com.example.Connect.Usuario.Domain.UsuarioRol;
import com.example.Connect.Usuario.Dto.UsuarioCreateDto;
import com.example.Connect.Usuario.Dto.UsuarioDto;
import com.example.Connect.Usuario.Infraestructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import com.example.Connect.Exception.CustomException;

import com.example.Connect.Security.JwtUtil;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UsuarioDto obtenerUsuarioDto(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
        DatosUsuario datosUsuario = usuario.getDatosUsuario();
        return new UsuarioDto(
            usuario.getId(),
            usuario.getCorreo(),
            usuario.getRol(),
            datosUsuario.getNombre(),
            datosUsuario.getApellido(),
            datosUsuario.getTelefono(),
            datosUsuario.getEdad(),
            datosUsuario.getFechaCreacionPerfil()
        );
    }

    public UsuarioDto obtenerUsuarioPorCorreo(String correo) {
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
            DatosUsuario datosUsuario = usuario.getDatosUsuario();
            return new UsuarioDto(
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getRol(),
                datosUsuario.getNombre(),
                datosUsuario.getApellido(),
                datosUsuario.getTelefono(),
                datosUsuario.getEdad(),
                datosUsuario.getFechaCreacionPerfil()
            );
        }

    public UsuarioDto createUsuario(UsuarioCreateDto usuarioCreateDto) {
        if (usuarioRepository.existsByCorreo(usuarioCreateDto.getCorreo())) {
            throw new CustomException(406, "Correo ya registrado");
        }

        if (!isValidPhoneNumber(usuarioCreateDto.getTelefono())) {
            throw new CustomException(422, "Unprocessable");
        }

        Usuario usuario = new Usuario();
        usuario.setCorreo(usuarioCreateDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioCreateDto.getContrasenia()));
        usuario.setRol(UsuarioRol.values()[usuarioCreateDto.getRol()]);

        DatosUsuario datosUsuario = new DatosUsuario();
        datosUsuario.setNombre(usuarioCreateDto.getNombre());
        datosUsuario.setApellido(usuarioCreateDto.getApellido());
        datosUsuario.setTelefono(usuarioCreateDto.getTelefono());
        datosUsuario.setEdad(Long.parseLong(usuarioCreateDto.getEdad())); 

        usuario.setDatosUsuario(datosUsuario);
        datosUsuario.setUsuario(usuario);

        try {
            Usuario savedUsuario = usuarioRepository.save(usuario);
            return new UsuarioDto(
                savedUsuario.getId(),
                savedUsuario.getCorreo(),
                savedUsuario.getRol(),
                datosUsuario.getNombre(),
                datosUsuario.getApellido(),
                datosUsuario.getTelefono(),
                datosUsuario.getEdad(),
                datosUsuario.getFechaCreacionPerfil()
            );
        } 
        catch (DataIntegrityViolationException e) {
            throw new CustomException(400, "Error al guardar el usuario");
        }
    }
    
    public UsuarioDto obtenerUsuarioPorToken(String token) {
      Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
      Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
      DatosUsuario datosUsuario = usuario.getDatosUsuario();
      return new UsuarioDto(
          usuario.getId(),
          usuario.getCorreo(),
          usuario.getRol(),
          datosUsuario.getNombre(),
          datosUsuario.getApellido(),
          datosUsuario.getTelefono(),
          datosUsuario.getEdad(),
          datosUsuario.getFechaCreacionPerfil()
      );
    }

    public void eliminarUsuarioPorToken(String token) {
        Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7)));
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

public UsuarioDto actualizarUsuarioPorToken(String token, UsuarioCreateDto usuarioCreateDto) {
        Long userId = Long.parseLong(jwtUtil.extractUsername(token.substring(7).trim()));
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new CustomException(404, "Usuario no encontrado"));

        if (!isValidPhoneNumber(usuarioCreateDto.getTelefono())) {
            throw new CustomException(422, "Unprocessable");
        }

        if (usuarioRepository.existsByCorreo(usuarioCreateDto.getCorreo()) && !usuario.getCorreo().equals(usuarioCreateDto.getCorreo())) {
            throw new CustomException(406, "Correo ya registrado");
        }

        usuario.setCorreo(usuarioCreateDto.getCorreo());
        usuario.setContrasenia(passwordEncoder.encode(usuarioCreateDto.getContrasenia()));
        usuario.setRol(UsuarioRol.values()[usuarioCreateDto.getRol()]);

        DatosUsuario datosUsuario = usuario.getDatosUsuario();
        datosUsuario.setNombre(usuarioCreateDto.getNombre());
        datosUsuario.setApellido(usuarioCreateDto.getApellido());
        datosUsuario.setTelefono(usuarioCreateDto.getTelefono());
        datosUsuario.setEdad(Long.parseLong(usuarioCreateDto.getEdad())); 

        usuario.setDatosUsuario(datosUsuario);
        datosUsuario.setUsuario(usuario);

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return new UsuarioDto(
            updatedUsuario.getId(),
            updatedUsuario.getCorreo(),
            updatedUsuario.getRol(),
            datosUsuario.getNombre(),
            datosUsuario.getApellido(),
            datosUsuario.getTelefono(),
            datosUsuario.getEdad(),
            datosUsuario.getFechaCreacionPerfil()
        );
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("\\d{7,15}");
    }
}

