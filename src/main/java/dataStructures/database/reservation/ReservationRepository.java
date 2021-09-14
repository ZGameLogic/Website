package dataStructures.database.reservation;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
//	@Query("SELECT r FROM Reservations r WHERE r.status < 2")
//	public Collection<Reservation> getValidReservations();

}
