package dbp.connect.Likes.Domain;

import dbp.connect.Likes.Infrastructure.LikeRepositorio;
import dbp.connect.PublicacionInicio.Domain.PublicacionInicio;
import dbp.connect.PublicacionInicio.Infrastructure.PublicacionInicioRepositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
public class LikeService {
    @Autowired
    private LikeRepositorio likeRepositorio;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicacionInicioRepositorio publicacionInicioRepositorio;

    @Async
    public void processLikeAsync(Long publicacionInicioId, Long usuarioLikeId) {

        User userLike = userRepository.findById(usuarioLikeId).orElseThrow(()
                -> new IllegalArgumentException("User not found"));
        PublicacionInicio publicacionInicio = publicacionInicioRepositorio.findById(publicacionInicioId).orElseThrow(()
                -> new IllegalArgumentException("PublicacionInicio not found"));
        publicacionInicio.setCantidadLikes(publicacionInicio.getCantidadLikes() + 1);
        Like like = new Like();
        like.setPublicacionInicio(publicacionInicio);
        like.setUsuarioLike(userLike);
        like.setFechaLike(ZonedDateTime.now(ZoneId.systemDefault()));
        likeRepositorio.save(like);
        System.out.println("Procesando like asincrono para publicacionInicioId: " + publicacionInicioId + " y usuarioLikeId: " + usuarioLikeId);

    }

}
