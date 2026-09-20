package vn.iotstar.productcatalog.service;
import org.springframework.web.multipart.MultipartFile;
public interface StorageService {
    void init();
    String store(MultipartFile file);
    void delete(String storedName);
}
