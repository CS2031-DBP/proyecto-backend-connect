package dbp.connect.Comentarios.Infrastructure;

import dbp.connect.Comentarios.Domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
