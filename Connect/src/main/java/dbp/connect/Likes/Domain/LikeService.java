package dbp.connect.Likes.Domain;

import dbp.connect.Likes.Infrastructure.LikeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LikeService {
    @Autowired
    private LikeRepositorio likeRepositorio;
}
