package dataStructures.database.honors.ghosty.aspect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("honorsAspect")
public interface AspectRepository  extends JpaRepository<HonorsAspect, Integer> {

}
