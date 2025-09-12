package fr.vod.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vod.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	
	public Utilisateur findByEmailAndPassword(String email, String password);//trouve moi l'utilisateur qui a ce mail et password 
		
	Optional<Utilisateur> findByEmail(String email);//regarde si l'utilisateur existe ou pas
	

}
