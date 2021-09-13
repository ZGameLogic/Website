package services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
	
	private static JavaMailSender emailSender;
	
	@Autowired
    private JavaMailSender preConstructEmailSender;
	
	@PostConstruct
	private void initMailSender() {
		emailSender = this.preConstructEmailSender;
	}
	
	public static void sendSimpleEmail(String subject, String body, String to, String from) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(from);
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(body);
        emailSender.send(message);
	}

}
