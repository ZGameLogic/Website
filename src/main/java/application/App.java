package application;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import data.ConfigLoader;
import lombok.Getter;

@SpringBootApplication(scanBasePackages = {"controllers", "dataStructures"})
@EnableJpaRepositories("dataStructures")
@EntityScan("dataStructures")
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
		
		properties.setProperty("spring.http.multipart.enabled", "false");
		properties.setProperty("spring.http.multipart.maxFileSize", "-1");
		properties.setProperty("spring.http.multipart.maxRequestSize", "-1");
		
		properties.setProperty("spring.datasource.url", "jdbc:sqlserver://192.168.1.12;databaseName=" + config.getDatabaseName());
		properties.setProperty("spring.datasource.username", config.getSqlUser());
		properties.setProperty("spring.datasource.password", config.getSqlPassword());
		
		properties.setProperty("spring.datasource.driver-class-name", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
		properties.setProperty("spring.jpa.show-sql", "true");
		properties.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
		
		properties.setProperty("logging.level.root", "INFO");
		properties.setProperty("server.port", config.getPort());
		
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
		context.scan("data");
		context.refresh();
		config = context.getBean(ConfigLoader.class);
		context.close();
	}
}
