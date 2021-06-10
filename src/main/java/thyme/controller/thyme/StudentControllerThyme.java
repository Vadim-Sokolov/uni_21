package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thyme.model.dto.StudentDTO;
import thyme.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentControllerThyme {

	private StudentService studentService;

	public StudentControllerThyme(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("")
	public String getStudents(Model model) {
		model.addAttribute("studentList", studentService.getStudents());
		return "student/students";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("studentDTO", new StudentDTO());
		return "student/new-student";
	}

	@PostMapping("/save")
	public String saveStudent(@ModelAttribute("studentDTO") StudentDTO studentDTO) {
		if (studentDTO.getId() == null) {
			studentService.addStudent(studentDTO);
		} else {
			studentService.updateStudent(studentDTO, studentDTO.getId());
		}
		return "redirect:/students";
	}

	@GetMapping("/updateForm")
	public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
		StudentDTO studentDTO = studentService.getStudent(id);
		model.addAttribute("student", studentDTO);
		return "student/update-student";
	}

	@GetMapping("/delete")
	public String deleteStudent(Integer id) {
		studentService.deleteStudent(id);
		return "redirect:/students";
	}
}
