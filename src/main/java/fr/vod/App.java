package fr.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"fr.vod.controller","fr.vod.config","fr.vod.security","fr.vod.service", "fr.vod.dto","fr.vod.exception","fr.vod.model","fr.vod.repository",})
@EnableJpaRepositories(basePackages = "fr.vod.repository")
@EntityScan(basePackages = "fr.vod.model")

public class App {
    public static void main(String[] args) {
        // Charge les variables .env
        Dotenv dotenv = Dotenv.load();

        // Définit le profil actif si non déjà défini
        String profile = System.getenv("SPRING_PROFILES_ACTIVE");
        if(profile == null) {
            System.setProperty("spring.profiles.active", dotenv.get("SPRING_PROFILES_ACTIVE", "local"));
        }

        SpringApplication.run(App.class, args);
    }
}
