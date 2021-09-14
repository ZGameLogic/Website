package dataStructures.database.websiteInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface WebsiteInfoRepository extends JpaRepository<WebsiteInfo, String> {

}
