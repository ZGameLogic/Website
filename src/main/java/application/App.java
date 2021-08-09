package application;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controllers"})
public class App {

	public static void main(String[] args) throws IOException {
		SpringApplication s = new SpringApplication(App.class);
		Properties config = new Properties();
		config.setProperty("spring.main.banner-mode", "off");
		config.setProperty("logging.level.root", "INFO");
		config.setProperty("server.port", "106");
		s.setDefaultProperties(config);
		s.run(args);
	}
}
