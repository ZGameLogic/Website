package dataStructures.database.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	/**
	 * @return List of all Reservations that have not been denied
	 */
	@Query(value = "SELECT * FROM reservations r WHERE r.status < 2", nativeQuery = true)
	public List<Reservation> getValidReservations();
	
	/**
	 * @return A reservation with an reservationId
	 */
	@Query(value = "SELECT * FROM reservations WHERE reservationid = ?1", nativeQuery = true)
	public Reservation getReservationByFakeID(Long reservationID);

}
