package vn.iotstar.productcatalog.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "product_name"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "product_name", nullable = false, length = 500)
    private String productName;
    @Column(nullable = false) private Integer quantity;
    @Column(nullable = false, precision = 15, scale = 2) private BigDecimal unitPrice;
    private String image;
    @Column(nullable = false, length = 500) private String description;
    @Column(nullable = false, precision = 5, scale = 2) private BigDecimal discount = BigDecimal.ZERO;
    @Column(nullable = false) private LocalDateTime createDate;
    @Column(nullable = false) private Short status = 1;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
