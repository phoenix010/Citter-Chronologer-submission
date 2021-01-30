package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Boolean isEmployeeInDB(Long Id);
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(long employeeId);
    List<Employee> getAvailableEmployees(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);
    Long getEmployeeIdbyName(String name);
    Employee updateEmployee(Employee employee);

}
