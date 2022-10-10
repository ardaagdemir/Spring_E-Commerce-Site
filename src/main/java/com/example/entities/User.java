package com.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @NotBlank(message = "First name cannot be empty!")
    @Length(message = "First name  must contain min 2 max 50 character.", min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @Length(message = "Last name  must contain min 2 max 50 character.", min = 2, max = 50)
    private String lastName;

    @Email(message = "Email Error")
    @Length(message = "Maximum 60", max = 60)
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @NotNull(message = "Password cannot be empty!")
    @Pattern(message = "Password must contain min one upper,lower letter and 0-9 digit number ", regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})")
    private String password;

    @Length(message = "Telephone must contain min 10 max 50 character.", min = 2, max = 50)
    private String phone;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private List<Role> roles;
}
