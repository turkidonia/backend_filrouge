package fr.vod.service;

import fr.vod.model.Utilisateur;
import fr.vod.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UtilisateurRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Authenticate user by username and raw password
	 */
	public Utilisateur get(String username, String rawPassword) {
		Optional<Utilisateur> user = userRepository.findByEmail(username);
		if (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) {
			return user.get();
		}
		return null;
	}

	/**
	 * Create new user with hashed password
	 */
	public Utilisateur createUser(String email, String password, String username, String lastName, String firstName, Character gender, String phone, Boolean isMentored) {
		Utilisateur user = new Utilisateur();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password)); // hash the password
		user.setName(firstName);
		user.setSurname(lastName);
		user.setGender(gender);
		user.setPhone(phone);
		user.setUsername(username);
		user.setMentored(isMentored); // <--- prend la valeur du front
	    user.setAdmin(false);          // par défaut
		System.out.println("Creating user: " + email + " - " + lastName);
		return userRepository.save(user);
	}

	/**
	 * Check if user with given email exists
	 */
	public boolean exists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
