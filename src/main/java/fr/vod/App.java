package fr.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"fr.vod.controller","fr.vod.config","fr.vod.security","fr.vod.service", "fr.vod.dto","fr.vod.exception","fr.vod.model","fr.vod.repository",})
@EnableJpaRepositories(basePackages = "fr.vod.repository")
@EntityScan(basePackages = "fr.vod.model")

public class App
{
    public static void main( String[] args )
    {
       /* System.out.println( "Hello World!" );
        User user = new User();
        user.getEmail();*/
    	SpringApplication.run(App.class, args);
       
    }
}
