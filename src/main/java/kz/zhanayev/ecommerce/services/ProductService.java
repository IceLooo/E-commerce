package kz.zhanayev.ecommerce.services;


import kz.zhanayev.ecommerce.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ProductService {

     ProductDTO createProduct(ProductDTO productDTO, MultipartFile file);
     ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile file);
     Page<ProductDTO> getAllProducts(int page, int size, String sortBy, String sortDir);
     ProductDTO getProductById(Long id);
     void deleteProduct(Long id);
     Page<ProductDTO> getFilteredProducts(String name, String category, BigDecimal minPrice, BigDecimal maxPrice,
                                          Boolean inStock, int page, int size, String sortBy, String sortDir);
}
