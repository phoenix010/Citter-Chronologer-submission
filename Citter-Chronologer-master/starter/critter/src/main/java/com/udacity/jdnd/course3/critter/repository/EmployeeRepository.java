package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query( value="select id from Employee h WHERE :name = #{name}",nativeQuery = true)
    Long getIdbyName(String name);
    List<Employee> findAllByDaysAvailableContaining(DayOfWeek day);

    Employee findByName(String name);




}
