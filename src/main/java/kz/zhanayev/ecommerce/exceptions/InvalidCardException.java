package kz.zhanayev.ecommerce.exceptions;

public class InvalidCardException extends RuntimeException {
  public InvalidCardException(String message) {
    super(message);
  }

  public InvalidCardException(String message, Throwable cause) {
    super(message, cause);
  }
}
