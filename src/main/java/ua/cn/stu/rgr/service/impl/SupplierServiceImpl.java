package ua.cn.stu.rgr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.rgr.entity.Supplier;
import ua.cn.stu.rgr.repository.SupplierRepository;
import ua.cn.stu.rgr.service.SupplierService;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier create(Supplier supplier) {
        syncProducts(supplier);
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Long id, Supplier supplier) {
        Supplier existing = getById(id);
        // Оновлюємо тільки поля, зберігаючи зв'язки
        existing.setName(supplier.getName());
        existing.setContactInfo(supplier.getContactInfo());
        existing.setFirm(supplier.getFirm());
        // Не чіпаємо products - вони залишаються як є
        return supplierRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier getById(Long id) {
        return supplierRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    private void syncProducts(Supplier supplier) {
        supplier.getProducts().forEach(p -> p.setSupplier(supplier));
    }
}