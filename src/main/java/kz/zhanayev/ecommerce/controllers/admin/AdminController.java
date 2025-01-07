package kz.zhanayev.ecommerce.controllers.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.zhanayev.ecommerce.dto.*;
import kz.zhanayev.ecommerce.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "API для управления ресурсами администратора")
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

    private ResponseEntity<Map<String, Object>> standardResponse(String statusMessage, Object payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("status_message", statusMessage);
        response.put("payload", payload);
        return ResponseEntity.ok(response);
    }

    // методы для продуктов
    @PostMapping("/products")
    @Operation(
            summary = "Создать продукт",
            description = "Добавляет новый продукт в систему",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, Object>> createProduct(
            @Parameter(description = "Информация о продукте") @RequestPart("product") ProductDTO productDTO,
            @Parameter(description = "Файл изображения продукта") @RequestPart(value = "file", required = false) MultipartFile file) {
        ProductDTO createdProduct = productService.createProduct(productDTO, file);
        return standardResponse("Продукт успешно создан", createdProduct);
    }

    @PutMapping("/products/{id}")
    @Operation(
            summary = "Обновить продукт",
            description = "Обновляет данные продукта по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Продукт не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> updateProduct(
            @Parameter(description = "ID продукта", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные продукта") @RequestPart("product") ProductDTO productDTO,
            @Parameter(description = "Новое изображение продукта") @RequestPart(value = "file", required = false) MultipartFile file) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, file);
        return standardResponse("Продукт успешно обновлен", updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    @Operation(
            summary = "Удалить продукт",
            description = "Удаляет продукт из системы по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Продукт не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> deleteProduct(
            @Parameter(description = "ID продукта", example = "1") @PathVariable Long id) {
        productService.deleteProduct(id);
        return standardResponse("Продукт успешно удален", null);
    }

    @GetMapping("/products")
    @Operation(
            summary = "Получить список продуктов",
            description = "Возвращает список всех продуктов с пагинацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукты успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @Parameter(description = "Номер страницы", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Поле для сортировки", example = "name") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Направление сортировки", example = "asc") @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductDTO> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return standardResponse("Продукты получены успешно", products);
    }

    @GetMapping("/products/{id}")
    @Operation(
            summary = "Получить продукт по ID",
            description = "Возвращает данные продукта по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Продукт не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getProductById(
            @Parameter(description = "ID продукта", example = "1") @PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return standardResponse("Продукт успешно получен", product);
    }


    // методы для заказов
    @GetMapping("/orders")
    @Operation(
            summary = "Получить заказы по статусу",
            description = "Возвращает список заказов по указанному статусу",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказы успешно получены"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    public ResponseEntity<Map<String, Object>> getOrdersByStatus(
            @Parameter(description = "Статус заказа", example = "COMPLETED") @RequestParam(required = false) String status) {
        List<OrderDTO> orders = orderService.getAllOrders(status);
        return standardResponse("Заказы получены успешно", orders);
    }

    @GetMapping("/orders/{id}")
    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает информацию о заказе по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getOrderById(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return standardResponse("Заказ успешно получен", order);
    }

    @GetMapping("/orders/all")
    @Operation(
            summary = "Получить все заказы",
            description = "Возвращает список всех заказов в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Все заказы успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders(null);
        return standardResponse("Все заказы получены успешно", orders);
    }

    @PutMapping("/orders/{id}/status")
    @Operation(
            summary = "Обновить статус заказа",
            description = "Изменяет статус заказа по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус заказа успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id,
            @Parameter(description = "Новый статус заказа", example = "SHIPPED") @RequestParam String status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return standardResponse("Статус заказа успешно обновлен", updatedOrder);
    }



    // методы для категорий
    @PostMapping("/categories")
    @Operation(
            summary = "Создать категорию",
            description = "Добавляет новую категорию в систему",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, Object>> addCategory(
            @Parameter(description = "Данные категории") @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.saveCategory(categoryDTO);
        return standardResponse("Категория успешно создана", createdCategory);
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Получить все категории",
            description = "Возвращает список всех категорий в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Все категории успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return standardResponse("Все категории успешно получены", categories);
    }

    @GetMapping("/categories/{id}")
    @Operation(
            summary = "Получить категорию по ID",
            description = "Возвращает данные категории по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> getCategoryById(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return standardResponse("Категория успешно получена", category);
    }

    @PutMapping("/categories/{id}")
    @Operation(
            summary = "Обновить категорию",
            description = "Обновляет данные категории по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория успешно обновлена"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> updateCategory(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные категории") @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return standardResponse("Категория успешно обновлена", updatedCategory);
    }

    @DeleteMapping("/categories/{id}")
    @Operation(
            summary = "Удалить категорию",
            description = "Удаляет категорию по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Категория не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> deleteCategory(
            @Parameter(description = "ID категории", example = "1") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return standardResponse("Категория успешно удалена", null);
    }

    // методы для брендов
    @PostMapping("/brands")
    @Operation(
            summary = "Создать бренд",
            description = "Добавляет новый бренд в систему",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренд успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, Object>> createBrand(
            @Parameter(description = "Данные бренда") @RequestBody BrandDTO brandDTO) {
        BrandDTO createdBrand = brandService.createBrand(brandDTO);
        return standardResponse("Бренд успешно создан", createdBrand);
    }

    @PutMapping("/brands/{id}")
    @Operation(
            summary = "Обновить бренд",
            description = "Обновляет данные бренда по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренд успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Бренд не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> updateBrand(
            @Parameter(description = "ID бренда", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновленные данные бренда") @RequestBody BrandDTO brandDTO) {
        BrandDTO updatedBrand = brandService.updateBrand(id, brandDTO);
        return standardResponse("Бренд успешно обновлен", updatedBrand);
    }

    @DeleteMapping("/brands/{id}")
    @Operation(
            summary = "Удалить бренд",
            description = "Удаляет бренд по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренд успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Бренд не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> deleteBrand(
            @Parameter(description = "ID бренда", example = "1") @PathVariable Long id) {
        brandService.deleteBrand(id);
        return standardResponse("Бренд успешно удален", null);
    }

    @GetMapping("/brands")
    @Operation(
            summary = "Получить все бренды",
            description = "Возвращает список всех брендов в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренды успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllBrands() {
        List<BrandDTO> brands = brandService.getAllBrands();
        return standardResponse("Бренды успешно получены", brands);
    }

    @GetMapping("/brands/{id}")
    @Operation(
            summary = "Получить бренд по ID",
            description = "Возвращает данные бренда по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бренд успешно получен"),
                    @ApiResponse(responseCode = "404", description = "Бренд не найден")
            }
    )
    public ResponseEntity<Map<String, Object>> getBrandById(
            @Parameter(description = "ID бренда", example = "1") @PathVariable Long id) {
        BrandDTO brand = brandService.getBrandById(id);
        return standardResponse("Бренд успешно получен", brand);
    }

    // методы для характеристик
    @PostMapping("/features")
    @Operation(
            summary = "Создать характеристику",
            description = "Добавляет новую характеристику продукта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристика успешно создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, Object>> createFeature(
            @Parameter(description = "Данные характеристики") @RequestBody FeatureDTO featureDTO) {
        FeatureDTO createdFeature = featureService.createFeature(featureDTO);
        return standardResponse("Характеристика успешно создана", createdFeature);
    }

    @PutMapping("/features/{id}")
    @Operation(
            summary = "Обновить характеристику",
            description = "Обновляет данные характеристики продукта по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристика успешно обновлена"),
                    @ApiResponse(responseCode = "404", description = "Характеристика не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> updateFeature(
            @Parameter(description = "ID характеристики", example = "1") @PathVariable Long id,
            @Parameter(description = "Обновлённые данные характеристики") @RequestBody FeatureDTO featureDTO) {
        FeatureDTO updatedFeature = featureService.updateFeature(id, featureDTO);
        return standardResponse("Характеристика успешно обновлена", updatedFeature);
    }

    @DeleteMapping("/features/{id}")
    @Operation(
            summary = "Удалить характеристику",
            description = "Удаляет характеристику продукта по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристика успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Характеристика не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> deleteFeature(
            @Parameter(description = "ID характеристики", example = "1") @PathVariable Long id) {
        featureService.deleteFeature(id);
        return standardResponse("Характеристика успешно удалена", null);
    }

    @GetMapping("/features")
    @Operation(
            summary = "Получить все характеристики",
            description = "Возвращает список всех характеристик продуктов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристики успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getAllFeatures() {
        List<FeatureDTO> features = featureService.getAllFeatures();
        return standardResponse("Характеристики успешно получены", features);
    }

    @GetMapping("/features/{id}")
    @Operation(
            summary = "Получить характеристику по ID",
            description = "Возвращает данные характеристики продукта по её ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристика успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Характеристика не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> getFeatureById(
            @Parameter(description = "ID характеристики", example = "1") @PathVariable Long id) {
        FeatureDTO feature = featureService.getFeatureById(id);
        return standardResponse("Характеристика успешно получена", feature);
    }

    @GetMapping("/features/product/{productId}")
    @Operation(
            summary = "Получить характеристики по продукту",
            description = "Возвращает характеристики продукта по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Характеристики успешно получены")
            }
    )
    public ResponseEntity<Map<String, Object>> getFeaturesByProductId(
            @Parameter(description = "ID продукта", example = "1") @PathVariable Long productId) {
        List<FeatureDTO> features = featureService.getFeaturesByProductId(productId);
        return standardResponse("Характеристики успешно получены", features);
    }

    @GetMapping("/payments/{orderId}")
    @Operation(
            summary = "Получить оплату по заказу",
            description = "Возвращает данные оплаты по ID заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Оплата успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Оплата не найдена")
            }
    )
    public ResponseEntity<Map<String, Object>> getPaymentByOrderId(
            @Parameter(description = "ID заказа", example = "1") @PathVariable Long orderId) {
        PaymentDTO paymentDTO = paymentService.getPaymentByOrderId(orderId);
        return standardResponse("Оплата успешно получена", paymentDTO);
    }
}
