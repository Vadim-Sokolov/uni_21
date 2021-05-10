package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;

@Controller
public class FacultyController {

	private FacultyService facultyService;

	public FacultyController(FacultyService facultyService) {
		this.facultyService = facultyService;
	}

	@GetMapping("/facultys")
	public String getFacultys(Model model) {

		model.addAttribute("facultyList", facultyService.getFacultys());
		return "faculty/facultys";
	}

	@GetMapping("/showNewFacultyForm")
	public String showNewForm(Model model) {
		model.addAttribute("facultyDTO", new FacultyDTO());
		return "faculty/new_faculty";
	}

	@PostMapping("/saveFaculty")
	public String saveFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO) {
		if (facultyDTO.getId() == null) {
			facultyService.addFaculty(facultyDTO);
		} else {
			facultyService.updateFaculty(facultyDTO, facultyDTO.getId());
		}
		return "redirect:/facultys";
	}

	@GetMapping("/showUpdateFacultyForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		Faculty faculty = facultyService.getFaculty(id).get();
		model.addAttribute("faculty", faculty);
		return "faculty/update_faculty";
	}
	
	@GetMapping("/deleteFaculty/{id}")
	public String deleteFaculty(@PathVariable(value = "id") int id, Model model) {
		facultyService.deleteFaculty(id);
		return "redirect:/facultys";
	}
}
