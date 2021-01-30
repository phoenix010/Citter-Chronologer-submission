package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    PetRepository petRepository;
    CustomerRepository customerRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Pet savePet(Pet pet) {
        Customer owner =  pet.getOwner();
        System.out.println("owner is: "+pet.getOwner());
        if(isPetInDB(pet,owner)){
            System.out.println("In exception block of savePet");
            throw new ObjectNotFoundException("Cannot add as this pet is already registered for this owner");
        }
        petRepository.save(pet);
        try{
            owner.getPets().add(pet);
            return pet;
        }catch(NullPointerException ex){ // this exception may arise when we are creating first pet for the owner.
            List<Pet> pets = new LinkedList<>();
            pets.add(pet);
            owner.setPets(pets);
        }
        return pet;

    }

    private boolean isPetInDB(Pet pet, Customer owner) {
        try{
            List<Pet> pets = owner.getPets();
            if (pets.isEmpty()) return false;
            for (Pet thisPet : pets) {
                if (thisPet.getName().equalsIgnoreCase(pet.getName()) && (thisPet.getType().equals(pet.getType()) && (thisPet.getBirthDate().equals(pet.getBirthDate())))) {
                    System.out.println("Pet is in the DB for this owner");
                    return true;
                }
            }
            return false;
        }catch(NullPointerException ex){
            System.out.println("It will catch null pointer exception as pets doesn't exist");
            return false;
        }

    }

    @Override
    public Pet getPetById(long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if(pet.isPresent()){
            return pet.get();
        }else{
            throw new ObjectNotFoundException("pet not found");
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> petList =  petRepository.findAll();
        if(petList.size()== 0){
            throw new ObjectNotFoundException("Pet List is zero");
        }
        return petList;
    }

    @Override
    public List<Pet> getAllByOwnerId(long ownerId) {
        return null;
    }

    @Override
    public List<Pet> getAllPetsByIds(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    @Override
    public Boolean isPetInDB(long id) {
        Optional<Pet>pet  = petRepository.findById(id);
        if(pet.isPresent()){
            return true;
        }else if(pet.isEmpty()){
            return false;
        }
        return false;
    }
}
