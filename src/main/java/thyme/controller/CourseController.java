package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.Course;
import thyme.model.dto.CourseDTO;
import thyme.service.CourseService;

@Controller
public class CourseController {

	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/courses")
	public String getCourses(Model model) {

		model.addAttribute("courseList", courseService.getCourses());
		return "course/courses";
	}

	@GetMapping("/showNewCourseForm")
	public String showNewForm(Model model) {
		model.addAttribute("courseDTO", new CourseDTO());
		return "course/new_course";
	}

	@PostMapping("/saveCourse")
	public String saveCourse(@ModelAttribute("courseDTO") CourseDTO courseDTO) {
		if (courseDTO.getId() == null) {
			courseService.addCourse(courseDTO);
		} else {
			courseService.updateCourse(courseDTO, courseDTO.getId());
		}
		return "redirect:/courses";
	}

	@GetMapping("/showUpdateCourseForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		Course course = courseService.getCourse(id).get();
		model.addAttribute("course", course);
		return "course/update_course";
	}
	
	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable(value = "id") int id, Model model) {
		courseService.deleteCourse(id);
		return "redirect:/courses";
	}
}
