package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Customer extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(message = "Firstname must be min 2, max 50 character", min = 2, max = 50)
    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "Lastname cannot be empty")
    @Length(message = "Firstname must be min 2, max 50 character", min = 2, max = 50)
    private String lastName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    @Length(message = "Email must be min 2, max 60 character", min = 2, max = 60)
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;



}
