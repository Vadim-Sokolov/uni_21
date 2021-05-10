package thyme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import thyme.model.Teacher;
import thyme.service.TeacherService;
import thyme.model.dto.TeacherDTO;

@RestController
public class TeacherController {

	private TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@PostMapping("/teachers")
	@ResponseStatus(HttpStatus.CREATED)
	public Teacher addTeacher(@RequestBody TeacherDTO teacherDTO) {
		return teacherService.addTeacher(teacherDTO);
	}

	@PutMapping("/teachers/{teacherId}")
	public Teacher updateTeacher(@RequestBody TeacherDTO teacherDTO, @PathVariable("teacherId") int teacherId) {
		return teacherService.updateTeacher(teacherDTO, teacherId);
	}

	@GetMapping("/teachers")
	public List<Teacher> getTeachers() {
		return teacherService.getTeachers();
	}

	@GetMapping("/teachers/{id}")
	public Optional<Teacher> getTeacher(@PathVariable("id") int id) {
		return teacherService.getTeacher(id);
	}

	@DeleteMapping("/teachers/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTeacher(@PathVariable("id") int id) {
		teacherService.deleteTeacher(id);
	}
}
