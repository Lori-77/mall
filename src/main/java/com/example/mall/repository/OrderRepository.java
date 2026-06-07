package com.example.mall.repository;

import com.example.mall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserIdOrderByCreateTimeDesc(Integer userId);
}
