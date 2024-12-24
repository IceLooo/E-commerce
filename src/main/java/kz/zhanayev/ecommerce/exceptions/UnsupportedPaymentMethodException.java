package kz.zhanayev.ecommerce.exceptions;

public class UnsupportedPaymentMethodException extends RuntimeException {
    public UnsupportedPaymentMethodException(String message) {
        super(message);
    }

    public UnsupportedPaymentMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
