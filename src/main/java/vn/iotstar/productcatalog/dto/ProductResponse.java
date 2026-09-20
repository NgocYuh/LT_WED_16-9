package vn.iotstar.productcatalog.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import vn.iotstar.productcatalog.entity.Product;

public record ProductResponse(Long productId, String productName, Integer quantity, BigDecimal unitPrice,
                              String image, String description, BigDecimal discount, LocalDateTime createDate,
                              Short status, Long categoryId, String categoryName) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getProductId(), product.getProductName(), product.getQuantity(),
                product.getUnitPrice(), product.getImage(), product.getDescription(), product.getDiscount(),
                product.getCreateDate(), product.getStatus(), product.getCategory().getCategoryId(),
                product.getCategory().getCategoryName());
    }
}
