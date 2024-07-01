package dbp.connect.ChatGrupal.Infrastructure;

import dbp.connect.ChatGrupal.Domain.ChatGrupal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGrupalRepositorio extends JpaRepository<ChatGrupal, Long> {
}
