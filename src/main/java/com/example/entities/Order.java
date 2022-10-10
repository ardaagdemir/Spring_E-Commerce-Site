package com.example.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "orders")
public class Order extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;
    private Integer uid;
    private Integer pid;

}
