package com.App.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Employee {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @NotNull
	 private int id;
     @NotBlank(message = "cannot be blank")
     private String name;
     @NotBlank(message = "cannot be blank")
     @Size(message = "*",min = 10,max = 10)
     private String contact;
     @NotBlank(message = "cannot be blank")
     private String email;
     @NotBlank(message = "cannot be blank")
     private String department;
     @NotBlank(message = "cannot be blank")
     private String password;
     @NotBlank
     private String role;
}
