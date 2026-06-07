package com.example.mall.repository;

import com.example.mall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    // 按名称模糊搜索
    List<Product> findByNameContaining(String keyword);

    // 按状态查（上架/下架）
    List<Product> findByStatus(Integer status);
}
