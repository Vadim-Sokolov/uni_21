package thyme.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.DbConnector;
import thyme.model.Course;
import thyme.model.dto.CourseDTO;
import thyme.service.CourseService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.CourseDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseControllerRestTest {

	@Autowired
	private CourseControllerRest courseController;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseDtoConverter courseDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS course CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("create TABLE course" + "(id serial primary key," + "name VARCHAR (200),"
				+ "number_of_weeks int, description text);");
		System.out.println("TABLE CREATED");

		statement.execute("insert into course (name, number_of_weeks, description) values ('Course1', 25, 'boo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course2', 30, 'hoo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course3', 35, 'omm');");
		System.out.println("COURSES INSERTED");

		connection.close();
	}

	@Test
	void addCourseTest() {

		// Given
		Course expected = new Course();
		expected.setId(4);
		expected.setName("testCourse");
		expected.setNumberOfWeeks(100);
		expected.setDescription("weeee");

		CourseDTO a = new CourseDTO();
		a.setName("testCourse");
		a.setNumberOfWeeks(100);
		a.setDescription("weeee");

		// When
		courseController.addCourse(a);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(4));

		System.out.println(actual);

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

		CourseDTO a = new CourseDTO();
		a.setId(1);
		a.setName("A2");
		a.setNumberOfWeeks(80);
		a.setDescription("weeee");

		// When
		courseController.updateCourse(a, 1);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(1));

		System.out.println(actual);

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
