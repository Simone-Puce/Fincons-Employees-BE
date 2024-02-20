package com.fincons.utilityTest;

import com.fincons.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentBuilder {
    public static List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, "code1", "name1", "address1", "city1"));
        departments.add(new Department(2L, "code2", "name2", "address2", "city2"));
        departments.add(new Department(3L, "code3", "name3", "address3", "city3"));
        return departments;
    }
    public static Department getDepartment() {
        return new Department(3L, "code1", "name1", "address1", "city1");
    }
    public static Department getDepartmentWithoutId() {
        return new Department("code1", "name1", "address1", "city1");
    }
    public static List<Department> getDepartmentsEmpty() {
        return new ArrayList<>();
    }
}
