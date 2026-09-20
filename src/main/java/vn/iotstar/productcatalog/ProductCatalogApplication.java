package vn.iotstar.productcatalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import vn.iotstar.productcatalog.config.StorageProperties;
import vn.iotstar.productcatalog.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ProductCatalogApplication {
    public static void main(String[] args) { SpringApplication.run(ProductCatalogApplication.class, args); }

    @Bean
    CommandLineRunner initialiseStorage(StorageService storageService) {
        return args -> storageService.init();
    }
}
