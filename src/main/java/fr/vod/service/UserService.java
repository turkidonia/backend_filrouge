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
	public Utilisateur createUser(String email, String password, String username, String name, String surname, Character gender, String phone, boolean isMentored) {
		System.out.println("=== DEBUG CREATE USER ===");
	    System.out.println("Email: " + email);
	    System.out.println("isMentored parameter: " + isMentored); // <-- ce que le back reçoit
	    System.out.println("Type: " + ((Object)isMentored).getClass().getSimpleName()); // devrait être boolean
		Utilisateur user = new Utilisateur();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password)); // hash the password
		user.setName(name);
		user.setSurname(surname);
		user.setGender(gender);
		user.setPhone(phone);
		user.setUsername(username);
		user.setMentored(isMentored); // <--- prend la valeur du front
	    user.setAdmin(false);          // par défaut
	    System.out.println("User isMentored before save: " + user.isMentored());
	    System.out.println("Creating user: " + email + " - " + surname);
		
	    Utilisateur savedUser = userRepository.save(user);
	    
	    System.out.println("User isMentored after save: " + savedUser.isMentored());
	    System.out.println("=== END DEBUG ===");
	    
	    return savedUser;
	}

	/**
	 * Check if user with given email exists
	 */
	public boolean exists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
