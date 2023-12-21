package com.fincons.mapper;

import com.fincons.entity.Group;
import com.fincons.models.GroupDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class GroupMapper {
    public GroupDTO mapGroupsToGroupDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO(group.getDescription(), group.getRole());
        return groupDTO;
    }
    public List<GroupDTO> mapGroupsListToGroupsDTOList (List<Group> groupList){
        List<GroupDTO> groupDTOList = new ArrayList<GroupDTO>();
        for (int i = 0; i< groupList.size(); i ++){
            GroupDTO groupDTO = mapGroupsToGroupDTO(groupList.get(i));
            groupDTOList.add(groupDTO);
        }
        return groupDTOList;
    }
    public Group mapGroupsDTOToGroups (GroupDTO groupDTO) {
        Group group = new Group();
        return group;
    }
    public List<Group> mapGroupsDTOListToGroupsList (List<GroupDTO> groupDTOList){
        List<Group> groupList = new ArrayList<Group>();
        for (int i = 0; i< groupList.size(); i ++){
            Group group = mapGroupsDTOToGroups(groupDTOList.get(i));
            groupList.add(group);
        }
        return groupList;
    }
}
