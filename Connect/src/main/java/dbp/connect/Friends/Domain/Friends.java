package dbp.connect.Friends.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fId;
    private String apodo;
    private long user;
    private boolean isBlocked;
    private boolean isFriend;

}
