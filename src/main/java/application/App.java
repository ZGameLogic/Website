package application;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dataStructures.ConfigLoader;
import lombok.Getter;

@SpringBootApplication(scanBasePackages = {"controllers", "dataStructures", "services"})
@EnableJpaRepositories({"dataStructures"})
@EntityScan({"dataStructures"})
public class App {
	
	@Getter
	public static ConfigLoader config;

	public static void main(String[] args) throws IOException {
		loadConfig();
		
		SpringApplication s = new SpringApplication(App.class);
		s.setDefaultProperties(injectProperties());
		s.run(args);
	}
	
	private static Properties injectProperties() {
		Properties properties = new Properties();
		properties.setProperty("spring.main.banner-mode", "off");
		
		// Stuff for download server
		properties.setProperty("spring.http.multipart.enabled", "false");
		properties.setProperty("spring.http.multipart.maxFileSize", "-1");
		properties.setProperty("spring.http.multipart.maxRequestSize", "-1");
		
		// Stuff for SQL
		properties.setProperty("spring.datasource.url", "jdbc:sqlserver://zgamelogic.com;databaseName=" + config.getDatabaseName());
		properties.setProperty("spring.datasource.username", config.getSqlUser());
		properties.setProperty("spring.datasource.password", config.getSqlPassword());
		properties.setProperty("spring.datasource.driver-class-name", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
		properties.setProperty("spring.jpa.show-sql", "true");
		properties.setProperty("org.hibernate.dialect.MySQLInnoDBDialect", "true");
		
		
		// Stuff for website
		properties.setProperty("logging.level.root", "INFO");
		properties.setProperty("server.port", config.getPort());
		
		// Stuff for email
		properties.setProperty("spring.mail.host", "zgamelogic.com");
		properties.setProperty("spring.mail.port", "25");
		properties.setProperty("spring.mail.username", config.getMailUserName());
		properties.setProperty("spring.mail.password", config.getMailPassword());
		properties.setProperty("spring.mail.properties.mail.smtp.auth", "true");
		properties.setProperty("spring.mail.properties.mail.smtp.starttls.enable", "true");
		
		// Stuff for SSL
		if(!config.getKeystoreLocation().equals("none")) {
			properties.setProperty("server.ssl.enabled", "true");
			properties.setProperty("server.ssl.key-store", config.getKeystoreLocation());
			properties.setProperty("server.ssl.key-alias", "tomcat");
			properties.setProperty("server.ssl.key-store-password", config.getKeystorePassword());
		}
		
		return properties;
	}
	
	private static void loadConfig() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("dataStructures");
		context.refresh();
		config = context.getBean(ConfigLoader.class);
		context.close();
	}
}
