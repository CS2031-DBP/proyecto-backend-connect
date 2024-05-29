package dbp.connect.Friendship.Domain;

import dbp.connect.FriendRequest.Domain.FriendshipRequest;
import dbp.connect.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @Column(name="blocked")
    private boolean blocked;
    @Column(name = "fechaAmistad")
    private ZonedDateTime fechaAmistad;
}
