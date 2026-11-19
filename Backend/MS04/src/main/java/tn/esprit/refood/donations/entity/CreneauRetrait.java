package tn.esprit.refood.donations.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "creneau_retrait")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreneauRetrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "don_id", unique = true)
    private Don don;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Builder.Default
    private boolean confirme = false;

    public void confirmer() {
        this.confirme = true;
    }
}
