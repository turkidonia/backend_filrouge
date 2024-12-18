package fr.vod.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationForm {
    private String username;//ici l'username c'est le mail passé dans le formulaire
    private String password;
}
