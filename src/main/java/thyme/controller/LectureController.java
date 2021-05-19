package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.dto.LectureDTO;
import thyme.service.LectureService;

@Controller
public class LectureController {

	private LectureService lectureService;

	public LectureController(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@GetMapping("/lectures")
	public String getLectures(Model model) {
		model.addAttribute("lectureList", lectureService.getLectures());
		return "lecture/lectures";
	}

	@GetMapping("/showNewLectureForm")
	public String showNewForm(Model model) {
		model.addAttribute("lectureDTO", new LectureDTO());
		return "lecture/new_lecture";
	}

	@PostMapping("/saveLecture")
	public String saveLecture(@ModelAttribute("lectureDTO") LectureDTO lectureDTO) {
		if (lectureDTO.getId() == null) {
			lectureService.addLecture(lectureDTO);
		} else {
			lectureService.updateLecture(lectureDTO, lectureDTO.getId());
		}
		return "redirect:/lectures";
	}

	@GetMapping("/showUpdateLectureForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		LectureDTO lectureDTO = lectureService.getLecture(id);
		model.addAttribute("lecture", lectureDTO);
		return "lecture/update_lecture";
	}

	@GetMapping("/deleteLecture/{id}")
	public String deleteLecture(@PathVariable(value = "id") int id, Model model) {
		lectureService.deleteLecture(id);
		return "redirect:/lectures";
	}
}
