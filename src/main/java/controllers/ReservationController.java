package controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dataStructures.database.reservation.Reservation;
import dataStructures.database.reservation.ReservationRepository;
import dataStructures.database.websiteInfo.WebsiteInfoRepository;
import services.EmailSender;

@Controller
public class ReservationController {

	@Autowired
	private ReservationRepository reservations;
	
	@Autowired
	private WebsiteInfoRepository webInf;
	
	@GetMapping("/reservations")
	public String reservation(Model model) {
		List<String> games = Arrays.asList(webInf.findById("games").get().getInformation().split(","));
		model.addAttribute("games", games);
		List<Reservation> res = reservations.getValidReservations();
		Collections.sort(res);
		model.addAttribute("reservations", res);
		IndexController.addPages(model);
		return "reservations";
	}
	
	@GetMapping("/reservations/edit")
	public String editReservation(Model model, @RequestParam("id") String reservationID) {
		model.addAttribute("reservation", reservations.getReservationByFakeID(Long.parseLong(reservationID)));
		IndexController.addPages(model);
		return "editReservation";
	}
	
	@PostMapping("/reservations")
	public String makeReservation(Model model,
			@RequestParam("game") String game,
			@RequestParam("who") String who,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("email") String email) {
		Reservation r = new Reservation(null, game, date, who, time, email, System.currentTimeMillis(), 0);
		reservations.save(r);
		
		String body = "Game: " + game + "\nWho with: " + who + "\nDate: " + date + "\nTime: " + time;
		String header = "Thank you for your reservation! You will get an email notification if Ben wants to accept"
				+ " or deny your reservation.\n\nReservation details:\n";
		String footer = "\n\nReservation id: " + r.getReservationID();
		
		EmailSender.sendSimpleEmail("Reservation request", body, "ben@zgamelogic.com", "notification@zgamelogic.com");
		EmailSender.sendSimpleEmail("Reservation request", header + body + footer, email, "no-reply@zgamelogic.com");
		
		IndexController.addPages(model);
		return "response";
	}
}
