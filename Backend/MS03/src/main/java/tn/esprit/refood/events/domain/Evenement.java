package tn.esprit.refood.events.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Enumerated(EnumType.STRING)
    private TypeEvenement type;

    private Long associationId;
    private String description;
    private String lieu;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @Min(1)
    private int capacite;

    @Enumerated(EnumType.STRING)
    private StatutEvenement statut = StatutEvenement.BROUILLON;

    private String organisateurId;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions = new ArrayList<>();

    public void publier() {
        if (statut != StatutEvenement.ANNULE) {
            statut = StatutEvenement.OUVERT;
        }
    }

    public void annuler() {
        statut = StatutEvenement.ANNULE;
        inscriptions.forEach(Inscription::annuler);
    }

    public void cloturer() {
        if (statut != StatutEvenement.ANNULE) {
            statut = StatutEvenement.TERMINE;
        }
    }

    public void actualiserStatutCapacite() {
        long placesOccupees = inscriptions.stream()
                .filter(i -> i.getStatut() == StatutInscription.INSCRIT)
                .count();
        if (statut == StatutEvenement.OUVERT && placesOccupees >= capacite) {
            statut = StatutEvenement.COMPLET;
        }
    }

    public void addInscription(Inscription inscription) {
        inscriptions.add(inscription);
        inscription.setEvenement(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public TypeEvenement getType() { return type; }
    public void setType(TypeEvenement type) { this.type = type; }
    public Long getAssociationId() { return associationId; }
    public void setAssociationId(Long associationId) { this.associationId = associationId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public StatutEvenement getStatut() { return statut; }
    public void setStatut(StatutEvenement statut) { this.statut = statut; }
    public String getOrganisateurId() { return organisateurId; }
    public void setOrganisateurId(String organisateurId) { this.organisateurId = organisateurId; }
    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }
}
