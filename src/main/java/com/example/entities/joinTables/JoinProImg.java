package com.example.entities.joinTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class JoinProImg {

    @Id
    private Long pid;
    private Long iid;
    private String title;
    private String price;
    private String categoryName;
    private String description;
    private String file;
}
