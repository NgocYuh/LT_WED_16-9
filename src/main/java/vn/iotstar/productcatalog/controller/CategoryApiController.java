package vn.iotstar.productcatalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.productcatalog.dto.ApiResponse;
import vn.iotstar.productcatalog.entity.Category;
import vn.iotstar.productcatalog.service.CatalogService;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "CRUD danh mục")
public class CategoryApiController {
    private final CatalogService catalogService;
    public CategoryApiController(CatalogService catalogService) { this.catalogService = catalogService; }
    @GetMapping @Operation(summary = "Lấy tất cả category") public ApiResponse<List<Category>> all() { return ApiResponse.ok("Thành công", catalogService.categories()); }
    @GetMapping("/{id}") @Operation(summary = "Lấy category theo id") public ApiResponse<Category> one(@PathVariable Long id) { return ApiResponse.ok("Thành công", catalogService.category(id)); }
    @PostMapping(consumes = "multipart/form-data") @Operation(summary = "Tạo category, icon là tùy chọn")
    public ResponseEntity<ApiResponse<Category>> create(@RequestParam String categoryName, @RequestParam(required = false) MultipartFile icon) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Thêm thành công", catalogService.createCategory(categoryName, icon))); }
    @PutMapping(value = "/{id}", consumes = "multipart/form-data") @Operation(summary = "Cập nhật category")
    public ApiResponse<Category> update(@PathVariable Long id, @RequestParam String categoryName, @RequestParam(required = false) MultipartFile icon) { return ApiResponse.ok("Cập nhật thành công", catalogService.updateCategory(id, categoryName, icon)); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Xóa category rỗng") public void delete(@PathVariable Long id) { catalogService.deleteCategory(id); }
}
