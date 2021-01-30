package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;
    private List<String> petNames;

public CustomerDTO(){}



    public CustomerDTO(long id,String name, String phoneNumber, String notes) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;

    }

    public List<String> getPetNames() {
        return petNames;
    }

    public void setPetNames(List<String> petNames) {
        this.petNames = petNames;
    }
//    public long getId() {

//    public Pet getPet() {
//        return pet;
//    }
//
//    public void setPet(Pet pet) {
//        this.pet = pet;
//    }
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", petIds=" + petIds +
                ", petNames=" + petNames +
                '}';
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
