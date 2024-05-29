package dbp.connect.FriendRequest.Domain;

import dbp.connect.FriendRequest.Infrastructure.FriendshipRequestRepositorio;
import dbp.connect.Friendship.Domain.FriendshipServicio;
import dbp.connect.Friendship.Infrastructure.FriendshipRepositorio;
import dbp.connect.User.Domain.User;
import dbp.connect.User.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class FriendshipRequestServicio {
    @Autowired
    private FriendshipRepositorio friendshipRepositorio;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipRequestRepositorio friendshipRequestRepositorio;
    @Autowired
    private FriendshipServicio friendshipServicio;
    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();

            FriendshipRequest friendRequest = new FriendshipRequest();
            friendRequest.setSender(sender);
            friendRequest.setReceiver(receiver);
            friendRequest.setStatus(FriendRequestStatus.PENDIENTE);
            friendRequest.setRequestDate(ZonedDateTime.now(ZoneId.systemDefault()));

            friendshipRequestRepositorio.save(friendRequest);
        }
        else{
            throw new EntityNotFoundException("Los usuarios no se encuentran registrados");
        }
    }

    @Transactional
    public void respondToFriendRequest(Long requestId, boolean accept) {
        Optional<FriendshipRequest> requestOpt = friendshipRequestRepositorio.findById(requestId);

        if (requestOpt.isPresent()) {
            FriendshipRequest friendRequest = requestOpt.get();

            if (accept) {
                friendRequest.setStatus(FriendRequestStatus.ACEPTADO);
                friendshipRequestRepositorio.save(friendRequest);
                friendshipServicio.createFriendship(friendRequest.getSender().getId(), friendRequest.getReceiver().getId());
            } else {
                friendRequest.setStatus(FriendRequestStatus.RECHAZADO);
                friendshipRequestRepositorio.save(friendRequest);
            }
        }
    }


}
