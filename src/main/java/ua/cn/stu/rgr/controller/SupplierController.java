package ua.cn.stu.rgr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ua.cn.stu.rgr.entity.Supplier;
import ua.cn.stu.rgr.service.FirmService;
import ua.cn.stu.rgr.service.SupplierService;

@Controller
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;
    private final FirmService firmService;

    @GetMapping
    public String getSuppliers(Model model) {
        log.info("Fetching all suppliers");
        model.addAttribute("suppliers", supplierService.getAll());
        return "suppliers/suppliers";
    }

    @GetMapping("/{id}")
    public String getSupplier(@PathVariable Long id, Model model) {
        log.info("Fetching supplier with id: {}", id);
        model.addAttribute("supplier", supplierService.getById(id));
        return "suppliers/supplier-detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newSupplierForm(Model model) {
        log.info("Opening new supplier form");
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("firms", firmService.getAll());
        return "suppliers/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createSupplier(@Valid @ModelAttribute Supplier supplier, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors while creating supplier");
            model.addAttribute("firms", firmService.getAll());
            return "suppliers/form";
        }
        log.info("Creating new supplier: {}", supplier.getName());
        supplierService.create(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editSupplierForm(@PathVariable Long id, Model model) {
        log.info("Opening edit form for supplier id: {}", id);
        model.addAttribute("supplier", supplierService.getById(id));
        model.addAttribute("firms", firmService.getAll());
        return "suppliers/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateSupplier(@PathVariable Long id, @Valid @ModelAttribute Supplier supplier, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Validation errors while updating supplier id: {}", id);
            model.addAttribute("firms", firmService.getAll());
            return "suppliers/form";
        }
        log.info("Updating supplier id: {}", id);
        supplierService.update(id, supplier);
        return "redirect:/suppliers/" + id;
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteSupplier(@PathVariable Long id) {
        log.info("Deleting supplier id: {}", id);
        supplierService.delete(id);
        return "redirect:/suppliers";
    }
}