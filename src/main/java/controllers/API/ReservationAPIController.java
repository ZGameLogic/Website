package controllers.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import application.App;
import dataStructures.database.reservation.Reservation;
import dataStructures.database.reservation.ReservationRepository;

@Controller
@RequestMapping("api/reservations/")
public class ReservationAPIController {
	
	@Autowired
	private ReservationRepository reservations;
	
	@GetMapping
	public ResponseEntity<String> getReservations(){
		try {
			JSONObject returnBody = new JSONObject();
			JSONArray reservation = new JSONArray();
			for(Reservation current : reservations.findAll()) {
				reservation.put(current.toJSONObject());
			}
			returnBody.put("reservations", reservation);
			return ResponseEntity.ok(returnBody.toString());
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSON format");
		}
	}
	
	@PostMapping
	public ResponseEntity<String> saveReservation(
			@RequestHeader(value="apiKey") String key, 
			@RequestBody String bodyString){
		try {
			JSONObject body = new JSONObject(bodyString);
			if(validateKey(key)) {
				Reservation newReservation = new Reservation(body);
				reservations.save(newReservation);
				return ResponseEntity.ok("Reservation changed");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid reservation id");
		} catch (JSONException e) {
			return ResponseEntity.ok("Invalid JSONObject");
		}
	}
	
	@DeleteMapping("{reservationID}")
	public ResponseEntity<String> deleteReservation(
			@RequestHeader(value="apiKey") String key,
			@PathVariable("reservationID") int reservationID){
		try {
			if(validateKey(key)) {
				reservations.deleteById(reservationID);
				return ResponseEntity.ok("Deleted reservation");
			}
			return ResponseEntity.ok("Invalid apiKey");
		} catch (IllegalArgumentException e1) {
			return ResponseEntity.ok("Invalid reservation id");
		}
	}

	private boolean validateKey(String key) {
		return key.equals(App.getConfig().getWebsiteApi());
	}
}
