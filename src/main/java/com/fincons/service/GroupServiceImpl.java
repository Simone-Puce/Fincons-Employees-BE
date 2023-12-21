package com.fincons.service;

import com.fincons.entity.Group;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.GroupMapper;
import com.fincons.models.GroupDTO;
import com.fincons.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class GroupServiceImpl implements GroupService<Group, GroupDTO> {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMapper groupMapper;
    @Override
    public Group createGroups(GroupDTO groupDTO) {
        Group group = groupMapper.mapGroupsDTOToGroups(groupDTO);
        Group newgroups = groupRepository.save (group);
        return groupRepository.save(newgroups);
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groupList = groupRepository.findAll();
        return groupMapper.mapGroupsListToGroupsDTOList(groupList);
    }
    @Override
    public GroupDTO getGroupsById(Long id) {
        Optional<Group> optionalGroups = groupRepository.findById(id);
        return null;
    }

    @Override
    public Group addGroups(GroupDTO newGroups) {
        Group group = groupMapper.mapGroupsDTOToGroups(newGroups);
        return groupRepository.save(group);
    }

    @Override
    public ResponseEntity<Group> updateGroups(Long id, Group groupDetails) {
        Group group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Groups not exists with id: " + id));
        group.setDescription(groupDetails.getDescription());
        group.setRole(groupDetails.getRole());
        Group updateGroup = groupRepository.save(group);
        return ResponseEntity.ok(updateGroup);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group not exists with id: " + id ));
        groupRepository.delete(group);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
