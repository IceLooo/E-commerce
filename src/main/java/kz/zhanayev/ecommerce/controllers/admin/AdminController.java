package kz.zhanayev.ecommerce.controllers.admin;

import kz.zhanayev.ecommerce.dto.*;
import kz.zhanayev.ecommerce.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final PaymentService paymentService;
    private final BrandService brandService;
    private final FeatureService featureService;

    public AdminController(ProductService productService, OrderService orderService, CategoryService categoryService, PaymentService paymentService, BrandService brandService, FeatureService featureService) {
        this.productService = productService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.paymentService = paymentService;
        this.brandService = brandService;
        this.featureService = featureService;
    }

//    методы для продуктов
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") ProductDTO productDTO,
                                                    @RequestPart(value = "file", required = false) MultipartFile file) {
        ProductDTO createdProduct = productService.createProduct(productDTO, file);
        return ResponseEntity.ok(createdProduct);
    }

//    @PostMapping("/products")
//    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
//        ProductDTO createdProduct = productService.createProduct(productDTO, null);
//        return ResponseEntity.ok(createdProduct);
//    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @RequestPart("product") ProductDTO productDTO,
                                                    @RequestPart(value = "file", required = false) MultipartFile file) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, file);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductDTO> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


    //методы для заказов
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam(required = false) String status) {
        List<OrderDTO> orders = orderService.getAllOrders(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders(null);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }


    //методы для категории
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryDTO));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }


    // методы для брендов
    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.createBrand(brandDTO));
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }



    //методы для харектерестик
    @PostMapping("/features")
    public ResponseEntity<FeatureDTO> createFeature(@RequestBody FeatureDTO featureDTO) {
        return ResponseEntity.ok(featureService.createFeature(featureDTO));
    }

    @PutMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> updateFeature(@PathVariable Long id, @RequestBody FeatureDTO featureDTO) {
        return ResponseEntity.ok(featureService.updateFeature(id, featureDTO));
    }

    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/features")
    public ResponseEntity<List<FeatureDTO>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @GetMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable Long id) {
        return ResponseEntity.ok(featureService.getFeatureById(id));
    }

    @GetMapping("/features/product/{productId}")
    public ResponseEntity<List<FeatureDTO>> getFeaturesByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(featureService.getFeaturesByProductId(productId));
    }


 //////////////
    @GetMapping("/payments/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentDTO paymentDTO = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentDTO);
    }
}