package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.dto.AuditoriumDTO;
import thyme.service.AuditoriumService;

@Controller
public class AuditoriumController {

	private AuditoriumService auditoriumService;

	public AuditoriumController(AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	@GetMapping("/auditoriums")
	public String getAuditoriums(Model model) {

		model.addAttribute("auditoriumList", auditoriumService.getAuditoriums());
		return "auditorium/auditoriums";
	}

	@GetMapping("/showNewAuditoriumForm")
	public String showNewForm(Model model) {
		model.addAttribute("auditoriumDTO", new AuditoriumDTO());
		return "auditorium/new_auditorium";
	}

	@PostMapping("/saveAuditorium")
	public String saveAuditorium(@ModelAttribute("auditoriumDTO") AuditoriumDTO auditoriumDTO) {
		if (auditoriumDTO.getId() == null) {
			auditoriumService.addAuditorium(auditoriumDTO);
		} else {
			auditoriumService.updateAuditorium(auditoriumDTO, auditoriumDTO.getId());
		}
		return "redirect:/auditoriums";
	}

	@GetMapping("/showUpdateAuditoriumForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		AuditoriumDTO auditoriumDTO = auditoriumService.getAuditorium(id);
		model.addAttribute("auditorium", auditoriumDTO);
		return "auditorium/update_auditorium";
	}
	
	@GetMapping("/deleteAuditorium/{id}")
	public String deleteAuditorium(@PathVariable(value = "id") int id, Model model) {
		auditoriumService.deleteAuditorium(id);
		return "redirect:/auditoriums";
	}
}
