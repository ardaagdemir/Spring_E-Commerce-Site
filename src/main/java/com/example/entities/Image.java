package com.example.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iid;

    private Long pid;
    @Lob
    private String file;
}
