package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.ProductDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.repositories.BrandRepository;
import kz.zhanayev.ecommerce.repositories.CategoryRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.ImageService;
import kz.zhanayev.ecommerce.services.ProductService;
import kz.zhanayev.ecommerce.specifications.ProductSpecifications;
import kz.zhanayev.ecommerce.util.mappers.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductSpecifications productSpecifications;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              BrandRepository brandRepository, ProductSpecifications productSpecifications,
                              ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productSpecifications = productSpecifications;
        this.imageService = imageService;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Категория с идентификатором не найдена: " + productDTO.getCategoryId()));

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException("Бренд не найден по идентификатору: " + productDTO.getBrandId()));

        Product product = ProductMapper.toEntity(productDTO, category, brand);

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + id));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Категория с идентификатором не найдена: " + productDTO.getCategoryId()));

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException("Бренд не найден по идентификатору: " + productDTO.getBrandId()));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setWeight(productDTO.getWeight());
        product.setCategory(category);
        product.setBrand(brand);

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            product.setImageUrl(imageUrl);
        }

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDTO(updatedProduct);
    }

    @Override
    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductMapper::toDTO);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + id));
        return ProductMapper.toDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + id));
        productRepository.delete(product);
    }

    @Override
    public Page<ProductDTO> getFilteredProducts(String name, String category, BigDecimal minPrice, BigDecimal maxPrice,
                                                Boolean inStock, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Specification<Product> spec = Specification.where(productSpecifications.hasName(name))
                .and(productSpecifications.hasCategory(category))
                .and(productSpecifications.hasPriceBetween(minPrice, maxPrice))
                .and(productSpecifications.isInStock(inStock));

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        return productPage.map(ProductMapper::toDTO);
    }
}
