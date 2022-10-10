package com.example.entities.joinTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class JoinProCat {

    @Id
    private Long pid;
    private Long cid;
    private String title;
    private String price;
    private String categoryName;
    private String description;
}
