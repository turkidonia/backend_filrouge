package fr.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "fr.vod.repository")
@EntityScan(basePackages = "fr.vod.model")

public class App {
    public static void main(String[] args) {
       
        SpringApplication.run(App.class, args);
    }
}
