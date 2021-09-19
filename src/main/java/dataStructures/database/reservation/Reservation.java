package dataStructures.database.reservation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructures.database.ToJSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Reservations")
public class Reservation extends ToJSONObject implements Comparable<Reservation>  {
	
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

	public Reservation(JSONObject body) {
		try {
			id = body.getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			game = body.getString("game");
			date = body.getString("date");
			people = body.getString("people");
			time = body.getString("time");
			email = body.getString("email");
			reservationID = body.getLong("reservationID");
			status = body.getInt("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(reservationID == -1) {
			reservationID = System.currentTimeMillis();
		}
	}
	
}
