package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.CourseControllerRest;
import thyme.model.Course;
import thyme.model.dto.CourseDTO;
import thyme.service.CourseService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.CourseDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseSystemTest {

	@Autowired
	private CourseControllerRest courseController;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseDtoConverter courseDtoConverter;

	@Test
	void addCourseTest() {

		// Given
		Course expected = new Course();
		expected.setId(7);
		expected.setName("testCourse");
		expected.setNumberOfWeeks(100);
		expected.setDescription("weeee");

		CourseDTO courseToAdd = new CourseDTO();
		courseToAdd.setName("testCourse");
		courseToAdd.setNumberOfWeeks(100);
		courseToAdd.setDescription("weeee");

		// When
		courseController.addCourse(courseToAdd);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(7));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateCourseTest() {
		// Given
		Course expected = new Course();
		expected.setId(1);
		expected.setName("A2");
		expected.setNumberOfWeeks(80);
		expected.setDescription("weeee");

		CourseDTO courseToUpdate = new CourseDTO();
		courseToUpdate.setId(1);
		courseToUpdate.setName("A2");
		courseToUpdate.setNumberOfWeeks(80);
		courseToUpdate.setDescription("weeee");

		// When
		courseController.updateCourse(courseToUpdate, 1);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteCourseTest() {
		// Given
		CourseDTO beforeDeletion = courseService.getCourse(3);

		// When
		courseController.deleteCourse(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> courseService.getCourse(3));
	}

	@Test
	void getCoursesTest() {
		// Given
		List<Course> list = courseController.getCourses();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		CourseDTO expected = new CourseDTO();
		expected.setId(2);
		expected.setName("Course2");
		expected.setNumberOfWeeks(30);
		expected.setDescription("hoo");

		// When
		CourseDTO actual = courseController.getCourse(2);
		System.out.println(actual.toString());

		// Then
		assertEquals(expected, actual);
	}
}
