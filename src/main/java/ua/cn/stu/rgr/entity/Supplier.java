package ua.cn.stu.rgr.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"firm", "products"})
@EqualsAndHashCode(exclude = {"firm", "products"})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁёЇїІіЄєҐґ'\\-\\s0-9]{2,50}$", message = "Name must be 2-50 characters long and contain only letters, spaces, apostrophes, and hyphens")
    private String name;

    @NotBlank(message="Contact Info is required")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁёЇїІіЄєҐґ'\\-\\s0-9@+.]{5,100}$", message = "Contact Info must be 5-100 characters long and contain only letters, numbers, spaces, apostrophes, and hyphens")
    private String contactInfo;

    @ManyToOne(optional=false)
    @JoinColumn(name="firm_id")
    private Firm firm;

    @OneToMany(mappedBy = "supplier", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Product> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
