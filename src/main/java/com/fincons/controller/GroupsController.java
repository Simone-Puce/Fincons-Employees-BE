package com.fincons.controller;

import com.fincons.entity.Groups;
import com.fincons.models.GroupsDTO;
import com.fincons.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/groups")
public class GroupsController {
    @Autowired
    GroupsService<Groups, GroupsDTO> groupsService;
    @GetMapping("/groups")
    public List<GroupsDTO> getAllGroups () {
        return groupsService.getAllGroups();
    }
    @PostMapping ("/groups/{id}")
    public Groups createGroups (@RequestBody GroupsDTO groupsDTO) {
        return groupsService.createGroups(groupsDTO);
    }
    @GetMapping ("/groups/{id}")
    public GroupsDTO getGroupsById (@PathVariable Long id){
        return groupsService.getGroupsById(id);
    }
    @PutMapping("/groups/{id}")
    public ResponseEntity<Groups> updateGroups (@PathVariable Long id, @RequestBody Groups groupsDetails) {
        return groupsService.updateGroups(id, groupsDetails);
    }
    @DeleteMapping ("/groups/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById (@PathVariable Long id) {
        return groupsService.deleteById(id);
    }
}