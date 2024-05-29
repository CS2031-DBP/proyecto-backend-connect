package dbp.connect.Friendship.Domain;

import dbp.connect.Friendship.DTO.AmigosDTO;
import dbp.connect.Friendship.Infrastructure.FriendshipRepositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendshipServicio {
    @Autowired
    private FriendshipRepositorio friendshipRepositorio;
    private UserRepository userRepository;

    public void createFriendship(Long senderId,Long receiverId){
        Friendship friendship = new Friendship();
        User sender = userRepository.findById(senderId).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado. "));
        User friend = userRepository.findById(receiverId).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado. "));

        friendship.setUser(sender);
        friendship.setFriend(friend);
        friendship.setBlocked(false);
        friendship.setFechaAmistad(ZonedDateTime.now(ZoneId.systemDefault()));
        friendshipRepositorio.save(friendship);
    }
    public void blockearAmigo(Long amistadId, Long usuarioId, Long amigoId){
        User usuario = userRepository.findById(usuarioId).orElseThrow(()->
                new EntityNotFoundException("Usuario no encontrado. "));
        User amigo = userRepository.findById(amigoId).orElseThrow(()->
                new EntityNotFoundException("Usuario no encontrado. "));
        Friendship friendship = friendshipRepositorio.findById(amistadId).orElseThrow(()
                -> new EntityNotFoundException("Amistad no encontrada. "));
        if(!friendship.getUser().equals(usuario)){
            throw  new IllegalArgumentException("Usuario no esta en la amistad. ");
        }
        if(!friendship.getFriend().equals(amigo)){
            throw new IllegalArgumentException("El usuario no es amigo de"+usuario.getPrimerNombre());
        }
        friendship.setBlocked(true);
        friendshipRepositorio.save(friendship);
    }
    public void unblockAmigo(Long amistadId, Long usuarioId,Long amigoId){
        User usuario = userRepository.findById(usuarioId).orElseThrow(()->
                new EntityNotFoundException("Usuario no encontrado. "));
        User amigo = userRepository.findById(amigoId).orElseThrow(()->
                new EntityNotFoundException("Usuario no encontrado. "));
        Friendship friendship = friendshipRepositorio.findById(amistadId).orElseThrow(()
                -> new EntityNotFoundException("Amistad no encontrada. "));
        if(!friendship.getUser().equals(usuario)){
            throw  new IllegalArgumentException("Usuario no esta en la amistad. ");
        }
        if(!friendship.getFriend().equals(amigo)){
            throw new IllegalArgumentException("El usuario no es amigo de"+usuario.getPrimerNombre());
        }
        friendship.setBlocked(false);
        friendshipRepositorio.save(friendship);
    }

    public Page<AmigosDTO> obtenerAmigosNoBloqueados(Long usuarioId, Integer page, Integer size) {
        User user = userRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Set<AmigosDTO> amigosNoBloqueados = new HashSet<>();

        for (Friendship friendship : user.getFriendshipsInicializados()) {
            if (!friendship.isBlocked()) {
                amigosNoBloqueados.add(mapToAmigosDTO(friendship.getFriend()));
            }
        }

        for (Friendship friendship : user.getFriendOf()) {
            if (!friendship.isBlocked()) {
                amigosNoBloqueados.add(mapToAmigosDTO(friendship.getUser()));
            }
        }

        return paginateList(new ArrayList<>(amigosNoBloqueados), page, size);
    }

    public Page<AmigosDTO> obtenerAmigosBloqueados(Long usuarioId, int page, int size) {
        User user = userRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Set<AmigosDTO> amigosBloqueados = new HashSet<>();

        for (Friendship friendship : user.getFriendshipsInicializados()) {
            if (friendship.isBlocked()) {
                amigosBloqueados.add(mapToAmigosDTO(friendship.getFriend()));
            }
        }

        for (Friendship friendship : user.getFriendOf()) {
            if (friendship.isBlocked()) {
                amigosBloqueados.add(mapToAmigosDTO(friendship.getUser()));
            }
        }

        return paginateList(new ArrayList<>(amigosBloqueados), page, size);
    }

    private AmigosDTO mapToAmigosDTO(User user) {
        AmigosDTO dto = new AmigosDTO();
        dto.setAmigoId(user.getId());
        dto.setNombreCompleto(user.getPrimerNombre() + " "+ user.getSegundoNombre());
        dto.setApellidoCompleto(user.getPrimerApellido() + " " + user.getSegundoApellido());
        return dto;    }

    private <T> Page<T> paginateList(List<T> list, int page, int size) {
        int start = Math.min(page * size, list.size());
        int end = Math.min((page + 1) * size, list.size());
        return new PageImpl<>(list.subList(start, end), PageRequest.of(page, size), list.size());
    }


}
