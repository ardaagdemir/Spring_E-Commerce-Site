package com.example.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Product extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer pid;

    @NotNull(message = "Category cannot be empty.!")
    private Integer cid;

    @NotBlank(message = "Title cannot be empty.!")
    @Length(message = "Product name must contain min 2 max  50 character.", min = 2, max = 50)
    private String title;

    @NotBlank(message = "Price cannot be empty.!")
    private String price;

    @Column(length = 999)

    private String description;
}
