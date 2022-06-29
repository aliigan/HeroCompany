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
public class Admin extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @NotBlank(message = "please enter a fistName")
    @Length(message = "fistName must be min 2, max  50 character", min = 2, max = 50)
    private String firstName;
    @NotBlank(message = "please enter a lastName")
    @Length(message = "lastName must be min 2, max  50 character", min = 2, max = 50)
    private String lastName;
    @NotBlank(message = "please enter a email")
    @Length(message = "email must be min 2, max  60 character", min = 2, max = 60)
    @Email(message = "invalid email format")
    private String email;
    //@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",message="length must be 3")
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

}
