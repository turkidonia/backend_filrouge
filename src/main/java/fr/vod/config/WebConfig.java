package fr.vod.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Value("${app.url}")
	private String allowedOrigin; // contient "http://localhost:3000,https://start-her.netlify.app"
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        	
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            			// On split la chaîne en tableau pour accepter plusieurs origines
                		String[] origins = allowedOrigin.split(",");
            			registry.addMapping("/**") // Allow all endpoints
                        		//.allowedOrigins("http://localhost:9091") // Allow React app origin
            			 		.allowedOrigins(origins) // ici on passe le tableau
                				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow HTTP methods
                				.allowedHeaders("*") // Allow all headers
                				.allowCredentials(true); // Allow credentials (cookies, etc.)
            }
        };
    }
}


