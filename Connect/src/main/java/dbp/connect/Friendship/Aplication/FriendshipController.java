package dbp.connect.Friendship.Aplication;

import dbp.connect.Friendship.DTO.AmigosDTO;
import dbp.connect.Friendship.Domain.Friendship;
import dbp.connect.Friendship.Domain.FriendshipServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController("/amigos")
public class FriendshipController {
    @Autowired
    private FriendshipServicio friendshipServicio;

    @PatchMapping("/bloquear/{amistadId}/{usuarioId}/{pAmigoId}")
    public ResponseEntity<Void> blockearAmigo(@PathVariable Long amistadId,
                                              @PathVariable Long usuarioId,
                                                        @PathVariable Long pAmigoId){
        friendshipServicio.blockearAmigo(amistadId,usuarioId,pAmigoId);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/unblock/{amistadId}/{usuarioId}/{pAmigoId}")
    public ResponseEntity<Void> unblockAmigo(@PathVariable Long amistadId,
                                              @PathVariable Long usuarioId,
                                              @PathVariable Long pAmigoId){
        friendshipServicio.unblockAmigo(amistadId,usuarioId,pAmigoId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("{usuarioId}")
    public ResponseEntity<Page<AmigosDTO>> mostraramigosNoblockeados(@PathVariable Long usuarioId,
                                                                     @RequestParam Integer page,
                                                                     @RequestParam Integer size){
        return ResponseEntity.ok(friendshipServicio.obtenerAmigosNoBloqueados(usuarioId,page,size));
    }
    @GetMapping("/bloqueados/{usuarioId}")
    public ResponseEntity<Page<AmigosDTO>> mostraramigosBlockeados(@PathVariable Long usuarioId,
                                                                     @RequestParam Integer page,
                                                                     @RequestParam Integer size){
        return ResponseEntity.ok(friendshipServicio.obtenerAmigosBloqueados(usuarioId,page,size));
    }

}
