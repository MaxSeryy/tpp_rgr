package ua.cn.stu.rgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.cn.stu.rgr.entity.Firm;

@Repository
public interface FirmRepository extends JpaRepository<Firm, Long> {
}
