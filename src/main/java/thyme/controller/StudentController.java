package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.dto.StudentDTO;
import thyme.service.StudentService;

@Controller
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/students")
	public String getStudents(Model model) {
		model.addAttribute("studentList", studentService.getStudents());
		return "student/students";
	}

	@GetMapping("/showNewStudentForm")
	public String showNewForm(Model model) {
		model.addAttribute("studentDTO", new StudentDTO());
		return "student/new_student";
	}

	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute("studentDTO") StudentDTO studentDTO) {
		if (studentDTO.getId() == null) {
			studentService.addStudent(studentDTO);
		} else {
			studentService.updateStudent(studentDTO, studentDTO.getId());
		}
		return "redirect:/students";
	}

	@GetMapping("/showUpdateStudentForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		StudentDTO studentDTO = studentService.getStudent(id);
		model.addAttribute("student", studentDTO);
		return "student/update_student";
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable(value = "id") int id, Model model) {
		studentService.deleteStudent(id);
		return "redirect:/students";
	}
}
