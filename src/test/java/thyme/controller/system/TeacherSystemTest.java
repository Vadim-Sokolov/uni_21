package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.TeacherControllerRest;
import thyme.model.Teacher;
import thyme.model.dto.TeacherDTO;
import thyme.service.FacultyService;
import thyme.service.ServiceException;
import thyme.service.TeacherService;
import thyme.service.dtoconverter.FacultyDtoConverter;
import thyme.service.dtoconverter.TeacherDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TeacherSystemTest {

	@Autowired
	private TeacherControllerRest teacherController;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;
	@Autowired
	private TeacherDtoConverter teacherDtoConverter;

	@Test
	void addTeacherTest() {

		// Given
		Teacher expected = new Teacher();
		expected.setId(7);
		expected.setFirstName("testFirstName");
		expected.setLastName("testLastName");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		TeacherDTO teacherToAdd = new TeacherDTO();
		teacherToAdd.setFirstName("testFirstName");
		teacherToAdd.setLastName("testLastName");
		teacherToAdd.setFacultyId(1);

		// When
		teacherController.addTeacher(teacherToAdd);

		Teacher actual = teacherDtoConverter.toEntity(teacherService.getTeacher(7));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateTeacherTest() {
		// Given
		Teacher expected = new Teacher();
		expected.setId(1);
		expected.setFirstName("Top");
		expected.setLastName("Job");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		TeacherDTO teacherToUpdate = new TeacherDTO();
		teacherToUpdate.setId(1);
		teacherToUpdate.setFirstName("Top");
		teacherToUpdate.setLastName("Job");
		teacherToUpdate.setFacultyId(1);

		// When
		teacherController.updateTeacher(teacherToUpdate, 1);

		Teacher actual = teacherDtoConverter.toEntity(teacherService.getTeacher(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteTeacherTest() {
		// Given
		TeacherDTO beforeDeletion = teacherService.getTeacher(3);

		// When
		teacherController.deleteTeacher(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> teacherService.getTeacher(3));
	}

	@Test
	void getTeachersTest() {
		// Given
		List<Teacher> list = teacherController.getTeachers();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		TeacherDTO expected = new TeacherDTO();
		expected.setId(2);
		expected.setFirstName("Skip");
		expected.setLastName("Dub");
		expected.setFacultyId(1);

		// When
		TeacherDTO actual = teacherController.getTeacher(2);

		// Then
		assertEquals(expected, actual);
	}
}
