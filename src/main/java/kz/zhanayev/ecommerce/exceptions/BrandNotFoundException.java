package kz.zhanayev.ecommerce.exceptions;

public class BrandNotFoundException extends RuntimeException {

    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
