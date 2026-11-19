package tn.esprit.refood.articles.config;

import tn.esprit.refood.articles.entity.Categorie;
import tn.esprit.refood.articles.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** H2 en mémoire est vide à chaque démarrage : on recrée les catégories de base. */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategorieRepository categorieRepository;

    @Override
    public void run(String... args) {
        if (categorieRepository.count() > 0) {
            return;
        }
        categorieRepository.saveAll(List.of(
                categorie("Anti-gaspillage", "Astuces pour réduire le gaspillage alimentaire"),
                categorie("Nutrition", "Équilibre alimentaire et bonnes pratiques"),
                categorie("Solidarité", "Actions et initiatives solidaires"),
                categorie("Écologie", "Alimentation durable et environnement")));
    }

    private Categorie categorie(String nom, String description) {
        Categorie c = new Categorie();
        c.setNom(nom);
        c.setDescription(description);
        return c;
    }
}
