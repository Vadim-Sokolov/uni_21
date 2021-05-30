package thyme.controller.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import thyme.model.dto.GroupDTO;
import thyme.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("")
	public String getGroups(Model model) {

		model.addAttribute("groupList", groupService.getGroups());
		return "group/groups";
	}

	@GetMapping("/newForm")
	public String showNewForm(Model model) {
		model.addAttribute("groupDTO", new GroupDTO());
		return "group/new-group";
	}

	@PostMapping("/save")
	public String saveGroup(@ModelAttribute("groupDTO") GroupDTO groupDTO) {
		if (groupDTO.getId() == null) {
			groupService.addGroup(groupDTO);
		} else {
			groupService.updateGroup(groupDTO, groupDTO.getId());
		}
		return "redirect:/groups";
	}

	@GetMapping("/updateForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		GroupDTO groupDTO = groupService.getGroup(id);
		model.addAttribute("group", groupDTO);
		return "group/update-group";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteGroup(@PathVariable(value = "id") int id) {
		groupService.deleteGroup(id);
		return "redirect:/groups";
	}
}
