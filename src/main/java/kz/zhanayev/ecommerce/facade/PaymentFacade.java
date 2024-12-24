package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.PaymentDTO;
import kz.zhanayev.ecommerce.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFacade {

    public PaymentDTO toDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setOrderId(payment.getOrder().getId());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setTransactionId(payment.getTransactionId());
        return paymentDTO;
    }

    public Payment toEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setTransactionId(paymentDTO.getTransactionId());
        return payment;
    }
}
