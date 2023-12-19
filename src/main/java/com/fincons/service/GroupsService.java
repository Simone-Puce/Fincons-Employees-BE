package com.fincons.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GroupsService<Groups, GroupsDTO> {
    Groups createGroups (GroupsDTO groupsDTO);
    List<GroupsDTO> getAllGroups();
    GroupsDTO getGroupsById (Long id);
    Groups addGroups(GroupsDTO newGroups);
    ResponseEntity<Groups> updateGroups (Long id, Groups groupsDetails);
    ResponseEntity<Map<String, Boolean>> deleteById (Long id);

}
