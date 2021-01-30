package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

@Entity
@Table(name="CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(name= "CUSTOMER_ID", nullable = false,length=255)
    private Long id;

    @Nationalized
    @Column(name= "CUSTOMER_NAME",nullable = false, length = 255)
    private String name;



    @Nationalized
    @Column(name= "CONTACT_NUMBER", length = 255)
    private String phoneNumber;

    @Nationalized
    @Column(name="NOTES", length = 512)
    private String notes;

//    @JsonIgnore
    @OneToMany( mappedBy = "owner",cascade = CascadeType.ALL)
    private List<Pet> pets;

    public Customer() { }

    public Customer(String name, String phoneNumber, String notes) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", pets=" + pets +
                '}';
    }
}
