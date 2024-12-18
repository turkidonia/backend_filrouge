package fr.vod.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//(strategy = GenerationType.AUTO, generator = "native")
	private int id;//les attributs par defaut c'est private
	
	@Column(name = "email")
	@NotNull(message = "l'email est obligatoire")
	private String email;
	
	@Column(name = "password")
	@Size(min = 8, max = 20)
	private String password;
	
	@Column(name = "lastname")
	@NotNull(message = "le nom est obligatoire")
	private String lastName;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "gender")
	private Character gender;
	
	@Column(name = "phone")
	private String phone;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "USER_VIDEO_LIKE", 
        joinColumns = {
            @JoinColumn(name = "UserFK_USER", nullable = false, updatable = false, insertable = false) }, 
            inverseJoinColumns = {
                @JoinColumn(name = "VideoFK_VIDEO", nullable = false, updatable = false, insertable = false) })
    Set<Video> videoLikes;

}
