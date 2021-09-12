package dataStructures.database.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
