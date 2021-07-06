package thyme.controller.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import thyme.controller.rest.AuditoriumControllerRest;
import thyme.model.Auditorium;
import thyme.model.dto.AuditoriumDTO;
import thyme.service.AuditoriumService;
import thyme.service.ServiceException;

@SpringBootTest
class AuditoriumSystemTest {

	@Autowired
	private AuditoriumControllerRest controller;
	@Autowired
	private AuditoriumService service;

	@Test
	void addAuditoriumTest() {

		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(7);
		expected.setName("testAuditorium");
		expected.setCapacity(100);

		AuditoriumDTO auditoriumToAdd = new AuditoriumDTO();
		auditoriumToAdd.setName("testAuditorium");
		auditoriumToAdd.setCapacity(100);

		// When
		controller.addAuditorium(auditoriumToAdd);

		AuditoriumDTO actual = service.getAuditorium(7);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void updateAuditoriumTest() {
		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(1);
		expected.setName("A2");
		expected.setCapacity(80);

		AuditoriumDTO auditoriumToUpdate = new AuditoriumDTO();
		auditoriumToUpdate.setId(1);
		auditoriumToUpdate.setName("A2");
		auditoriumToUpdate.setCapacity(80);

		// When
		controller.updateAuditorium(auditoriumToUpdate, 1);

		AuditoriumDTO actual = service.getAuditorium(1);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteAuditoriumTest() {
		// Given
		AuditoriumDTO beforeDeletion = service.getAuditorium(3);

		// When
		controller.deleteAuditorium(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> service.getAuditorium(3));
	}

	@Test
	void getAuditoriumsTest() {
		// Given
		List<Auditorium> list = controller.getAuditoriums();
		// When

		// Then
		assertEquals(6, list.size());
	}

	@Test
	void getOneTest() {
		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(2);
		expected.setName("B1");
		expected.setCapacity(30);

		// When
		AuditoriumDTO actual = controller.getAuditorium(2);

		// Then
		assertEquals(expected, actual);
	}
}
