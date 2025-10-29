package fr.vod;

import fr.vod.controller.AuthController;
import fr.vod.dto.SubscribeForm;
import fr.vod.exception.UtilisateurExisteDejaException;
import fr.vod.security.JwtTokenUtil;
import fr.vod.service.CustomUserDetailsService;
import fr.vod.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SubscribeUserExistsTest {

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
    void subscribeShouldThrowExceptionWhenUserAlreadyExists() {
        SubscribeForm form = new SubscribeForm();
        form.setEmail("existing@example.com");

        Mockito.when(userService.exists("existing@example.com")).thenReturn(true);

        assertThrows(UtilisateurExisteDejaException.class, () -> authController.subscribe(form));
    }
}
