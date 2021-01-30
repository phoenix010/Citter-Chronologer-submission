package com.udacity.jdnd.course3.critter.pet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.service.impl.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.impl.PetServiceImpl;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    PetServiceImpl petService;
    CustomerServiceImpl customerService;
    private static final Logger LOGGER = LogManager.getLogger(PetController.class);

    @Autowired
    public PetController(PetServiceImpl petService, CustomerServiceImpl customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        LOGGER.info("Saving Pets");
        System.out.println("working on PET POST REQ");
        Pet pet = convertPetDTOtoEntity(petDTO);
        Pet savedPet = petService.savePet(pet); //error
        return convertEntityToPetDTO(savedPet);
    }


    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertEntityToPetDTO(pet);
    }


    @GetMapping
    public List<PetDTO> getPets(){
        System.out.println("Get All Pets");
        List <Pet> petList = petService.getAllPets();
        return petList.stream().map(x->convertEntityToPetDTO(x)).collect(Collectors.toList());
    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        System.out.println("Get All the pets by ownerID");
        Customer owner = customerService.findById(ownerId);
        return owner.getPets().stream().map(x->convertEntityToPetDTO(x)).collect(Collectors.toList());
    }

    private Pet convertPetDTOtoEntity(PetDTO petDTO){
        String petName = petDTO.getName();
        PetType petType = petDTO.getType();
        long ownerId = petDTO.getOwnerId();
        Customer owner = customerService.findById(ownerId);
        LocalDate birthDate = petDTO.getBirthDate();
        String notes = petDTO.getNotes();
        return new Pet(petName,owner,petType,birthDate,notes);
    }
    private PetDTO convertEntityToPetDTO(Pet pet){
        PetType petType = pet.getType();
        String name = pet.getName();
        long ownerId = pet.getOwner().getId();
        LocalDate birthDate = pet.getBirthDate();
        String notes = pet.getNotes();
        return new PetDTO(pet.getId(),petType,name,ownerId,birthDate,notes);
     }
}
