package fr.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.vod.model.User;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"fr.vod.controller","fr.vod.config","fr.vod.security","fr.vod.service", "fr.vod.dto","fr.vod.exception","fr.vod.model","fr.vod.repository",})
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
