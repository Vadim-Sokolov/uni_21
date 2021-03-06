package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thyme.model.dto.CourseDTO;
import thyme.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseControllerThyme {

	private CourseService courseService;

	public CourseControllerThyme(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("")
	public String getCourses(Model model) {

		model.addAttribute("courseList", courseService.getCourses());
		return "course/courses";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("courseDTO", new CourseDTO());
		return "course/new-course";
	}

	@PostMapping("/save")
	public String saveCourse(@ModelAttribute("courseDTO") CourseDTO courseDTO) {
		if (courseDTO.getId() == null) {
			courseService.addCourse(courseDTO);
		} else {
			courseService.updateCourse(courseDTO, courseDTO.getId());
		}
		return "redirect:/courses";
	}

	@GetMapping("/updateForm")
	public String showUpdateForm(@RequestParam("id") Integer id, Model model) {
		CourseDTO courseDTO = courseService.getCourse(id);
		model.addAttribute("course", courseDTO);
		return "course/update-course";
	}
	
	@GetMapping("/delete")
	public String deleteCourse(Integer id) {
		courseService.deleteCourse(id);
		return "redirect:/courses";
	}
}
