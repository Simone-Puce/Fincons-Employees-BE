package com.fincons.utility;

import com.fincons.entity.Role;
import com.fincons.enums.RoleEndpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endpoint {

    public String path;

    public List<RoleEndpoint> roles;

}
