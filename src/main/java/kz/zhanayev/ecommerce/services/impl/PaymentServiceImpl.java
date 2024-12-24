package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CardPaymentRequest;
import kz.zhanayev.ecommerce.dto.PaymentDTO;
import kz.zhanayev.ecommerce.exceptions.InvalidCardException;
import kz.zhanayev.ecommerce.exceptions.OrderNotFoundException;
import kz.zhanayev.ecommerce.exceptions.UnsupportedPaymentMethodException;
import kz.zhanayev.ecommerce.facade.PaymentFacade;
import kz.zhanayev.ecommerce.models.Order;
import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import kz.zhanayev.ecommerce.models.Payment;
import kz.zhanayev.ecommerce.repositories.OrderRepository;
import kz.zhanayev.ecommerce.repositories.PaymentRepository;
import kz.zhanayev.ecommerce.services.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentFacade paymentFacade;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, PaymentFacade paymentFacade) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentFacade = paymentFacade;
    }

    private static final Set<String> SUPPORTED_PAYMENT_METHODS = Set.of("credit_card", "paypal");

    @Override
    public PaymentDTO processPayment(String paymentMethod, CardPaymentRequest cardPaymentRequest, Long orderId) {
        if (!SUPPORTED_PAYMENT_METHODS.contains(paymentMethod)) {
            throw new UnsupportedPaymentMethodException("Unsupported payment method: " + paymentMethod);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

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

        return paymentFacade.toDTO(savedPayment);
    }

    private void validateCard(String cardNumber) {
        if (!isValidCard(cardNumber)) {
            throw new InvalidCardException("Invalid card number");
        }
    }

    private boolean isValidCard(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    @Override
    public PaymentDTO getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found with Order ID: " + orderId));
        return paymentFacade.toDTO(payment);
    }
}
