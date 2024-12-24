package kz.zhanayev.ecommerce.services;


import kz.zhanayev.ecommerce.dto.CardPaymentRequest;
import kz.zhanayev.ecommerce.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO processPayment(String paymentMethod, CardPaymentRequest cardPaymentRequest, Long orderId);
    PaymentDTO getPaymentByOrderId(Long orderId);
}
