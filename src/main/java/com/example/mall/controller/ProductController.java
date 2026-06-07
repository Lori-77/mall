package com.example.mall.controller;

import com.example.mall.common.Result;
import com.example.mall.entity.Product;
import com.example.mall.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 查全部上架商品（普通用户看到的，status为null也当上架）
    @GetMapping
    public Result<List<Product>> getAll() {
        List<Product> all = productRepository.findAll();
        return Result.success(all.stream()
            .filter(p -> p.getStatus() == null || p.getStatus() == 1)
            .toList());
    }

    // 查全部商品（管理员用，含下架）
    @GetMapping("/all")
    public Result<List<Product>> getAllAdmin() {
        return Result.success(productRepository.findAll());
    }

    // 查单个
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Integer id) {
        return productRepository.findById(id)
                .map(Result::success)
                .orElse(Result.error(404, "商品不存在"));
    }

    // 搜索（只搜上架商品）
    @GetMapping("/search")
    public Result<List<Product>> search(@RequestParam String keyword) {
        List<Product> all = productRepository.findByNameContaining(keyword);
        List<Product> result = all.stream().filter(p -> p.getStatus() == 1).toList();
        return Result.success(result);
    }

    // 增
    @PostMapping
    public Result<Product> addProduct(@RequestBody Product product) {
        if (product.getStatus() == null) product.setStatus(1);
        productRepository.save(product);
        return Result.success(product);
    }

    // 改
    @PutMapping
    public Result<Void> updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return Result.success();
    }

    // 删
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return Result.success();
    }

    // 上架/下架切换
    @PutMapping("/{id}/toggle")
    public Result<Product> toggleStatus(@PathVariable Integer id) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) return Result.error(404, "商品不存在");
        p.setStatus(p.getStatus() == null || p.getStatus() == 1 ? 0 : 1);
        productRepository.save(p);
        return Result.success(p);
    }
}
