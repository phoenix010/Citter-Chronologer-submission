package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SCHEDULE_ID", nullable = false,unique = true)
    private long id;

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", activities=" + activities +
                ", date=" + date +
                ", employees=" + employees +
                ", pets=" + pets +
                '}';
    }

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITIES", length = 500)
    @JsonDeserialize(as = EmployeeSkill.class)
    private Set<EmployeeSkill> activities = new HashSet<>();

    @Column(name="SCHEDULE_DATE")
    @JsonDeserialize(as = LocalDate.class)
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "SCHEDULE_EMPLOYEE", joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "SCHEDULE_PET", joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PET_ID"))
    private List<Pet> pets = new ArrayList<>();


    public Schedule() {

    }

    public Schedule(Set<EmployeeSkill> activities, LocalDate date, List<Employee> employees, List<Pet> pets) {
//        this.id = id;
        this.activities = activities;
        this.date = date;
        this.employees = employees;
        this.pets = pets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }



}
