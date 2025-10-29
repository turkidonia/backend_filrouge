package fr.vod;

import fr.vod.controller.AuthController;
import fr.vod.dto.AuthenticationForm;
import fr.vod.security.JwtTokenUtil;
import fr.vod.service.CustomUserDetailsService;
import fr.vod.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class LoginFailureTest {

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
    void loginShouldReturn401WhenCredentialsAreWrong() {
        AuthenticationForm form = new AuthenticationForm();
        form.setUsername("wrong@example.com");
        form.setPassword("wrongpass");

        Mockito.when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        ResponseEntity<?> response = authController.login(form);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Invalid username or password");
    }
}

