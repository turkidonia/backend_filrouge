package fr.vod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.vod.dto.AuthenticationForm;
import fr.vod.dto.AuthenticationResponse;
import fr.vod.dto.SubscribeForm;
import fr.vod.dto.RestAPIResponse;
import fr.vod.exception.UtilisateurExisteDejaException;
import fr.vod.exception.UtilisateurInexistantException;
import fr.vod.model.User;
import fr.vod.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("public/v1/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody AuthenticationForm loginRequest, 
    		HttpServletResponse response) {
        Authentication authenticationRequest =
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        System.out.println(loginRequest.getUsername()+" / "+loginRequest.getPassword());
        User user = userService.get(loginRequest.getUsername(), loginRequest.getPassword());
        //verifier que l'utilisateur en base de donnees
        if (user!=null) {
        	String token = user.hashCode()+""+System.currentTimeMillis();
        
        //String token = "azertyuiop";

        //ajouter un cookie à la reponse
        Cookie cookie = new Cookie("auth-token-vod", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        
        // Retourner directement le token sans redirection
       return ResponseEntity.ok(new AuthenticationResponse(token));
        }
        // Pour test de l'erreur
        else throw new UtilisateurInexistantException("Pas d'utilisateur avec cet email en base");
    }
    
    @PostMapping("/subscribe")
    public Object subscribe(@RequestBody SubscribeForm subscribeForm, HttpServletResponse response) {
         if(!userService.exists(subscribeForm.getEmail()))
         {
             userService.createUser(
              subscribeForm.getEmail(), 
              subscribeForm.getPassword(),
              subscribeForm.getLastName(), 
              subscribeForm.getFirstName(),
              subscribeForm.getGender(),	
              subscribeForm.getPhone());
        return ResponseEntity.ok(new RestAPIResponse(200, "Enregistrement créé avec succès"));
         }
        else throw new UtilisateurExisteDejaException();
       
         }
    
    
    
    
}
