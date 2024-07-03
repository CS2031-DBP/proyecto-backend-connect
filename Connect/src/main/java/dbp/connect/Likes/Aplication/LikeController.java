package dbp.connect.Likes.Aplication;

import dbp.connect.Likes.Domain.Like;
import dbp.connect.Likes.Domain.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{publicacionInicioId}/{usuarioLikeId")
    public ResponseEntity<Void> postLike(Long publicacionInicioId, Long usuarioLikeId) {
        likeService.processLikeAsync(publicacionInicioId, usuarioLikeId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{likeId}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long likeId) {
        return likeService.findLikeById(likeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
        likeService.deleteLikeById(likeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/publicacion/{publicacionInicioId}")
    public ResponseEntity<List<Like>> getLikesByPublicacion(@PathVariable Long publicacionInicioId) {
        List<Like> likes = likeService.findLikesByPublicacionInicioId(publicacionInicioId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/usuario/{usuarioLikeId}")
    public ResponseEntity<List<Like>> getLikesByUsuario(@PathVariable Long usuarioLikeId) {
        List<Like> likes = likeService.findLikesByUsuarioLikeId(usuarioLikeId);
        return ResponseEntity.ok(likes);
    }
}
