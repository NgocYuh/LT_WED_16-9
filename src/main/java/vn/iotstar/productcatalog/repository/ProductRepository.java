package vn.iotstar.productcatalog.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.productcatalog.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductNameIgnoreCase(String productName);
    boolean existsByProductNameIgnoreCaseAndProductIdNot(String productName, Long productId);
    boolean existsByCategoryCategoryId(Long categoryId);
}
