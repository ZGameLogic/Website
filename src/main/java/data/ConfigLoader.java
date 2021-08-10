package data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@Getter
@PropertySource("File:website.properties")
public class ConfigLoader {
	
	@Value("${keystore.password:none}")
	private String keystorePassword;

	@Value("${keystore.location:none}")
	private String keystoreLocation;
	
	@Value("${port}")
	private String port;

}