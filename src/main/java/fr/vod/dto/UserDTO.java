package fr.vod.dto;

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
    private boolean mentored;
    private String username;
}
