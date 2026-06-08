package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String username;

    // 把购物车的商品信息拼成字符串存起来
    @Column(length = 1000)
    private String items;

    private Double totalPrice;

    private LocalDateTime createTime;
}
