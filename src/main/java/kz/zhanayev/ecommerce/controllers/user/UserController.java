package kz.zhanayev.ecommerce.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.zhanayev.ecommerce.dto.*;
import kz.zhanayev.ecommerce.models.*;
import kz.zhanayev.ecommerce.services.*;
import kz.zhanayev.ecommerce.util.mappers.AddressMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "API для работы с действиями пользователей")
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

    public UserController(ProductService productService, CartService cartService, PaymentService paymentService,
                          OrderService orderService, ReviewService reviewService, CategoryService categoryService,
                          BrandService brandService, FeatureService featureService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.featureService = featureService;
        this.userService = userService;
    }

    private ResponseEntity<Map<String, Object>> standardResponse(String statusMessage, Object payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("status_message", statusMessage);
        response.put("payload", payload);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    @Operation(
            summary = "Получить список продуктов",
            description = "Возвращает список всех продуктов с возможностью фильтрации и пагинации",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка продуктов"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    public ResponseEntity<Map<String, Object>> getProducts(
            @Parameter(description = "Номер страницы", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Поле для сортировки", example = "name") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Направление сортировки", example = "asc") @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Название продукта для поиска", example = "Laptop") @RequestParam(required = false) String name,
            @Parameter(description = "Категория продукта", example = "Electronics") @RequestParam(required = false) String category,
            @Parameter(description = "Минимальная цена", example = "1000") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Максимальная цена", example = "5000") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Наличие на складе", example = "true") @RequestParam(required = false) Boolean inStock) {
        Page<ProductDTO> products = productService.getFilteredProducts(name, category, minPrice, maxPrice, inStock, page, size, sortBy, sortDir);
        return standardResponse("Продукты получены успешно", products);
    }

    @GetMapping("/products/{id}")
    @Operation(
            summary = "Получить продукт по ID",
            description = "Возвращает информацию о продукте по его уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение данных продукта"),
                    @ApiResponse(responseCode = "404", description = "Продукт не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return standardResponse("Продукт успешно получен", product);
    }

    @PostMapping("/cart")
    @Operation(
            summary = "Добавить элемент в корзину",
            description = "Добавляет элемент в корзину текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Элемент успешно добавлен в корзину"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, Object>> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.addItemToCart(userId, cartItemDTO);
        return standardResponse("Товар успешно добавлен в корзину", updatedCart);
    }

    @DeleteMapping("/cart/{itemId}")
    @Operation(
            summary = "Удалить элемент из корзины",
            description = "Удаляет элемент из корзины текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Элемент успешно удалён из корзины"),
                    @ApiResponse(responseCode = "404", description = "Элемент не найден в корзине")
            }
    )
    public ResponseEntity<Map<String, Object>> removeItemFromCart(@PathVariable Long itemId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.removeItemFromCart(userId, itemId);
        return standardResponse("Товар успешно удален из корзины", updatedCart);
    }

    @PostMapping("/cart/clear")
    @Operation(
            summary = "Очистить корзину",
            description = "Очищает корзину текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Корзина успешно очищена")
            }
    )
    public ResponseEntity<Map<String, Object>> clearCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.getUserByUsername(username).getId();
        CartDTO updatedCart = cartService.clearCart(userId);
        return standardResponse("Корзина успешно очищена", updatedCart);
    }

    @PostMapping("/payments")
    @Operation(
            summary = "Обработать платёж",
            description = "Производит обработку платежа для указанного заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Платёж успешно обработан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных платежа")
            }
    )
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody CardPaymentRequest paymentRequest) {
        PaymentDTO payment = paymentService.processPayment("credit_card", paymentRequest, paymentRequest.getOrderId());
        return standardResponse("Платеж успешно обработан", payment);
    }

    @GetMapping("/payments/order/{orderId}")
    @Operation(
            summary = "Получить платёж по ID заказа",
            description = "Возвращает информацию о платеже по указанному ID заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные о платеже успешно получены"),
                    @ApiResponse(responseCode = "404", description = "Платёж не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentDTO payment = paymentService.getPaymentByOrderId(orderId);
        return standardResponse("Платеж получен успешно", payment);
    }

    @PostMapping("/orders")
    @Operation(
            summary = "Создать заказ",
            description = "Создаёт заказ на основе текущей корзины пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных заказа")
            }
    )
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody AddressDTO addressDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        Address address = AddressMapper.toEntity(addressDTO, user);
        OrderDTO createdOrder = orderService.createOrderFromCart(user.getId(), address);
        return standardResponse("Заказ успешно создан", createdOrder);
    }

    @GetMapping("/orders")
    @Operation(
            summary = "Получить заказы пользователя",
            description = "Возвращает список заказов текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказы успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getUserOrders(@RequestParam Long userId) {
        List<OrderDTO> orders = orderService.getAllOrdersByUserId(userId);
        return standardResponse("Заказы пользователей успешно получены", orders);
    }

    @GetMapping("/orders/{id}")
    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает информацию о заказе по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация о заказе успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id, @RequestParam Long userId) {
        OrderDTO order = orderService.getOrderById(id);
        return standardResponse("Заказ получен успешно", order);
    }

    @DeleteMapping("/orders/{id}/delete")
    @Operation(
            summary = "Удалить заказ",
            description = "Удаляет заказ по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно удалён"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long id, @RequestParam Long userId) {
        orderService.deleteOrder(id, userId);
        return standardResponse("Заказ успешно удален", null);
    }

    @PostMapping("/reviews")
    @Operation(
            summary = "Добавить отзыв",
            description = "Добавляет отзыв к указанному продукту",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отзыв успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных отзыва")
            }
    )
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.addReview(reviewDTO.getProductId(), reviewDTO.getUserId(), reviewDTO);
        return standardResponse("Отзыв успешно добавлен", createdReview);
    }

    @GetMapping("/reviews/product/{productId}")
    @Operation(
            summary = "Получить отзывы о продукте",
            description = "Возвращает список отзывов для указанного продукта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отзывы успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getProductReviews(@PathVariable Long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return standardResponse("Успешно получены отзывы о продукте", reviews);
    }

    @GetMapping("/reviews/user/{userId}")
    @Operation(
            summary = "Получить отзывы пользователя",
            description = "Возвращает список отзывов, оставленных пользователем",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отзывы успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return standardResponse("Отзывы пользователей получены успешно", reviews);
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Получить категории",
            description = "Возвращает список всех категорий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категории успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        return standardResponse("Категории выбраны успешно", categoryService.getAllCategories());
    }

    @GetMapping("/categories/{id}")
    @Operation(
            summary = "Получить категорию по ID",
            description = "Возвращает данные категории по её уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
        return standardResponse("Категория выбрана успешно", categoryService.getCategoryById(id));
    }

    @GetMapping("/brands")
    @Operation(
            summary = "Получить бренды",
            description = "Возвращает список всех брендов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренды успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllBrands() {
        return standardResponse("Бренды успешно получены", brandService.getAllBrands());
    }

    @GetMapping("/brands/{id}")
    @Operation(
            summary = "Получить бренд по ID",
            description = "Возвращает данные бренда по его уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренд успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Бренд не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getBrandById(@PathVariable Long id) {
        return standardResponse("Бренд успешно получен", brandService.getBrandById(id));
    }

    @GetMapping("/features")
    @Operation(
            summary = "Получить характеристики",
            description = "Возвращает список всех характеристик",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристики успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllFeatures() {
        List<FeatureDTO> features = featureService.getAllFeatures();
        return standardResponse("Характеристики получен успешно", features);
    }

    @GetMapping("/features/{id}")
    @Operation(
            summary = "Получить характеристику по ID",
            description = "Возвращает данные характеристики по её уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристика успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Характеристика не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> getFeatureById(@PathVariable Long id) {
        FeatureDTO featureDTO = featureService.getFeatureById(id);
        if (featureDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return standardResponse("Характеристика успешно получен", featureDTO);
    }
}
