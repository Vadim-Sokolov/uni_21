package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thyme.model.dto.TeacherDTO;
import thyme.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherControllerThyme {

	private TeacherService teacherService;

	public TeacherControllerThyme(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@GetMapping("")
	public String getTeachers(Model model) {
		model.addAttribute("teacherList", teacherService.getTeachers());
		return "teacher/teachers";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("teacherDTO", new TeacherDTO());
		return "teacher/new-teacher";
	}

	@PostMapping("/save")
	public String saveTeacher(@ModelAttribute("teacherDTO") TeacherDTO teacherDTO) {
		if (teacherDTO.getId() == null) {
			teacherService.addTeacher(teacherDTO);
		} else {
			teacherService.updateTeacher(teacherDTO, teacherDTO.getId());
		}
		return "redirect:/teachers";
	}

	@GetMapping("/updateForm")
	public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
		TeacherDTO teacherDTO = teacherService.getTeacher(id);
		model.addAttribute("teacher", teacherDTO);
		return "teacher/update-teacher";
	}

	@GetMapping("/delete")
	public String deleteTeacher(Integer id) {
		teacherService.deleteTeacher(id);
		return "redirect:/teachers";
	}
}
