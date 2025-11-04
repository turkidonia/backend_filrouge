package fr.vod.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private boolean isAdmin;
    @JsonProperty("isMentored") // Force Jackson à mapper correctement
    private boolean mentored = true;
    private String username;
}
