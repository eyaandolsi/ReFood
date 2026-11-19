package tn.esprit.refood.donations.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "association")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    private String telephone;

    @Column(nullable = false)
    @Builder.Default
    private boolean verifiee = false;

    public void verifier() {
        this.verifiee = true;
    }
}
