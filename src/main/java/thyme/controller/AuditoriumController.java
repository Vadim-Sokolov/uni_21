package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import thyme.model.dto.AuditoriumDTO;
import thyme.service.AuditoriumService;

@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

	private AuditoriumService auditoriumService;

	public AuditoriumController(AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	@GetMapping("")
	public String getAuditoriums(Model model) {

		model.addAttribute("auditoriumList", auditoriumService.getAuditoriums());
		return "auditorium/auditoriums";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("auditoriumDTO", new AuditoriumDTO());
		return "auditorium/new-auditorium";
	}

	@PostMapping("/save")
	public String saveAuditorium(@ModelAttribute("auditoriumDTO") AuditoriumDTO auditoriumDTO) {
		if (auditoriumDTO.getId() == null) {
			auditoriumService.addAuditorium(auditoriumDTO);
		} else {
			auditoriumService.updateAuditorium(auditoriumDTO, auditoriumDTO.getId());
		}
		return "redirect:/auditoriums";
	}

	@GetMapping("/updateForm")
	public String showUpdateForm(Integer id, Model model) {
		AuditoriumDTO auditoriumDTO = auditoriumService.getAuditorium(id);
		model.addAttribute("auditorium", auditoriumDTO);
		return "auditorium/update-auditorium";
	}
	
	@GetMapping("/delete")
	public String deleteAuditorium(Integer id) {
		auditoriumService.deleteAuditorium(id);
		return "redirect:/auditoriums";
	}
}
