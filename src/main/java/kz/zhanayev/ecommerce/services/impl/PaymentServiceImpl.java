package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CardPaymentRequest;
import kz.zhanayev.ecommerce.dto.PaymentDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.exceptions.UnsupportedPaymentMethodException;
import kz.zhanayev.ecommerce.models.Order;
import kz.zhanayev.ecommerce.models.Payment;
import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import kz.zhanayev.ecommerce.repositories.OrderRepository;
import kz.zhanayev.ecommerce.repositories.PaymentRepository;
import kz.zhanayev.ecommerce.services.PaymentService;
import kz.zhanayev.ecommerce.util.mappers.PaymentMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private static final int CARD_DOUBLE_THRESHOLD = 9; // Максимум для удвоенного числа
    private static final int CARD_MODULO = 10; // Модуль проверки

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    private static final Set<String> SUPPORTED_PAYMENT_METHODS = Set.of("credit_card", "paypal");

    @Override
    public PaymentDTO processPayment(String paymentMethod, CardPaymentRequest cardPaymentRequest, Long orderId) {
        if (!SUPPORTED_PAYMENT_METHODS.contains(paymentMethod)) {
            throw new UnsupportedPaymentMethodException("Неподдерживаемый способ оплаты: " + paymentMethod);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказ с идентификатором не найден: " + orderId));

        if (paymentMethod.equals("credit_card")) {
            validateCard(cardPaymentRequest.getCardNumber());
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("SUCCESS");
        payment.setTransactionId(UUID.randomUUID().toString());

        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        return PaymentMapper.toDTO(savedPayment);
    }

    private void validateCard(String cardNumber) {
        if (!isValidCard(cardNumber)) {
            throw new NotFoundException("Неверный номер карты");
        }
    }

    private boolean isValidCard(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > CARD_DOUBLE_THRESHOLD) {
                    n -= CARD_DOUBLE_THRESHOLD;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % CARD_MODULO == 0);
    }

    @Override
    public PaymentDTO getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Платеж не найден по идентификатору заказа: " + orderId));
        return PaymentMapper.toDTO(payment);
    }
}
