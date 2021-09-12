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
			@RequestParam("time") String time) {
		Reservation r = new Reservation(null, game, date, who, time);
		reservations.save(r);
		
		String body = game + "\n" + who + "\n" + date + "\n" + time;
		
		EmailSender.sendSimpleEmail("Reservation request", body, "notification@zgamelogic.com");
		
		IndexController.addPages(model);
		return "response";
	}
}
