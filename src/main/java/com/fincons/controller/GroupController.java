package com.fincons.controller;

import com.fincons.entity.Group;
import com.fincons.models.GroupDTO;
import com.fincons.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    GroupService<Group, GroupDTO> groupService;
    @GetMapping("/groups")
    public List<GroupDTO> getAllGroups () {
        return groupService.getAllGroups();
    }
    @PostMapping ("/groups/{id}")
    public Group createGroups (@RequestBody GroupDTO groupDTO) {
        return groupService.createGroups(groupDTO);
    }
    @GetMapping ("/groups/{id}")
    public GroupDTO getGroupsById (@PathVariable Long id){
        return groupService.getGroupsById(id);
    }
    @PutMapping("/groups/{id}")
    public ResponseEntity<Group> updateGroups (@PathVariable Long id, @RequestBody Group groupDetails) {
        return groupService.updateGroups(id, groupDetails);
    }
    @DeleteMapping ("/groups/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById (@PathVariable Long id) {
        return groupService.deleteById(id);
    }
}