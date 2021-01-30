    package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
//
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "PET")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PET_ID", nullable = false,unique = true)
    private Long id;

    @Nationalized
    @Column(name="NAME",nullable = false, length = 255)
    private String name;


    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer owner;

    @Enumerated(EnumType.STRING)
    @Column(name="PET_TYPE", length = 255)
    private PetType type;

    @Column(name="BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "NOTES",length = 255)
    private String notes;
    public Pet() {}


    public Pet(String name, Customer owner, PetType type, LocalDate birthDate, String notes) {
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.birthDate = birthDate;
        this.notes = notes;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


}
