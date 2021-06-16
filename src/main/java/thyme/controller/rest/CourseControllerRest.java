package thyme.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import thyme.model.Course;
import thyme.service.CourseService;
import thyme.model.dto.CourseDTO;

@RestController
public class CourseControllerRest {

	private CourseService courseService;

	public CourseControllerRest(CourseService courseService) {
		this.courseService = courseService;
	}

	@PostMapping("/rest/courses")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Method adds new course to database",
	notes = "All fields must be filled in", response = Course.class)
	public Course addCourse(@RequestBody CourseDTO courseDTO) {
		return courseService.addCourse(courseDTO);
	}

	@PutMapping("/rest/courses/{id}")
	@ApiOperation(value = "Method updates course in database",
	notes = "All fields must be filled in", response = Course.class)
	public Course updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable("id") int id) {
		return courseService.updateCourse(courseDTO, id);
	}

	@GetMapping("/rest/courses")
	@ApiOperation(value = "Method returns list of all courses from database")
	public List<Course> getCourses() {
		return courseService.getCourses();
	}

	@GetMapping("/rest/courses/{id}")
	@ApiOperation(value = "Method finds and returns course by id from database", response = CourseDTO.class)
	public CourseDTO getCourse(@PathVariable("id") int id) {
		return courseService.getCourse(id);
	}

	@DeleteMapping("/rest/courses/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Method deletes course from database")
	public void deleteCourse(@PathVariable("id") int id) {
		courseService.deleteCourse(id);
	}
}
