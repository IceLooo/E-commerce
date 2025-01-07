package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.PaymentDTO;
import kz.zhanayev.ecommerce.models.Order;
import kz.zhanayev.ecommerce.models.Payment;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setTransactionId(payment.getTransactionId());
        paymentDTO.setOrderId(payment.getOrder().getId());
        return paymentDTO;
    }

    public static Payment toEntity(PaymentDTO paymentDTO, Order order) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setOrder(order);
        return payment;
    }
}
