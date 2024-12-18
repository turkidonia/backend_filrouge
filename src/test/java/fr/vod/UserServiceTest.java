package fr.vod;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.vod.model.User;
import fr.vod.model.Video;
import fr.vod.repository.UserRepository;
import fr.vod.service.UserService;

public class UserServiceTest {
	
	@Mock//creer une copie de user qui est fictive juste pour tester: fausse simulation
	private UserRepository userRepository;//mocking the repository
	
	@InjectMocks
	private UserService userService; //the service being tested
	
	private User mockUser;
	
	
	@BeforeEach
	public void SetUp() {
		
		MockitoAnnotations.openMocks(this);//Initilizes mocks
		HashSet<Video> videos = new HashSet<Video>();
		Video mockvideo = new Video();
		videos.add(mockvideo);
		
		mockUser = new User();
		mockUser.setId(1);
		mockUser.setEmail("marine@yahoo.fr");
		mockUser.setFirstName("marine");
		mockUser.setLastName("toto");
		mockUser.setPassword("pass1254");
		mockUser.setGender('F');
		mockUser.setPhone("0337896542");
		mockUser.setVideoLikes(videos);
		
		
	}
	
	@Test
	public void testGetUserById() {

		//assertEquals(1,1);//assertEquals(1,0);
		//Arrange: Mock the behavior of userRepository
		//Optional<User> optionalUser = Optional.of(mockUser);
		when(userRepository.findByEmailAndPassword("marine@yahoo.fr", "pass1254"))
		.thenReturn(mockUser);
		
		//Act: Call the method under test
		User result = userService.get("marine@yahoo.fr", "pass1254");
		
		//Assert: verify the result
		assertNotNull(result);
		assertEquals("marine", result.getFirstName());
		assertEquals("marine@yahoo.fr", result.getEmail());
		Boolean b = result.getGender() == 'F';
		assertEquals(b , true); 
	}

}
