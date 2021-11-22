package dataStructures.database.seaofthieves;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {

}
