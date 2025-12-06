package ua.cn.stu.rgr.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.cn.stu.rgr.entity.Firm;
import ua.cn.stu.rgr.entity.Product;
import ua.cn.stu.rgr.entity.Supplier;
import ua.cn.stu.rgr.repository.FirmRepository;
import ua.cn.stu.rgr.service.FirmService;

@Service
@Transactional
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;

    public FirmServiceImpl(FirmRepository firmRepository) {
        this.firmRepository = firmRepository;
    }

    @Override
    public Firm create(Firm firm) {
        syncHierarchy(firm);
        return firmRepository.save(firm);
    }

    @Override
    public Firm update(Long id, Firm firm) {
        Firm existing = getById(id);
        // Оновлюємо тільки поля, зберігаючи зв'язки
        existing.setName(firm.getName());
        existing.setAddress(firm.getAddress());
        // Не чіпаємо suppliers - вони залишаються як є
        return firmRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        firmRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Firm getById(Long id) {
        return firmRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Firm> getAll() {
        return firmRepository.findAll();
    }

    private void syncHierarchy(Firm firm) {
        if (firm.getSuppliers() == null) {
            return;
        }
        for (Supplier supplier : firm.getSuppliers()) {
            supplier.setFirm(firm);
            if (supplier.getProducts() == null) {
                continue;
            }
            for (Product product : supplier.getProducts()) {
                product.setSupplier(supplier);
            }
        }
    }
}