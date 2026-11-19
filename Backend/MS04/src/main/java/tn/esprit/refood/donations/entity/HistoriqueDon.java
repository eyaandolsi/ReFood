package tn.esprit.refood.donations.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_don")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriqueDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "don_id")
    private Don don;

    @Enumerated(EnumType.STRING)
    private StatutDon ancienStatut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDon nouveauStatut;

    @Column(nullable = false)
    private LocalDateTime dateChangement;
}
