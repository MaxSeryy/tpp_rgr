package ua.cn.stu.rgr.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.cn.stu.rgr.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
    
    Optional<User> findByUsername(String username);
    
    User save(User user);
    
    void deleteById(Long id);
    
    boolean existsByUsername(String username);
}
