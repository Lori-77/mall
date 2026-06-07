package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private Integer stock;

    private String image;

    @Column(length = 500)
    private String description;

    // 1=上架  0=下架
    private Integer status = 1;
}
