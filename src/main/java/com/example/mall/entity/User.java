package com.example.mall.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    // 只写不读，前端传密码但查用户列表时不返回密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
