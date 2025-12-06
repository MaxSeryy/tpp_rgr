package ua.cn.stu.rgr.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.cn.stu.rgr.entity.Firm;
import ua.cn.stu.rgr.entity.Product;
import ua.cn.stu.rgr.entity.Supplier;
import ua.cn.stu.rgr.entity.User;
import ua.cn.stu.rgr.repository.FirmRepository;
import ua.cn.stu.rgr.repository.ProductRepository;
import ua.cn.stu.rgr.repository.SupplierRepository;
import ua.cn.stu.rgr.repository.UserRepository;

@Component
@Profile("!test") // not for test profile
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final FirmRepository firmRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        initUsers();
        initBusinessData();
    }

    private void initUsers() {
        if (userRepository.count() > 0) {
            log.info("Users already exist - skipping user initialization.");
            return;
        }

        log.info("Initializing users...");

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);
        log.info("Created admin user: admin");

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole(User.Role.USER);
        userRepository.save(user);
        log.info("Created regular user: user");

        log.info("Users initialization completed.");
    }

    private void initBusinessData() {
        if (firmRepository.count() > 0) {
            log.info("Business data already exists - skipping initialization.");
            return;
        }

        log.info("Loading business data...");

        Firm firm1 = new Firm();
        firm1.setName("Tech Solutions");
        firm1.setAddress("Київ вул Хрещатик 1");
        firmRepository.save(firm1);

        Firm firm2 = new Firm();
        firm2.setName("Global Trade");
        firm2.setAddress("Львів вул Городоцька 15");
        firmRepository.save(firm2);

        Firm firm3 = new Firm();
        firm3.setName("Eastern Electronics");
        firm3.setAddress("Одеса вул Дерибасівська 20");
        firmRepository.save(firm3);

        Supplier supplier1 = new Supplier();
        supplier1.setName("Іван Петренко");
        supplier1.setContactInfo("ivan@example.com +380501234567");
        supplier1.setFirm(firm1);
        supplierRepository.save(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setName("Марія Коваленко");
        supplier2.setContactInfo("maria@example.com +380672345678");
        supplier2.setFirm(firm1);
        supplierRepository.save(supplier2);

        Supplier supplier3 = new Supplier();
        supplier3.setName("Олександр Сидоренко");
        supplier3.setContactInfo("alex@example.com +380933456789");
        supplier3.setFirm(firm2);
        supplierRepository.save(supplier3);

        Supplier supplier4 = new Supplier();
        supplier4.setName("Олена Мельник");
        supplier4.setContactInfo("olena@example.com +380504567890");
        supplier4.setFirm(firm3);
        supplierRepository.save(supplier4);

        Product product1 = new Product();
        product1.setName("Ноутбук Dell");
        product1.setSku(10001);
        product1.setPrice(25000);
        product1.setSupplier(supplier1);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Монітор Samsung");
        product2.setSku(10002);
        product2.setPrice(8000);
        product2.setSupplier(supplier1);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Клавіатура Logitech");
        product3.setSku(10003);
        product3.setPrice(1500);
        product3.setSupplier(supplier2);
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setName("Миша Microsoft");
        product4.setSku(10004);
        product4.setPrice(800);
        product4.setSupplier(supplier2);
        productRepository.save(product4);

        Product product5 = new Product();
        product5.setName("Принтер HP");
        product5.setSku(10005);
        product5.setPrice(5500);
        product5.setSupplier(supplier3);
        productRepository.save(product5);

        Product product6 = new Product();
        product6.setName("Сканер Canon");
        product6.setSku(10006);
        product6.setPrice(3200);
        product6.setSupplier(supplier3);
        productRepository.save(product6);

        Product product7 = new Product();
        product7.setName("Веб камера Logitech");
        product7.setSku(10007);
        product7.setPrice(2100);
        product7.setSupplier(supplier4);
        productRepository.save(product7);

        Product product8 = new Product();
        product8.setName("Навушники Sony");
        product8.setSku(10008);
        product8.setPrice(3500);
        product8.setSupplier(supplier4);
        productRepository.save(product8);

        log.info("Business data loaded successfully!");
    }
}