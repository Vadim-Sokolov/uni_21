package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/thyme")
	public String goThyme() {
		return "thyme-index";
	}
	
	@GetMapping("/rest")
	public String goRest() {
		return "rest/rest-index";
	}
	
	@GetMapping("/rest/auditorium")
	public String restAuditorium() {
		return "rest/auditorium";
	}
	
	@GetMapping("/rest/course")
	public String restCourse() {
		return "rest/course";
	}
	
	@GetMapping("/rest/faculty")
	public String restFaculty() {
		return "rest/faculty";
	}
	
	@GetMapping("/rest/group")
	public String restGroup() {
		return "rest/group";
	}
	
	@GetMapping("/rest/lecture")
	public String restLecture() {
		return "rest/lecture";
	}
	
	@GetMapping("/rest/student")
	public String restStudent() {
		return "rest/student";
	}
	
	@GetMapping("/rest/teacher")
	public String restTeacher() {
		return "rest/teacher";
	}
}
