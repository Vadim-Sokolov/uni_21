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
	@ApiOperation(value = "Method adds new student to database",
	notes = "All fields must be filled in", response = Student.class)
	public Student addStudent(@RequestBody StudentDTO studentDTO) {
		return studentService.addStudent(studentDTO);
	}

	@PutMapping("/rest/students/{studentId}")
	@ApiOperation(value = "Method updates student in database",
	notes = "All fields must be filled in", response = Student.class)
	public Student updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable("studentId") int studentId) {
		return studentService.updateStudent(studentDTO, studentId);
	}

	@GetMapping("/rest/students")
	@ApiOperation(value = "Method returns list of all students from database")
	public List<Student> getStudents() {
		return studentService.getStudents();
	}

	@GetMapping("/rest/students/{id}")
	@ApiOperation(value = "Method finds and returns student by id from database", response = StudentDTO.class)
	public StudentDTO getStudent(@PathVariable("id") int id) {
		return studentService.getStudent(id);
	}

	@DeleteMapping("/rest/students/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Method deletes student from database")
	public void deleteStudent(@PathVariable("id") int id) {
		studentService.deleteStudent(id);
	}
}
