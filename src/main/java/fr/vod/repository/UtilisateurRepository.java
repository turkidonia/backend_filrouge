package fr.vod.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vod.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	
	
		
	Optional<Utilisateur> findByEmail(String email);//regarde si l'utilisateur existe ou pas
	

}
