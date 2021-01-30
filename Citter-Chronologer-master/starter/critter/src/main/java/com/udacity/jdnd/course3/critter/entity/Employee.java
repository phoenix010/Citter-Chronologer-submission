package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "DAYS_AVAILABLE", length = 500)
    private Set<DayOfWeek> daysAvailable;

    public Employee(){}

    public Employee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    @Override
    public String toString() {
        return "Employee [daysAvailable=" + daysAvailable + ", id=" + id + ", name=" + name + ", skills=" + skills
                + "]";
    }



}
