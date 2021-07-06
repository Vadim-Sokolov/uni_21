package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.StudentControllerRest;
import thyme.model.Student;
import thyme.model.dto.StudentDTO;
import thyme.service.GroupService;
import thyme.service.ServiceException;
import thyme.service.StudentService;
import thyme.service.dtoconverter.GroupDtoConverter;
import thyme.service.dtoconverter.StudentDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentSystemTest {

	@Autowired
	private StudentControllerRest studentController;

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	@Autowired
	private StudentDtoConverter studentDtoConverter;

	@Test
	void addStudentTest() {

		// Given
		Student expected = new Student();
		expected.setId(7);
		expected.setFirstName("testFirstName");
		expected.setLastName("testLastName");
		expected.setStudentCardNumber("testCardNumber");
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));

		StudentDTO studentToAdd = new StudentDTO();
		studentToAdd.setFirstName("testFirstName");
		studentToAdd.setLastName("testLastName");
		studentToAdd.setStudentCardNumber("testCardNumber");
		studentToAdd.setGroupId(1);

		// When
		studentController.addStudent(studentToAdd);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(7));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateStudentTest() {
		// Given
		Student expected = new Student();
		expected.setId(1);
		expected.setFirstName("Loop");
		expected.setLastName("Hole");
		expected.setStudentCardNumber("Carrr");
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));

		StudentDTO studentToUpdate = new StudentDTO();
		studentToUpdate.setId(1);
		studentToUpdate.setFirstName("Loop");
		studentToUpdate.setLastName("Hole");
		studentToUpdate.setStudentCardNumber("Carrr");
		studentToUpdate.setGroupId(1);

		// When
		studentController.updateStudent(studentToUpdate, 1);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteStudentTest() {
		// Given
		StudentDTO beforeDeletion = studentService.getStudent(3);

		// When
		studentController.deleteStudent(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> studentService.getStudent(3));
	}
	@Test
	void getStudentsTest() {
		// Given
		List<Student> list = studentController.getStudents();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		StudentDTO expected = new StudentDTO();
		expected.setId(2);
		expected.setFirstName("June");
		expected.setLastName("Bay");
		expected.setStudentCardNumber("zaboo");
		expected.setGroupId(1);

		// When
		StudentDTO actual = studentController.getStudent(2);

		// Then
		assertEquals(expected, actual);
	}
}
