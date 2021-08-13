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
	
	@Value("${halo.api:none}")
	private String haloAPIKey;
	
	@Value("${port}")
	private String port;
	
	@Value("${bitbucket.api}")
	private String bitbucketAPIKey;
	
	@Value("${sql.username}")
	private String sqlUser;
	
	@Value("${sql.password}")
	private String sqlPassword;
	
	@Value("${website.api}")
	private String websiteApi;

}
