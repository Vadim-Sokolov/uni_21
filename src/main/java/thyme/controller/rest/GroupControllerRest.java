package thyme.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import thyme.model.Group;
import thyme.service.GroupService;
import thyme.model.dto.GroupDTO;

@RestController
public class GroupControllerRest {

	private GroupService groupService;

	public GroupControllerRest(GroupService groupService) {
		this.groupService = groupService;
	}

	@PostMapping("/rest/groups")
	@ResponseStatus(HttpStatus.CREATED)
	public Group addGroup(@RequestBody GroupDTO groupDTO) {
		return groupService.addGroup(groupDTO);
	}

	@PutMapping("/rest/groups/{groupId}")
	public Group updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable("groupId") int groupId) {
		return groupService.updateGroup(groupDTO, groupId);
	}

	@GetMapping("/rest/groups")
	public List<Group> getGroups() {
		return groupService.getGroups();
	}

	@GetMapping("/rest/groups/{id}")
	public GroupDTO getGroup(@PathVariable("id") int id) {
		return groupService.getGroup(id);
	}

	@DeleteMapping("/rest/groups/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroup(@PathVariable("id") int id) {
		groupService.deleteGroup(id);
	}
}
