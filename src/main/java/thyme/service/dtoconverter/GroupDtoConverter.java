package thyme.service.dtoconverter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import thyme.model.Faculty;
import thyme.model.Group;
import thyme.model.dto.GroupDTO;
import thyme.repository.FacultyRepository;

@Component
public class GroupDtoConverter {

	private FacultyRepository facultyRepository;

	public GroupDtoConverter(FacultyRepository facultyRepository) {
		this.facultyRepository = facultyRepository;
	}

	public Group toEntity(GroupDTO groupDTO) {
		Group group = new Group();
		group.setId(groupDTO.getId());
		group.setName(groupDTO.getName());

		Optional<Faculty> facultySearchResult = facultyRepository.findById(groupDTO.getFacultyId());
		if (facultySearchResult.isPresent()) {
			Faculty facultyToSet = facultySearchResult.get();
			group.setFaculty(facultyToSet);
		}
		return group;
	}
	
	public GroupDTO toDTO(Group group) {
		GroupDTO groupDTO = new GroupDTO();
		groupDTO.setId(group.getId());
		groupDTO.setName(group.getName());
		groupDTO.setFacultyId(group.getFaculty().getId());
		return groupDTO;
	}

}
