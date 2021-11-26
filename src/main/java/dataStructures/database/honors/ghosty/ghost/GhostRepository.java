package dataStructures.database.honors.ghosty.ghost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("honorsGhost")
public interface GhostRepository extends JpaRepository<HonorsGhost, Integer> {

}
