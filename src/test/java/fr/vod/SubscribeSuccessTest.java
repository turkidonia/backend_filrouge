package fr.vod;

import fr.vod.controller.AuthController;
import fr.vod.dto.JwtResponse;
import fr.vod.dto.SubscribeForm;
import fr.vod.security.JwtTokenUtil;
import fr.vod.service.CustomUserDetailsService;
import fr.vod.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class SubscribeSuccessTest {

    private AuthController authController;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        authController = new AuthController();
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        userService = Mockito.mock(UserService.class);
        userDetailsService = Mockito.mock(CustomUserDetailsService.class);

        authController.setAuthenticationManager(authenticationManager);
        authController.setJwtTokenUtil(jwtTokenUtil);
        authController.setUserService(userService);
        authController.setUserDetailsService(userDetailsService);
    }

    @Test
    void subscribeShouldCreateUserAndReturnJwt() {
        SubscribeForm form = new SubscribeForm();
        form.setEmail("newuser@example.com");
        form.setPassword("password");
        form.setUsername("newuser");
        form.setLastName("Doe");
        form.setFirstName("John");
        form.setGender('H');
        form.setPhone("0600000000");
        form.setIsMentored(true);

        Mockito.when(userService.exists("newuser@example.com")).thenReturn(false);

        UserDetails userDetails = new User("newuser@example.com", "password",
                Collections.singleton((GrantedAuthority) () -> "ROLE_USER"));

        Mockito.when(userDetailsService.loadUserByUsername("newuser@example.com"))
                .thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("jwt-token");

        ResponseEntity<?> response = authController.subscribe(form);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertThat(jwtResponse.getToken()).isEqualTo("jwt-token");
        assertThat(jwtResponse.getUsername()).isEqualTo("newuser@example.com");
    }
}
