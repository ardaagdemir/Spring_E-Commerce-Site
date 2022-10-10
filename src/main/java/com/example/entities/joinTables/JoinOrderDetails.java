package com.example.entities.joinTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class JoinOrderDetails {

    @Id
    private Integer oid;
    private Integer uid;
    private Integer pid;

    private String firstName;
    private String lastName;
    private String title;
    private String price;
}
