package dataStructures.database.minecraft.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ChatRepository extends JpaRepository<ChatMessage, Integer> {
	

}
