package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thyme.model.dto.LectureDTO;
import thyme.service.LectureService;

@Controller
@RequestMapping("/lectures")
public class LectureControllerThyme {

	private LectureService lectureService;

	public LectureControllerThyme(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@GetMapping("")
	public String getLectures(Model model) {
		model.addAttribute("lectureList", lectureService.getLectures());
		return "lecture/lectures";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("lectureDTO", new LectureDTO());
		return "lecture/new-lecture";
	}

	@PostMapping("/save")
	public String saveLecture(@ModelAttribute("lectureDTO") LectureDTO lectureDTO) {
		if (lectureDTO.getId() == null) {
			lectureService.addLecture(lectureDTO);
		} else {
			lectureService.updateLecture(lectureDTO, lectureDTO.getId());
		}
		return "redirect:/lectures";
	}

	@GetMapping("/updateForm")
	public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
		LectureDTO lectureDTO = lectureService.getLecture(id);
		model.addAttribute("lecture", lectureDTO);
		return "lecture/update-lecture";
	}

	@GetMapping("/delete")
	public String deleteLecture(Integer id) {
		lectureService.deleteLecture(id);
		return "redirect:/lectures";
	}
}
