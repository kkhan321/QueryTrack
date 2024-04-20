package com.App.dto;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Query {
	
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @NotNull
	 private int id;
     @NotBlank(message = "cannot be blank")
     private String writeQuery;
    // @NotBlank(message = "cannot be blank")
     private LocalDateTime createdAt;
     @NotBlank(message = "cannot be blank")
     private String status;
     @ManyToOne
     @NotNull
     private Employee emp;
     @ManyToOne
     @NotNull
     private MyUser myUser;
     
     
	

}
