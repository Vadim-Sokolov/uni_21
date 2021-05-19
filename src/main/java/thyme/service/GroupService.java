package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Group;
import thyme.model.dto.GroupDTO;
import thyme.repository.FacultyRepository;
import thyme.repository.GroupRepository;
import thyme.service.dtoconverter.GroupDtoConverter;

@Service
public class GroupService {

	private GroupRepository groupRepository;
	private FacultyRepository facultyRepository;
	private GroupDtoConverter groupDtoConverter;
	private static final String INVALID_GROUP_ID = "Invalid group Id";
	private static final String INVALID_FACULTY_ID = "Invalid faculty Id";

	public GroupService(GroupRepository groupRepository, FacultyRepository facultyRepository,
			GroupDtoConverter groupDtoConverter) {
		this.groupRepository = groupRepository;
		this.facultyRepository = facultyRepository;
		this.groupDtoConverter = groupDtoConverter;
	}

	public Group addGroup(GroupDTO groupDTO) {
		Group groupToSave = groupDtoConverter.toEntity(groupDTO);
		if (facultyRepository.findById(groupDTO.getFacultyId()).isEmpty()) {
			throw new ServiceException(INVALID_FACULTY_ID);
		} else {
			return groupRepository.save(groupToSave);
		}
	}

	public Group updateGroup(GroupDTO groupDTO, int groupId) {
		Group groupToSave = groupDtoConverter.toEntity(groupDTO);
		groupToSave.setId(groupId);
		if (groupRepository.findById(groupId).isEmpty()) {
			throw new ServiceException(INVALID_GROUP_ID);
		} else if (facultyRepository.findById(groupDTO.getFacultyId()).isEmpty()) {
			throw new ServiceException(INVALID_FACULTY_ID);
		} else {
			return groupRepository.save(groupToSave);
		}
	}

	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	public GroupDTO getGroup(int id) {
		Optional<Group> optional = groupRepository.findById(id);
		if (optional.isPresent()) {
			return groupDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_GROUP_ID);
		}
	}

	public void deleteGroup(int id) {
		groupRepository.deleteById(id);
	}

}
