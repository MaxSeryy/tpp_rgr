package ua.cn.stu.rgr.service;

import java.util.List;

import ua.cn.stu.rgr.entity.Supplier;

public interface SupplierService {
    Supplier create(Supplier supplier);
    Supplier update(Long id, Supplier supplier);
    void delete(Long id);
    Supplier getById(Long id);
    List<Supplier> getAll();
}
