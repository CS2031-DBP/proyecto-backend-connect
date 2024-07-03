package dbp.connect.FriendRequest.Aplication;

import dbp.connect.FriendRequest.Domain.FriendshipRequestServicio;
import dbp.connect.Friendship.Domain.FriendshipServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship-requests")
public class FriendshipRequestController {

    @Autowired
    private FriendshipRequestServicio friendshipRequestServicio;

    @PostMapping("/{senderId}/{receiverId}")
    public void sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        friendshipRequestServicio.sendFriendRequest(senderId, receiverId);
    }

    @PatchMapping("/{requestId}/respond")
    public void respondToFriendRequest(@PathVariable Long requestId, @RequestParam boolean accept) {
        friendshipRequestServicio.respondToFriendRequest(requestId, accept);
    }


}
