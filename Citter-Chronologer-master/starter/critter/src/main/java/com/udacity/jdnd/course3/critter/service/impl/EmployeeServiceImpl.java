package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if(isNullOrEmpty(employee.getName().trim())){
            throw new ObjectNotFoundException("Please type your name");
        }
        return  employeeRepository.save(employee);
    }

    private static boolean isNullOrEmpty(String str){
        if(str !=null && !str.isEmpty())
            return false;
        else
            return true;
    }


    @Override
    public Boolean isEmployeeInDB(Long Id){
        //id is null
        try{
            Optional<Employee>employee  = employeeRepository.findById(Id);
            if(employee.isPresent()){
                return true;
            }else if(employee.isEmpty()){
                return false;
            }
        }catch (Exception ex){
            //exception may happen if Id(in method parameter) is null.
            return true;
        }

        return true;
    }

    @Override
    public Employee getEmployeeById(long employeeId) {
        Optional<Employee>employee  = employeeRepository.findById(employeeId);
        if(employee.isPresent()){
            return employee.get();
        }else{
            throw new ObjectNotFoundException("Employee not found");
        }
    }


    @Override
    public List<Employee> getAvailableEmployees(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(dayOfWeek);
        List<Employee> availableEmployees = new ArrayList<>();
        for(Employee e : employees){
            if(e.getSkills().containsAll(skills)) {
                availableEmployees.add(e);
            }
        }
        return availableEmployees;
    }

    @Override
    public Long getEmployeeIdbyName(String name) {
        return employeeRepository.getIdbyName(name);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
       Employee employee1 = getEmployeeById(employee.getId());
       return employeeRepository.save(employee1);
    }


}
