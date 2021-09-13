package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dataStructures.database.reservation.Reservation;
import dataStructures.database.reservation.ReservationRepository;
import services.EmailSender;

@Controller
public class ReservationController {

	@Autowired
	ReservationRepository reservations;
	
	@GetMapping("/reservations")
	public String reservation(Model model) {
		IndexController.addPages(model);
		return "reservations";
	}
	
	@PostMapping("/reservations")
	public String makeReservation(Model model,
			@RequestParam("game") String game,
			@RequestParam("who") String who,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("email") String email) {
		Reservation r = new Reservation(null, game, date, who, time, email);
		reservations.save(r);
		
		String body = "Game: " + game + "\nWho with: " + who + "\nDate: " + date + "\nTime: " + time;
		String header = "Thank you for your reservation! You will get an email notification if Ben wants to accept"
				+ " or deny your reservation.\n\nReservation details:\n";
		
		EmailSender.sendSimpleEmail("Reservation request", body, "ben@zgamelogic.com", "notification@zgamelogic.com");
		EmailSender.sendSimpleEmail("Reservation request", header + body, email, "no-reply@zgamelogic.com");
		
		IndexController.addPages(model);
		return "response";
	}
}
