package tn.esprit.refood.donations.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "besoin")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Besoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "association_id")
    private Association association;

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false)
    private int quantite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgence urgence;

    private LocalDate dateLimite;
}
