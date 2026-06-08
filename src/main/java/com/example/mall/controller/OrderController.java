package com.example.mall.controller;

import com.example.mall.common.Result;
import com.example.mall.entity.Order;
import com.example.mall.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 下单
    @PostMapping
    public Result<Order> create(@RequestBody Order order) {
        order.setCreateTime(LocalDateTime.now());
        orderRepository.save(order);
        return Result.success(order);
    }

    // 查看用户的订单
    @GetMapping
    public Result<List<Order>> list(@RequestParam Integer userId) {
        return Result.success(orderRepository.findByUserIdOrderByCreateTimeDesc(userId));
    }
}
