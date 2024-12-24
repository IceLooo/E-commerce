package kz.zhanayev.ecommerce.exceptions;

public class FeatureNotFoundException extends RuntimeException {

    public FeatureNotFoundException(String message) {
        super(message);
    }

    public FeatureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
