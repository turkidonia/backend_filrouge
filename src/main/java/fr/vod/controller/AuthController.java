package fr.vod.controller;

import fr.vod.dto.*;
import fr.vod.security.JwtTokenUtil;
import fr.vod.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.vod.exception.UtilisateurExisteDejaException;
import fr.vod.service.UserService;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("public/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Your JWT utility class for generating tokens

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationForm loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), roles));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscribeForm subscribeForm) {
        if (userService.exists(subscribeForm.getEmail())) {
            throw new UtilisateurExisteDejaException();
        }

        userService.createUser(
                subscribeForm.getEmail(),
                subscribeForm.getPassword(),
                subscribeForm.getUsername(),
                subscribeForm.getLastName(),
                subscribeForm.getFirstName(),
                subscribeForm.getGender(),
                subscribeForm.getPhone()
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(subscribeForm.getEmail());

        String token = jwtTokenUtil.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), roles);
        return ResponseEntity.ok(jwtResponse);
    }

}
