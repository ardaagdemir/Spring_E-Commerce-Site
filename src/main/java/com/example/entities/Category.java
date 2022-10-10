package com.example.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Category extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @NotBlank(message = "Category name cannot be empty!")
    @Length(message = "Category name must contain min 2 max 50 character.", min = 2, max = 50)
    private String categoryName;
}
