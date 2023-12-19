package com.fincons.mapper;

import com.fincons.entity.Groups;
import com.fincons.models.GroupsDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class GroupsMapper {
    public GroupsDTO mapGroupsToGroupDTO(Groups groups) {
        GroupsDTO groupsDTO = new GroupsDTO(groups.getDescription(), groups.getRole());
        return groupsDTO;
    }
    public List<GroupsDTO> mapGroupsListToGroupsDTOList (List<Groups> groupsList){
        List<GroupsDTO> groupsDTOList = new ArrayList<GroupsDTO>();
        for (int i=0; i<groupsList.size(); i ++){
            GroupsDTO groupsDTO = mapGroupsToGroupDTO(groupsList.get(i));
            groupsDTOList.add(groupsDTO);
        }
        return groupsDTOList;
    }
    public Groups mapGroupsDTOToGroups (GroupsDTO groupsDTO) {
        Groups groups = new Groups();
        return groups;
    }
    public List<Groups> mapGroupsDTOListToGroupsList (List<GroupsDTO> groupsDTOList){
        List<Groups> groupsList = new ArrayList<Groups>();
        for (int i = 0; i< groupsList.size(); i ++){
            Groups groups = mapGroupsDTOToGroups(groupsDTOList.get(i));
            groupsList.add(groups);
        }
        return groupsList;
    }
}
