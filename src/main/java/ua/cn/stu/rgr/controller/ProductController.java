package ua.cn.stu.rgr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ua.cn.stu.rgr.entity.Product;
import ua.cn.stu.rgr.service.ProductService;
import ua.cn.stu.rgr.service.SupplierService;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final SupplierService supplierService;

    @GetMapping
    public String getProducts(Model model) {
        log.info("Fetching all products");
        model.addAttribute("products", productService.getAll());
        return "products/products";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        log.info("Fetching product with id: {}", id);
        model.addAttribute("product", productService.getById(id));
        return "products/product-detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newProductForm(Model model) {
        log.info("Opening new product form");
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", supplierService.getAll());
        return "products/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(@Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors while creating product");
            model.addAttribute("suppliers", supplierService.getAll());
            return "products/form";
        }
        try {
            log.info("Creating new product: {}", product.getName());
            productService.create(product);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            log.warn("SKU validation error: {}", e.getMessage());
            model.addAttribute("skuError", e.getMessage());
            model.addAttribute("suppliers", supplierService.getAll());
            return "products/form";
        }
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProductForm(@PathVariable Long id, Model model) {
        log.info("Opening edit form for product id: {}", id);
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("suppliers", supplierService.getAll());
        return "products/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors while updating product id: {}", id);
            model.addAttribute("suppliers", supplierService.getAll());
            return "products/form";
        }
        try {
            log.info("Updating product id: {}", id);
            productService.update(id, product);
            return "redirect:/products/" + id;
        } catch (IllegalArgumentException e) {
            log.warn("SKU validation error: {}", e.getMessage());
            model.addAttribute("skuError", e.getMessage());
            model.addAttribute("suppliers", supplierService.getAll());
            return "products/form";
        }
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        log.info("Deleting product id: {}", id);
        productService.delete(id);
        return "redirect:/products";
    }
}