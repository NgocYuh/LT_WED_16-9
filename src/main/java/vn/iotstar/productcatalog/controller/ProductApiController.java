package vn.iotstar.productcatalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.productcatalog.dto.ApiResponse;
import vn.iotstar.productcatalog.dto.ProductResponse;
import vn.iotstar.productcatalog.service.CatalogService;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "CRUD sản phẩm")
public class ProductApiController {
    private final CatalogService catalogService;
    public ProductApiController(CatalogService catalogService) { this.catalogService = catalogService; }
    @GetMapping @Operation(summary = "Lấy tất cả product") public ApiResponse<List<ProductResponse>> all() { return ApiResponse.ok("Thành công", catalogService.products()); }
    @GetMapping("/{id}") @Operation(summary = "Lấy product theo id") public ApiResponse<ProductResponse> one(@PathVariable Long id) { return ApiResponse.ok("Thành công", catalogService.product(id)); }
    @PostMapping(consumes = "multipart/form-data") @Operation(summary = "Tạo product, image là tùy chọn")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@RequestParam String productName, @RequestParam Integer quantity, @RequestParam BigDecimal unitPrice, @RequestParam String description, @RequestParam(defaultValue = "0") BigDecimal discount, @RequestParam(defaultValue = "1") Short status, @RequestParam Long categoryId, @RequestParam(required = false) MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Thêm thành công", catalogService.createProduct(productName, quantity, unitPrice, description, discount, status, categoryId, image)));
    }
    @PutMapping(value = "/{id}", consumes = "multipart/form-data") @Operation(summary = "Cập nhật product")
    public ApiResponse<ProductResponse> update(@PathVariable Long id, @RequestParam String productName, @RequestParam Integer quantity, @RequestParam BigDecimal unitPrice, @RequestParam String description, @RequestParam(defaultValue = "0") BigDecimal discount, @RequestParam(defaultValue = "1") Short status, @RequestParam Long categoryId, @RequestParam(required = false) MultipartFile image) {
        return ApiResponse.ok("Cập nhật thành công", catalogService.updateProduct(id, productName, quantity, unitPrice, description, discount, status, categoryId, image));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Xóa product") public void delete(@PathVariable Long id) { catalogService.deleteProduct(id); }
}
