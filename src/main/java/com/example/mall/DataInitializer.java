package com.example.mall;

import com.example.mall.entity.Product;
import com.example.mall.entity.User;
import com.example.mall.repository.ProductRepository;
import com.example.mall.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DataInitializer(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // 修复老数据：status为null的改成1（上架）
        productRepository.findAll().forEach(p -> {
            if (p.getStatus() == null) {
                p.setStatus(1);
                productRepository.save(p);
            }
        });

        // 初始化管理员账号
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            userRepository.save(admin);
        }

        // 初始化示例商品（仅在表为空时）
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("机械键盘");
            p1.setPrice(299.0);
            p1.setStock(50);
            p1.setImage("机械键盘.jpg");
            p1.setDescription("青轴机械键盘，RGB背光，104键全键无冲，适合游戏和办公使用。");

            Product p2 = new Product();
            p2.setName("游戏鼠标");
            p2.setPrice(159.0);
            p2.setStock(100);
            p2.setImage("游戏鼠标.jpg");
            p2.setDescription("电竞游戏鼠标，16000DPI，可编程按键，RGB灯效，人体工学设计。");

            Product p3 = new Product();
            p3.setName("游戏耳机");
            p3.setPrice(399.0);
            p3.setStock(30);
            p3.setImage("耳机.jpg");
            p3.setDescription("7.1虚拟环绕声游戏耳机，降噪麦克风，蛋白皮耳罩，长时间佩戴舒适。");

            Product p4 = new Product();
            p4.setName("高性能电脑");
            p4.setPrice(5999.0);
            p4.setStock(10);
            p4.setImage("电脑.jpg");
            p4.setDescription("i7处理器，RTX4060显卡，16G内存，512G固态，畅玩3A大作。");

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
            productRepository.save(p4);

            System.out.println(">>> 示例数据初始化完成");
        }
    }
}
