package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.FacultyControllerRest;
import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.FacultyDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FacultySystemTest {

	@Autowired
	private FacultyControllerRest facultyController;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;

	@Test
	void addFacultyTest() {

		// Given
		Faculty expected = new Faculty();
		expected.setId(7);
		expected.setName("testFaculty");

		FacultyDTO facultyToAdd = new FacultyDTO();
		facultyToAdd.setName("testFaculty");

		// When
		facultyController.addFaculty(facultyToAdd);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(7));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateFacultyTest() {
		// Given
		Faculty expected = new Faculty();
		expected.setId(1);
		expected.setName("A2");

		FacultyDTO facultyToUpdate = new FacultyDTO();
		facultyToUpdate.setId(1);
		facultyToUpdate.setName("A2");

		// When
		facultyController.updateFaculty(facultyToUpdate, 1);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteFacultyTest() {
		// Given
		FacultyDTO beforeDeletion = facultyService.getFaculty(3);

		// When
		facultyController.deleteFaculty(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> facultyService.getFaculty(3));
	}
	@Test
	void getFacultysTest() {
		// Given
		List<Faculty> list = facultyController.getFacultys();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		FacultyDTO expected = new FacultyDTO();
		expected.setId(2);
		expected.setName("Faculty2");

		// When
		FacultyDTO actual = facultyController.getFaculty(2);

		// Then
		assertEquals(expected, actual);
	}
}
