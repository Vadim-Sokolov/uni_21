package thyme.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.DbConnector;
import thyme.model.Lecture;
import thyme.model.dto.LectureDTO;
import thyme.service.AuditoriumService;
import thyme.service.CourseService;
import thyme.service.GroupService;
import thyme.service.LectureService;
import thyme.service.ServiceException;
import thyme.service.TeacherService;
import thyme.service.dtoconverter.AuditoriumDtoConverter;
import thyme.service.dtoconverter.CourseDtoConverter;
import thyme.service.dtoconverter.GroupDtoConverter;
import thyme.service.dtoconverter.LectureDtoConverter;
import thyme.service.dtoconverter.TeacherDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LectureControllerRestTest {

	@Autowired
	private LectureControllerRest lectureController;

	@Autowired
	private LectureService lectureService;
	@Autowired
	private AuditoriumService auditoriumService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private LectureDtoConverter lectureDtoConverter;
	@Autowired
	private AuditoriumDtoConverter auditoriumDtoConverter;
	@Autowired
	private CourseDtoConverter courseDtoConverter;
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	@Autowired
	private TeacherDtoConverter teacherDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS lecture CASCADE;");
		statement.execute("create TABLE lecture" + "(id serial primary key," + "course_id int, auditorium_id int,"
				+ "teacher_id int, group_id int, time time);");

		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '09:00');");
		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '10:00');");
		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '11:00');");

		connection.close();
	}

	@Test
	void addLectureTest() {

		// Given
		Lecture expected = new Lecture();
		expected.setId(4);
		expected.setAuditorium(auditoriumDtoConverter.toEntity(auditoriumService.getAuditorium(1)));
		expected.setCourse(courseDtoConverter.toEntity(courseService.getCourse(1)));
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));
		expected.setTeacher(teacherDtoConverter.toEntity(teacherService.getTeacher(1)));
		expected.setTime(LocalTime.of(12, 00));

		LectureDTO a = new LectureDTO();
		a.setAuditoriumId(1);
		a.setCourseId(1);
		a.setGroupId(1);
		a.setTeacherId(1);
		a.setTime(LocalTime.of(12, 00));

		// When
		lectureController.addLecture(a);

		Lecture actual = lectureDtoConverter.toEntity(lectureService.getLecture(4));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateLectureTest() {
		// Given
		Lecture expected = new Lecture();
		expected.setId(1);
		expected.setAuditorium(auditoriumDtoConverter.toEntity(auditoriumService.getAuditorium(1)));
		expected.setCourse(courseDtoConverter.toEntity(courseService.getCourse(1)));
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));
		expected.setTeacher(teacherDtoConverter.toEntity(teacherService.getTeacher(1)));
		expected.setTime(LocalTime.of(14, 00));

		LectureDTO a = new LectureDTO();
		a.setId(1);
		a.setAuditoriumId(1);
		a.setCourseId(1);
		a.setGroupId(1);
		a.setTeacherId(1);
		a.setTime(LocalTime.of(14, 00));

		// When
		lectureController.updateLecture(a, 1);

		Lecture actual = lectureDtoConverter.toEntity(lectureService.getLecture(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteLectureTest() {
		// Given
		LectureDTO beforeDeletion = lectureService.getLecture(3);

		// When
		lectureController.deleteLecture(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> lectureService.getLecture(3));
	}

	@Test
	void getLecturesTest() {
		// Given
		List<Lecture> list = lectureController.getLectures();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		LectureDTO expected = new LectureDTO();
		expected.setId(2);
		expected.setAuditoriumId(1);
		expected.setCourseId(1);
		expected.setGroupId(1);
		expected.setTeacherId(1);
		expected.setTime(LocalTime.of(10, 00));

		// When
		LectureDTO actual = lectureController.getLecture(2);

		// Then
		assertEquals(expected, actual);
	}
}
