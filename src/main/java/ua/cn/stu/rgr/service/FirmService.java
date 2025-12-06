package ua.cn.stu.rgr.service;

import java.util.List;

import ua.cn.stu.rgr.entity.Firm;

public interface FirmService {
    Firm create(Firm firm);
    Firm update(Long id, Firm firm);
    void delete(Long id);
    Firm getById(Long id);
    List<Firm> getAll();
}
