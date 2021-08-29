package dataStructures.database.ghost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface GhostRepository extends JpaRepository<Ghost, Integer> {

}
