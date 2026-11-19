package tn.esprit.refood.donations.entity;

import tn.esprit.refood.donations.exception.ConflictException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "don")
@Getter @Setter @NoArgsConstructor
public class Don {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence vers une offre gratuite du MS1 (pas de clé étrangère : base séparée). */
    @Column(nullable = false)
    private Long offreId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "association_id")
    private Association association;

    @Column(nullable = false)
    private int quantite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDon statut = StatutDon.DEMANDE;

    private LocalDateTime dateDemande;
    private LocalDateTime dateReception;
    private String motifRefus;

    @OneToOne(mappedBy = "don", cascade = CascadeType.ALL, orphanRemoval = true)
    private CreneauRetrait creneau;

    @OneToMany(mappedBy = "don", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateChangement ASC")
    private List<HistoriqueDon> historique = new ArrayList<>();

    // ----- règles métier (machine à états) -----
    public void accepter() {
        exiger("accepter", StatutDon.DEMANDE);
        statut = StatutDon.ACCEPTE;
    }

    public void refuser(String motif) {
        exiger("refuser", StatutDon.DEMANDE);
        statut = StatutDon.REFUSE;
        motifRefus = motif;
    }

    public void planifier() {
        exiger("planifier", StatutDon.ACCEPTE);
        statut = StatutDon.PLANIFIE;
    }

    public void marquerRecu() {
        exiger("marquer comme reçu", StatutDon.ACCEPTE, StatutDon.PLANIFIE);
        statut = StatutDon.RECU;
        dateReception = LocalDateTime.now();
    }

    public void annuler() {
        exiger("annuler", StatutDon.DEMANDE, StatutDon.ACCEPTE, StatutDon.PLANIFIE);
        statut = StatutDon.ANNULE;
    }

    private void exiger(String action, StatutDon... autorises) {
        if (!Arrays.asList(autorises).contains(statut)) {
            throw new ConflictException("Impossible de " + action + " un don au statut " + statut);
        }
    }
}
