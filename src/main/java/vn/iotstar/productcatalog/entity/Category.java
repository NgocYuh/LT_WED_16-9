package vn.iotstar.productcatalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "category_name"))
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "category_name", nullable = false, length = 150)
    private String categoryName;
    private String icon;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Product> products = new LinkedHashSet<>();
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
