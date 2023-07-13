package com.petpic.petpicserv.repository;

import com.petpic.petpicserv.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    Optional<Pet> findByID(Integer ID);

}