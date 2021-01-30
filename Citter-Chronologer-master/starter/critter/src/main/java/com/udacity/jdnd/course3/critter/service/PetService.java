package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {
    Pet savePet(Pet pet);
    Pet getPetById(long id);
    List<Pet> getAllPets();
    List<Pet> getAllByOwnerId(long ownerId);
    //    Long getPetIdByName(String name);
    List<Pet> getAllPetsByIds(List <Long> ids);
    Boolean isPetInDB(long id);
}
