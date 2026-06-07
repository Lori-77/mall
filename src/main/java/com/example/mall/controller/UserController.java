package com.example.mall.controller;

import com.example.mall.common.Result;
import com.example.mall.entity.User;
import com.example.mall.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 查所有用户
    @GetMapping
    public Result<List<User>> getAll() {
        return Result.success(userRepository.findAll());
    }

    // 增（手动添加用户）
    @PostMapping
    public Result<User> addUser(@RequestBody User user) {
        userRepository.save(user);
        return Result.success(user);
    }

    // 删
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return Result.success();
    }

    // 改
    @PutMapping
    public Result<Void> updateUser(@RequestBody User user) {
        userRepository.save(user);
        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser == null) {
            return Result.error(404, "用户不存在");
        }

        if (dbUser.getPassword().equals(user.getPassword())) {
            return Result.success(dbUser);
        } else {
            return Result.error(401, "密码错误");
        }
    }

    // 注册
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {

        User exist = userRepository.findByUsername(user.getUsername());

        if (exist != null) {
            return Result.error(400, "用户已存在");
        }

        userRepository.save(user);
        return Result.success(user);
    }
}
