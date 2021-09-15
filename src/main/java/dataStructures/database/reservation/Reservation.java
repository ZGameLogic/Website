package dataStructures.database.reservation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Reservations")
public class Reservation implements Comparable<Reservation> {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String game;
    private String date;
    private String people;
    private String time;
    private String email;
    // This is a fake ID the user will use to update the reservation
    private long reservationID;
    // 0 needs reviewing, 1 approved, 2 is denied
    private int status;
    
	@Override
	public int compareTo(Reservation o) {
		if(o.getDate().equals(date)) {
			return time.compareTo(o.getTime());
		} else {
			return date.compareTo(o.getDate());
		}
	}
	
}
