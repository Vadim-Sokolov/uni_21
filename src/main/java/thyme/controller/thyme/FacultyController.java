package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;

@Controller
@RequestMapping("/facultys")
public class FacultyController {

	private FacultyService facultyService;

	public FacultyController(FacultyService facultyService) {
		this.facultyService = facultyService;
	}

	@GetMapping("")
	public String getFacultys(Model model) {

		model.addAttribute("facultyList", facultyService.getFacultys());
		return "faculty/facultys";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("facultyDTO", new FacultyDTO());
		return "faculty/new-faculty";
	}

	@PostMapping("/save")
	public String saveFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO) {
		if (facultyDTO.getId() == null) {
			facultyService.addFaculty(facultyDTO);
		} else {
			facultyService.updateFaculty(facultyDTO, facultyDTO.getId());
		}
		return "redirect:/facultys";
	}

	@GetMapping("/updateForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		FacultyDTO facultyDTO = facultyService.getFaculty(id);
		model.addAttribute("faculty", facultyDTO);
		return "faculty/update-faculty";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteFaculty(@PathVariable(value = "id") int id) {
		facultyService.deleteFaculty(id);
		return "redirect:/facultys";
	}
}
