package vn.iotstar.productcatalog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.productcatalog.config.StorageProperties;

@Service
public class FileStorageService implements StorageService {
    private final Path root;
    public FileStorageService(StorageProperties properties) { this.root = Path.of(properties.getLocation()).toAbsolutePath().normalize(); }
    @Override public void init() { try { Files.createDirectories(root); } catch (IOException ex) { throw new IllegalStateException("Không thể tạo thư mục uploads", ex); } }
    @Override public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        String sourceName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String extension = sourceName.lastIndexOf('.') >= 0 ? sourceName.substring(sourceName.lastIndexOf('.')) : "";
        String storedName = UUID.randomUUID() + extension.toLowerCase();
        Path destination = root.resolve(storedName).normalize();
        if (!destination.getParent().equals(root)) throw new IllegalArgumentException("Tên tệp không hợp lệ");
        try { Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING); return storedName; }
        catch (IOException ex) { throw new IllegalArgumentException("Không thể lưu tệp tải lên", ex); }
    }
    @Override public void delete(String storedName) {
        if (storedName == null || storedName.isBlank()) return;
        try { Files.deleteIfExists(root.resolve(storedName).normalize()); }
        catch (IOException ignored) { }
    }
}
