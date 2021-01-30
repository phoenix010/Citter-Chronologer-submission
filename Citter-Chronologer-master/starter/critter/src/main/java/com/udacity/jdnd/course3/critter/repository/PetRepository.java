package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value = "select id from Pet h WHERE :name = #{name}",nativeQuery = true)
    Long findIdbyName(String name);


//    @Query("select p from Pet p where :owner member of p.owner")
//    Pet getPetByOwner(@Param("owner") Customer customer);







    //    @Query("select p from Pet p where :owner member of p.owner")
//    String getNameByOwnerId(Long ownerId);
//    @Query("select p from Pet p where :owner member of p.owner")
//    LocalDate getBirthDateByOwnerID(Long ownerId);

    /*
    //you can provide specific JPQL Queries
   @Query("select h from Humanoid h where :outfit member of h.outfits ")
   List<Humanoid> findAllByOutfit(@Param("outfit") Outfit outfit);
     */
}

