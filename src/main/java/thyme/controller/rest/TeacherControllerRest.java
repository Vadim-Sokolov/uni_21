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
import thyme.model.Teacher;
import thyme.service.TeacherService;
import thyme.model.dto.TeacherDTO;

@RestController
public class TeacherControllerRest {

	private TeacherService teacherService;

	public TeacherControllerRest(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@PostMapping("/rest/teachers")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adds new teacher to database",
	notes = "All fields must be filled in", response = Teacher.class)
	public Teacher addTeacher(@RequestBody TeacherDTO teacherDTO) {
		return teacherService.addTeacher(teacherDTO);
	}

	@PutMapping("/rest/teachers/{teacherId}")
	@ApiOperation(value = "Updates teacher in database",
	notes = "All fields must be filled in", response = Teacher.class)
	public Teacher updateTeacher(@RequestBody TeacherDTO teacherDTO, @PathVariable("teacherId") int teacherId) {
		return teacherService.updateTeacher(teacherDTO, teacherId);
	}

	@GetMapping("/rest/teachers")
	@ApiOperation(value = "Get list of all teachers")
	public List<Teacher> getTeachers() {
		return teacherService.getTeachers();
	}

	@GetMapping("/rest/teachers/{id}")
	@ApiOperation(value = "Find teacher by id", response = TeacherDTO.class)
	public TeacherDTO getTeacher(@PathVariable("id") int id) {
		return teacherService.getTeacher(id);
	}

	@DeleteMapping("/rest/teachers/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete teacher")
	public void deleteTeacher(@PathVariable("id") int id) {
		teacherService.deleteTeacher(id);
	}
}
