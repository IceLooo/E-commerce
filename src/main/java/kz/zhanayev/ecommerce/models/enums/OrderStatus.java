package kz.zhanayev.ecommerce.models.enums;

public enum OrderStatus {
    PENDING,       // Заказ оформлен, ожидает обработки
    IN_PROGRESS,
    PAID,
    SHIPPED,// Заказ в обработке
    COMPLETED,     // Заказ завершён
    CANCELED       // Заказ отменён
}
