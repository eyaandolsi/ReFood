package tn.esprit.refood.donations.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ressource, Long id) {
        super(ressource + " introuvable avec l'id " + id);
    }
}
