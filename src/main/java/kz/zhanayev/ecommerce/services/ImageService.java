package kz.zhanayev.ecommerce.services;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
     String uploadImage(MultipartFile file);
}
