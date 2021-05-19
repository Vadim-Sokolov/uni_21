package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.dto.TeacherDTO;
import thyme.service.TeacherService;

@Controller
public class TeacherController {

	private TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@GetMapping("/teachers")
	public String getTeachers(Model model) {
		model.addAttribute("teacherList", teacherService.getTeachers());
		return "teacher/teachers";
	}

	@GetMapping("/showNewTeacherForm")
	public String showNewForm(Model model) {
		model.addAttribute("teacherDTO", new TeacherDTO());
		return "teacher/new_teacher";
	}

	@PostMapping("/saveTeacher")
	public String saveTeacher(@ModelAttribute("teacherDTO") TeacherDTO teacherDTO) {
		if (teacherDTO.getId() == null) {
			teacherService.addTeacher(teacherDTO);
		} else {
			teacherService.updateTeacher(teacherDTO, teacherDTO.getId());
		}
		return "redirect:/teachers";
	}

	@GetMapping("/showUpdateTeacherForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		TeacherDTO teacherDTO = teacherService.getTeacher(id);
		model.addAttribute("teacher", teacherDTO);
		return "teacher/update_teacher";
	}

	@GetMapping("/deleteTeacher/{id}")
	public String deleteTeacher(@PathVariable(value = "id") int id, Model model) {
		teacherService.deleteTeacher(id);
		return "redirect:/teachers";
	}
}
