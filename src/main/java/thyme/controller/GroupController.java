package thyme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import thyme.model.dto.GroupDTO;
import thyme.service.GroupService;

@Controller
public class GroupController {

	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/groups")
	public String getGroups(Model model) {

		model.addAttribute("groupList", groupService.getGroups());
		return "group/groups";
	}

	@GetMapping("/showNewGroupForm")
	public String showNewForm(Model model) {
		model.addAttribute("groupDTO", new GroupDTO());
		return "group/new_group";
	}

	@PostMapping("/saveGroup")
	public String saveGroup(@ModelAttribute("groupDTO") GroupDTO groupDTO) {
		if (groupDTO.getId() == null) {
			groupService.addGroup(groupDTO);
		} else {
			groupService.updateGroup(groupDTO, groupDTO.getId());
		}
		return "redirect:/groups";
	}

	@GetMapping("/showUpdateGroupForm/{id}")
	public String showUpdateForm(@PathVariable(value = "id") int id, Model model) {
		GroupDTO groupDTO = groupService.getGroup(id);
		model.addAttribute("group", groupDTO);
		return "group/update_group";
	}
	
	@GetMapping("/deleteGroup/{id}")
	public String deleteGroup(@PathVariable(value = "id") int id, Model model) {
		groupService.deleteGroup(id);
		return "redirect:/groups";
	}
}
