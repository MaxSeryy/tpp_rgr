package ua.cn.stu.rgr.service;

import java.util.List;

import ua.cn.stu.rgr.entity.Product;

public interface ProductService {
    Product create(Product product);
    Product update(Long id, Product product);
    void delete(Long id);
    Product getById(Long id);
    List<Product> getAll();
}
