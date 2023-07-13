package com.petpic.petpicserv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer ID;
    String name;
    String fileUrl;
    String contentType;
    String description;
    @JsonIgnore
    private String fileHashName;

    public Pet(String name, String description, String fileHashName, String contentType) {
        this.setName(name);
        this.setDescription(description);
        this.fileHashName = fileHashName;
        this.setContentType(contentType);
    }

}
