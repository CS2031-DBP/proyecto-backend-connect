package dbp.connect.ChatIndividual.Infrastructure;

import dbp.connect.ChatIndividual.Domain.ChatIndividual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ChatIndividualRepository extends JpaRepository<ChatIndividual, Long> {

    @Query("SELECT c FROM ChatIndividual c JOIN c.usuarios u1 JOIN c.usuarios u2 WHERE u1.Id = :usuario1 AND u2.Id = :usuario2")
    ChatIndividual findByUsuarios(@Param("usuario1") Long usuario1, @Param("usuario2") Long usuario2);
}
