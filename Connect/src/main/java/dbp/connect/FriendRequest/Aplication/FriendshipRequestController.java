package dbp.connect.FriendRequest.Aplication;

import dbp.connect.FriendRequest.Domain.FriendshipRequestServicio;
import dbp.connect.Friendship.Domain.FriendshipServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestServicio friendshipRequestServicio;

    @PostMapping("/{senderId}/friend-requests/{receiverId}")
    public void sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        friendshipRequestServicio.sendFriendRequest(senderId, receiverId);
    }

    @PostMapping("/friend-requests/{requestId}/respond")
    public void respondToFriendRequest(@PathVariable Long requestId, @RequestParam boolean accept) {
        friendshipRequestServicio.respondToFriendRequest(requestId, accept);
    }

}
