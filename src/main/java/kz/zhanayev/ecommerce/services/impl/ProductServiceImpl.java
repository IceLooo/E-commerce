package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.ProductDTO;
import kz.zhanayev.ecommerce.exceptions.ResourceNotFoundException;
import kz.zhanayev.ecommerce.facade.ProductFacade;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.repositories.BrandRepository;
import kz.zhanayev.ecommerce.repositories.CategoryRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.ImageService;
import kz.zhanayev.ecommerce.services.ProductService;
import kz.zhanayev.ecommerce.specifications.ProductSpecifications;
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
    private final ProductFacade productFacade;
    private final ProductSpecifications productSpecifications;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              BrandRepository brandRepository, ProductFacade productFacade,
                              ProductSpecifications productSpecifications, ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productFacade = productFacade;
        this.productSpecifications = productSpecifications;
        this.imageService = imageService;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = productFacade.productDTOToProduct(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + productDTO.getBrandId()));
        product.setBrand(brand);

        // Handle image upload
        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return productFacade.productToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setWeight(productDTO.getWeight());

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));
        product.setCategory(category);

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + productDTO.getBrandId()));
        product.setBrand(brand);

        // Handle image upload
        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            product.setImageUrl(imageUrl);
        }

        Product updatedProduct = productRepository.save(product);
        return productFacade.productToProductDTO(updatedProduct);
    }

    @Override
    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productFacade::productToProductDTO);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return productFacade.productToProductDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
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
        return productPage.map(productFacade::productToProductDTO);
    }
}
