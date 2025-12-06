package ua.cn.stu.rgr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.rgr.entity.Product;
import ua.cn.stu.rgr.repository.ProductRepository;
import ua.cn.stu.rgr.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        ensureSupplier(product);
        checkSkuUnique(product.getSku(), null);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = getById(id);
        // Перевіряємо унікальність SKU (виключаючи поточний продукт)
        checkSkuUnique(product.getSku(), id);
        // Оновлюємо тільки поля
        existing.setName(product.getName());
        existing.setSku(product.getSku());
        existing.setPrice(product.getPrice());
        existing.setSupplier(product.getSupplier());
        return productRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    private void ensureSupplier(Product product) {
        if (product.getSupplier() == null) {
            throw new IllegalArgumentException("Product must reference a supplier");
        }
    }

    private void checkSkuUnique(Integer sku, Long excludeId) {
        productRepository.findBySku(sku).ifPresent(existing -> {
            if (excludeId == null || !existing.getId().equals(excludeId)) {
                throw new IllegalArgumentException("Товар з таким SKU вже існує: " + sku);
            }
        });
    }
}