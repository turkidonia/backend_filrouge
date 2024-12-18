package fr.vod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.vod.model.User;
import fr.vod.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User get(String username, String password) {
		return userRepository.findByEmailAndPassword(username, password);
	}
	
	public User createUser(String email, String password, String lastName, String firstName, Character gender, String phone) {
		System.out.println(lastName+" - "+email);
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setPhone(phone);
		userRepository.save(user);
		return user;
		
	}
	
	public boolean exists(String email) {
		return (userRepository.findByEmail(email)!=null);
	}
}
