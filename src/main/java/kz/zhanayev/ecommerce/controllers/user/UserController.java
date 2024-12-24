package kz.zhanayev.ecommerce.controllers.user;

import kz.zhanayev.ecommerce.dto.*;
import kz.zhanayev.ecommerce.facade.AddressFacade;
import kz.zhanayev.ecommerce.models.*;
import kz.zhanayev.ecommerce.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final ProductService productService;
    private final CartService cartService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final FeatureService featureService;
    private final UserService userService;
    private final AddressFacade addressFacade;

    public UserController(ProductService productService, CartService cartService, PaymentService paymentService,
                          OrderService orderService, ReviewService reviewService, CategoryService categoryService,
                          BrandService brandService, FeatureService featureService, UserService userService,
                          AddressFacade addressFacade) {
        this.productService = productService;
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.featureService = featureService;
        this.userService = userService;
        this.addressFacade = addressFacade;
    }



    //методы для продуктов
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock) {
        Page<ProductDTO> products = productService.getFilteredProducts(name, category, minPrice, maxPrice, inStock, page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }



    //методы для корзины
    @PostMapping("/cart")
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.addItemToCart(userId, cartItemDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/cart/{itemId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long itemId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/cart/clear")
    public ResponseEntity<CartDTO> clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.clearCart(userId);
        return ResponseEntity.ok(updatedCart);
    }


    //метод для оплаты
    @PostMapping("/payments")
    public ResponseEntity<PaymentDTO> processPayment(@RequestBody CardPaymentRequest paymentRequest) {
        PaymentDTO payment = paymentService.processPayment("credit_card", paymentRequest, paymentRequest.getOrderId());
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/payments/order/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentDTO payment = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(payment);
    }


    //методы для заказа
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam Long userId, @RequestBody AddressDTO addressDTO) {
        Address address = addressFacade.dtoToEntity(addressDTO);
        OrderDTO createdOrder = orderService.createOrderFromCart(userId, address);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@RequestParam Long userId) {
        List<OrderDTO> orders = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id, @RequestParam Long userId) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orders/{id}/delete")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id, @RequestParam Long userId) {
        orderService.deleteOrder(id, userId);
        return ResponseEntity.ok("Order deleted successfully");
    }


    //методы для отзыва
    @PostMapping("/reviews")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.addReview(reviewDTO.getProductId(), reviewDTO.getUserId(), reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/reviews/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getProductReviews(@PathVariable Long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }


    // методы для категории
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }


    //методы для брендов
    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    //методы для характеристик
    @GetMapping("/features")
    public ResponseEntity<List<FeatureDTO>> getAllFeatures() {
        List<FeatureDTO> features = featureService.getAllFeatures();
        return ResponseEntity.ok(features);
    }

    @GetMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable Long id) {
        FeatureDTO featureDTO = featureService.getFeatureById(id);
        if (featureDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(featureDTO);
    }

}