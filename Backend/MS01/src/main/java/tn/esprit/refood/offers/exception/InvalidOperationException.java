package tn.esprit.refood.offers.exception;

/**
 * Exception levée lorsqu'une opération métier n'est pas valide.
 */
public class InvalidOperationException extends RuntimeException {

    public InvalidOperationException(String message) {
        super(message);
    }
}
