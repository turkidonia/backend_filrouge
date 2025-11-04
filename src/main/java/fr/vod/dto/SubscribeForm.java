package fr.vod.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeForm {
	private String email;
	private String username;
	private String password;
	private String name;
	private String surname;
	private Character gender;
	private String phone;
	@JsonProperty("isMentored")
    private boolean mentored = true;
}
