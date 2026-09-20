package vn.iotstar.productcatalog.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.productcatalog.dto.ProductResponse;
import vn.iotstar.productcatalog.entity.Category;
import vn.iotstar.productcatalog.entity.Product;
import vn.iotstar.productcatalog.exception.NotFoundException;
import vn.iotstar.productcatalog.repository.CategoryRepository;
import vn.iotstar.productcatalog.repository.ProductRepository;

@Service
@Transactional
public class CatalogService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StorageService storageService;
    public CatalogService(CategoryRepository categoryRepository, ProductRepository productRepository, StorageService storageService) {
        this.categoryRepository = categoryRepository; this.productRepository = productRepository; this.storageService = storageService;
    }
    @Transactional(readOnly = true) public List<Category> categories() { return categoryRepository.findAll(); }
    @Transactional(readOnly = true) public Category category(Long id) { return getCategory(id); }
    public Category createCategory(String name, MultipartFile icon) {
        validateName(name, "Tên danh mục");
        if (categoryRepository.findByCategoryNameIgnoreCase(name.trim()).isPresent()) throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        Category category = new Category(); category.setCategoryName(name.trim()); category.setIcon(storageService.store(icon));
        return categoryRepository.save(category);
    }
    public Category updateCategory(Long id, String name, MultipartFile icon) {
        validateName(name, "Tên danh mục");
        if (categoryRepository.existsByCategoryNameIgnoreCaseAndCategoryIdNot(name.trim(), id)) throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        Category category = getCategory(id); category.setCategoryName(name.trim());
        if (icon != null && !icon.isEmpty()) { String old = category.getIcon(); category.setIcon(storageService.store(icon)); storageService.delete(old); }
        return category;
    }
    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        if (productRepository.existsByCategoryCategoryId(id)) throw new IllegalArgumentException("Không thể xóa danh mục đang có sản phẩm");
        categoryRepository.delete(category); storageService.delete(category.getIcon());
    }
    @Transactional(readOnly = true) public List<ProductResponse> products() { return productRepository.findAll().stream().map(ProductResponse::from).toList(); }
    @Transactional(readOnly = true) public ProductResponse product(Long id) { return ProductResponse.from(getProduct(id)); }
    public ProductResponse createProduct(String name, Integer quantity, BigDecimal unitPrice, String description, BigDecimal discount, Short status, Long categoryId, MultipartFile image) {
        Product product = new Product(); applyProduct(product, name, quantity, unitPrice, description, discount, status, categoryId, image, false);
        product.setCreateDate(LocalDateTime.now()); return ProductResponse.from(productRepository.save(product));
    }
    public ProductResponse updateProduct(Long id, String name, Integer quantity, BigDecimal unitPrice, String description, BigDecimal discount, Short status, Long categoryId, MultipartFile image) {
        Product product = getProduct(id); applyProduct(product, name, quantity, unitPrice, description, discount, status, categoryId, image, true); return ProductResponse.from(product);
    }
    public void deleteProduct(Long id) { Product product = getProduct(id); productRepository.delete(product); storageService.delete(product.getImage()); }
    private void applyProduct(Product product, String name, Integer quantity, BigDecimal unitPrice, String description, BigDecimal discount, Short status, Long categoryId, MultipartFile image, boolean updating) {
        validateName(name, "Tên sản phẩm");
        if (quantity == null || quantity < 0) throw new IllegalArgumentException("Số lượng phải từ 0 trở lên");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("Đơn giá phải từ 0 trở lên");
        if (discount == null || discount.signum() < 0 || discount.compareTo(new BigDecimal("100")) > 0) throw new IllegalArgumentException("Giảm giá phải trong khoảng 0-100");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Mô tả không được để trống");
        if (status == null || (status != 0 && status != 1)) throw new IllegalArgumentException("Trạng thái chỉ nhận 0 hoặc 1");
        if (updating ? productRepository.existsByProductNameIgnoreCaseAndProductIdNot(name.trim(), product.getProductId()) : productRepository.findByProductNameIgnoreCase(name.trim()).isPresent()) throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        product.setProductName(name.trim()); product.setQuantity(quantity); product.setUnitPrice(unitPrice); product.setDescription(description.trim());
        product.setDiscount(discount); product.setStatus(status); product.setCategory(getCategory(categoryId));
        if (image != null && !image.isEmpty()) { String old = product.getImage(); product.setImage(storageService.store(image)); storageService.delete(old); }
    }
    private Category getCategory(Long id) { return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy category có id=" + id)); }
    private Product getProduct(Long id) { return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy product có id=" + id)); }
    private void validateName(String value, String label) { if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " không được để trống"); }
}
