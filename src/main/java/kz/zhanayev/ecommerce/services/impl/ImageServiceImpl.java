package kz.zhanayev.ecommerce.services.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import kz.zhanayev.ecommerce.exceptions.FileUploadException;
import kz.zhanayev.ecommerce.services.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final String bucketName;
    private final String minioUrl;

    public ImageServiceImpl(MinioClient minioClient,
                            @Value("${minio.bucket-name}") String bucketName,
                            @Value("${minio.url}") String minioUrl) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.minioUrl = minioUrl;
    }


    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Создаем URL для доступа к объекту вручную
            return minioUrl + "/" + bucketName + "/" + fileName;

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new FileUploadException("Не удалось загрузить файл в MinIO : " + e.getMessage());
        }
    }
}
