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

import thyme.model.Student;
import thyme.service.StudentService;
import thyme.model.dto.StudentDTO;

@RestController
public class StudentControllerRest {

	private StudentService studentService;

	public StudentControllerRest(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/rest/students")
	@ResponseStatus(HttpStatus.CREATED)
	public Student addStudent(@RequestBody StudentDTO studentDTO) {
		return studentService.addStudent(studentDTO);
	}

	@PutMapping("/rest/students/{studentId}")
	public Student updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable("studentId") int studentId) {
		return studentService.updateStudent(studentDTO, studentId);
	}

	@GetMapping("/rest/students")
	public List<Student> getStudents() {
		return studentService.getStudents();
	}

	@GetMapping("/rest/students/{id}")
	public StudentDTO getStudent(@PathVariable("id") int id) {
		return studentService.getStudent(id);
	}

	@DeleteMapping("/rest/students/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStudent(@PathVariable("id") int id) {
		studentService.deleteStudent(id);
	}
}
