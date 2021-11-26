package dataStructures.database.honors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("honorsProject")
public interface ProjectRepository extends JpaRepository<HonorsProject, Integer> {
	

}
