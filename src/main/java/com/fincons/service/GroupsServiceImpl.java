package com.fincons.service;

import com.fincons.entity.Groups;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.GroupsMapper;
import com.fincons.models.GroupsDTO;
import com.fincons.repository.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class GroupsServiceImpl implements GroupsService<Groups, GroupsDTO>{
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    GroupsMapper groupsMapper;
    @Override
    public Groups createGroups(GroupsDTO groupsDTO) {
        Groups groups = groupsMapper.mapGroupsDTOToGroups(groupsDTO);
        Groups newgroups = groupsRepository.save (groups);
        return groupsRepository.save(newgroups);
    }

    @Override
    public List<GroupsDTO> getAllGroups() {
        List<Groups> groupsList = groupsRepository.findAll();
        return groupsMapper.mapGroupsListToGroupsDTOList(groupsList);
    }
    @Override
    public GroupsDTO getGroupsById(Long id) {
        Optional<Groups> optionalGroups = groupsRepository.findById(id);
        return null;
    }

    @Override
    public Groups addGroups(GroupsDTO newGroups) {
        Groups groups = groupsMapper.mapGroupsDTOToGroups(newGroups);
        return groupsRepository.save(groups);
    }

    @Override
    public ResponseEntity<Groups> updateGroups(Long id, Groups groupsDetails) {
        Groups groups = groupsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Groups not exists with id: " + id));
        groups.setDescription(groupsDetails.getDescription());
        groups.setRole(groupsDetails.getRole());
        Groups updateGroups = groupsRepository.save(groups);
        return ResponseEntity.ok(updateGroups);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteById(Long id) {
        Groups groups = groupsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group not exists with id: " + id ));
        groupsRepository.delete(groups);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
