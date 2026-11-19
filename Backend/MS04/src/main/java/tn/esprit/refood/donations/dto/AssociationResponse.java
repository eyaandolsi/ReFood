package tn.esprit.refood.donations.dto;

import tn.esprit.refood.donations.entity.Association;

public record AssociationResponse(Long id, String nom, String adresse, String telephone, boolean verifiee) {
    public static AssociationResponse from(Association a) {
        return new AssociationResponse(a.getId(), a.getNom(), a.getAdresse(), a.getTelephone(), a.isVerifiee());
    }
}
