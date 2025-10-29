package fr.vod;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Service;
import fr.vod.model.Utilisateur;
import fr.vod.model.Video;
import fr.vod.repository.ServiceRepository;
import fr.vod.repository.UtilisateurRepository;
import fr.vod.repository.VideoRepository;
import fr.vod.service.VideoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour VideoService
 * Teste les opérations CRUD sur les vidéos, notamment la création
 */
@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private VideoService videoService;

    private VideoDTO videoDTO;
    private Video video;
    private Utilisateur mentor;
    private Service service;

    /**
     * Initialisation des données de test avant chaque test
     */
    @BeforeEach
    void setUp() {
        // Création d'un mentor
        mentor = new Utilisateur();
        mentor.setId(1L);
        mentor.setEmail("mentor@test.com");
        mentor.setName("Dupont");
        mentor.setSurname("Jean");

        // Création d'un service
        service = new Service();
        service.setId(1L);
        service.setName("Développement Web");

        // Création d'un DTO de vidéo valide
        videoDTO = new VideoDTO();
        videoDTO.setTitle("Introduction à Spring Boot");
        videoDTO.setUrl("https://example.com/video.mp4");
        videoDTO.setDescription("Tutoriel complet sur Spring Boot");
        videoDTO.setDuration(1800); // 30 minutes
        videoDTO.setServiceId(1L);
        videoDTO.setMentorEmail("mentor@test.com");

        // Création d'une vidéo entity
        video = new Video();
        video.setId(1L);
        video.setTitle(videoDTO.getTitle());
        video.setUrl(videoDTO.getUrl());
        video.setDescription(videoDTO.getDescription());
        video.setDuration(videoDTO.getDuration());
        video.setService(service);
        video.setUtilisateur(mentor);
        video.setIsActive(true);
    }

    /**
     * Test de création de vidéo avec des données valides
     * Vérifie que la vidéo est correctement créée et sauvegardée
     */
    @Test
    void testCreateVideo_WithValidData_ShouldReturnVideoDTO() {
        // Arrange
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.of(mentor));
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.of(service));
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video);

        // Act
        VideoDTO result = videoService.createVideo(videoDTO);

        // Assert
        assertNotNull(result, "Le résultat ne doit pas être null");
        assertEquals("Introduction à Spring Boot", result.getTitle());
        assertEquals("https://example.com/video.mp4", result.getUrl());
        assertEquals("Tutoriel complet sur Spring Boot", result.getDescription());
        assertEquals(1800, result.getDuration());
        
        // Vérification que les méthodes des repositories ont été appelées
        verify(utilisateurRepository, times(1)).findByEmail("mentor@test.com");
        verify(serviceRepository, times(1)).findById(1L);
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test de création de vidéo avec un titre vide
     * Vérifie que la vidéo est quand même créée (pas de validation côté service)
     */
    @Test
    void testCreateVideo_WithEmptyTitle_ShouldCreateVideo() {
        // Arrange
        videoDTO.setTitle("");
        video.setTitle("");
        
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.of(mentor));
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.of(service));
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video);

        // Act
        VideoDTO result = videoService.createVideo(videoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getTitle());
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test de création de vidéo avec un service inexistant
     * Vérifie qu'une exception est levée
     */
    @Test
    void testCreateVideo_WithInvalidServiceId_ShouldThrowException() {
        // Arrange
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.of(mentor));
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            videoService.createVideo(videoDTO);
        }, "Une exception doit être levée pour un service inexistant");
        
        verify(videoRepository, never()).save(any(Video.class));
    }

    /**
     * Test de création de vidéo avec un mentor inexistant
     * Vérifie que la vidéo est créée avec mentor = null
     */
    @Test
    void testCreateVideo_WithInvalidMentorEmail_ShouldCreateVideoWithNullMentor() {
        // Arrange
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.empty());
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.of(service));
        
        Video videoWithoutMentor = new Video();
        videoWithoutMentor.setId(1L);
        videoWithoutMentor.setTitle(videoDTO.getTitle());
        videoWithoutMentor.setUrl(videoDTO.getUrl());
        videoWithoutMentor.setDescription(videoDTO.getDescription());
        videoWithoutMentor.setDuration(videoDTO.getDuration());
        videoWithoutMentor.setService(service);
        videoWithoutMentor.setUtilisateur(null);
        
        when(videoRepository.save(any(Video.class)))
                .thenReturn(videoWithoutMentor);

        // Act
        VideoDTO result = videoService.createVideo(videoDTO);

        // Assert
        assertNotNull(result);
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test de création de vidéo avec durée négative
     * Vérifie que la vidéo est créée (pas de validation de durée)
     */
    @Test
    void testCreateVideo_WithNegativeDuration_ShouldCreateVideo() {
        // Arrange
        videoDTO.setDuration(-100);
        video.setDuration(-100);
        
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.of(mentor));
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.of(service));
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video);

        // Act
        VideoDTO result = videoService.createVideo(videoDTO);

        // Assert
        assertNotNull(result);
        assertEquals(-100, result.getDuration());
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    /**
     * Test de création de vidéo avec tous les champs requis
     * Vérifie que toutes les propriétés sont correctement mappées
     */
    @Test
    void testCreateVideo_ShouldMapAllProperties() {
        // Arrange
        when(utilisateurRepository.findByEmail(videoDTO.getMentorEmail()))
                .thenReturn(Optional.of(mentor));
        when(serviceRepository.findById(videoDTO.getServiceId()))
                .thenReturn(Optional.of(service));
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video);

        // Act
        VideoDTO result = videoService.createVideo(videoDTO);

        // Assert
        assertAll("Vérification de toutes les propriétés",
            () -> assertNotNull(result.getTitle(), "Le titre ne doit pas être null"),
            () -> assertNotNull(result.getUrl(), "L'URL ne doit pas être null"),
            () -> assertNotNull(result.getDescription(), "La description ne doit pas être null"),
            () -> assertTrue(result.getDuration() > 0, "La durée doit être positive"),
            () -> assertNotNull(result.getServiceId(), "L'ID du service ne doit pas être null")
        );
    }
}