# ReFood : Moins de gaspillage, plus de solidarité

> Projet AWD : architecture microservices | Équipe **Five Core** | Classe : `4DASI9`

ReFood est une application web qui connecte les **commerçants** ayant des surplus invendus avec des **clients** (vente de paniers à prix réduit) et des **associations** (dons gratuits), et qui sensibilise au gaspillage alimentaire grâce à des **articles**, des **événements solidaires** et des **recettes anti-gaspillage**.

> ⚠️ **État du projet : validation intermédiaire (séance 4).** Le projet n'est pas finalisé. Ce dépôt présente la conception, l'organisation et les premières réalisations.

---

## 🎯 Problématique

Chaque jour, des produits encore consommables sont jetés par les commerces, alors que des consommateurs et des associations ont besoin de produits abordables ou de dons. Les citoyens manquent aussi d'informations et d'occasions pour agir contre le gaspillage.

**Notre solution :** une seule application pour **vendre** des paniers à prix réduit, **donner** les surplus aux associations, et **sensibiliser** (articles, événements solidaires, recettes), avec une couche d'intelligence pour anticiper les surplus.

## 🧑‍🤝‍🧑 Acteurs et fonctionnalités

| Acteur | Fonctionnalités principales |
|---|---|
| **Commerçant** | Publier et gérer ses offres et son stock, accepter des dons, consulter les prévisions de surplus |
| **Client** | Consulter les offres, lire et commenter des articles, s'inscrire à des événements, chercher des recettes |
| **Association** | Déclarer ses besoins, demander un don, confirmer la réception, organiser des événements |
| **Administrateur** | Gérer les catégories et les recettes, relancer l'analyse |
| **Tous les utilisateurs** | S'authentifier, rédiger des articles, gérer son garde-manger, sauvegarder des recettes en favori |
| **Système (IA)** | Prédire les surplus, recommander des associations, alimenter l'historique |

---

## 🏗️ Architecture



📄 Les diagrammes complets (architecture, classes, cas d'utilisation) se trouvent dans [`Documentation/Diagrams/`](Documentation/Diagrams/).

### Microservices

| # | Service | Responsabilité | Entités principales | Base | Responsable |
|---|---|---|---|---|---|
| MS1 | **Offres et stocks** | Publier et gérer les surplus (offres payantes ou gratuites) et leurs quantités | Offre, Stock, Catégorie | MySQL | Eya Andolsi |
| MS2 | **Articles et blog** | Espace collaboratif pour créer, publier et consulter des articles sur l'anti-gaspillage, la nutrition, la solidarité et l'écologie | Article, Commentaire, Like, Catégorie | H2 | Mayssa Ferjani |
| MS3 | **Événements solidaires** | Publier et gérer des événements (collectes, ateliers anti-gaspillage, distributions de repas) et leurs inscriptions | Événement, Inscription | H2 | Rayen Gmati |
| MS4 | **Dons et associations** | Gérer les besoins des associations et les demandes de dons | Association, Don, Besoin | MySQL | Abrar Abidi |
| MS5 | **Recettes anti-gaspillage** | Proposer des recettes pour cuisiner les restes et les produits proches de leur date limite, gérer le garde-manger | Recette, Ingrédient, IngrédientRecette, Favori, ProduitGardeManger | MySQL | Aya Chagra |
| MS6 | **Food Intelligence** *(commun)* | Analyser les historiques, prédire les surplus, recommander des associations | EnregistrementHistorique, PrévisionSurplus, RecommandationAssociation | MongoDB | Toute l'équipe |

### Communication

- **Synchrone** : REST avec OpenFeign, quand une réponse immédiate est nécessaire.
  - MS4 vérifie l'offre concernée par un don auprès de MS1 (`offreId`).
  - MS3 vérifie qu'une association est valide auprès de MS4 (`associationId`).
- **Asynchrone** : événements RabbitMQ, une fois l'action terminée.
  - MS1, MS3, MS4 et MS5 publient leurs événements (offre publiée, inscription confirmée, don accepté, recette sauvegardée).
  - MS6 les consomme pour alimenter son historique, puis produit des prévisions et des recommandations.

### Sécurité

- Authentification et autorisation via **Keycloak** (JWT).
- Le JWT est vérifié à l'**API Gateway**, qui route ensuite les requêtes vers les microservices.
- Rôles : `COMMERCANT`, `CLIENT`, `ASSOCIATION`, `ADMIN`.

---

## 🛠️ Technologies

| Couche | Technologies |
|---|---|
| Frontend | React |
| Backend | Java, Spring Boot, Maven |
| Infrastructure | Spring Cloud Gateway, Eureka, OpenFeign |
| Sécurité | Keycloak, JWT |
| Messagerie | RabbitMQ |
| Bases de données | MySQL, H2, MongoDB |
| Déploiement | Docker |
| Gestion de projet | *(à compléter : Trello / Jira / GitHub Projects)* |

---

## 🔌 Contrats d'API (aperçu)

> Les endpoints de MS2, MS3 et MS5 sont proposés et seront précisés lors du développement.

| Service | Endpoints principaux |
|---|---|
| MS1 | `POST /offers` · `GET /offers?type=&category=` · `PUT /offers/{id}` · `PATCH /offers/{id}/stock` |
| MS2 | `POST /articles` · `GET /articles?category=` · `POST /articles/{id}/comments` · `POST /articles/{id}/likes` · `GET /categories` |
| MS3 | `POST /events` · `GET /events?type=&date=` · `PATCH /events/{id}/cancel` · `POST /events/{id}/registrations` · `DELETE /events/{id}/registrations/me` |
| MS4 | `POST /needs` · `GET /donations/available` · `POST /donations` · `PATCH /donations/{id}/accept` |
| MS5 | `GET /recipes?ingredient=&difficulty=` · `POST /recipes` · `POST /recipes/{id}/favorite` · `GET /pantry` · `POST /pantry` |
| MS6 | `GET /predictions/surplus` · `GET /recommendations/associations` · `POST /analysis/refresh` |

Les codes de réponse et les événements détaillés sont dans le dossier technique.

---

## 🚀 Lancer le projet

> 📝 À compléter au fur et à mesure de l'avancement.

### Prérequis

- Java 17+
- Maven
- Node.js 18+
- Docker et Docker Compose

### Démarrage

```bash
# 1. Cloner le dépôt
git clone https://github.com/<organisation>/refood-4dasi9-gX.git
cd refood-4dasi9-gX

# 2. Lancer l'infrastructure (Keycloak, RabbitMQ, bases de données)
docker compose up -d

# 3. Lancer les services backend (Eureka, Gateway, puis les microservices)
cd Backend/<service> && mvn spring-boot:run

# 4. Lancer le frontend
cd Frontend && npm install && npm run dev
```

---

## 🌍 Objectifs de développement durable

ODD 1 (Pas de pauvreté) · ODD 2 (Faim zéro) · ODD 11 (Villes durables) · ODD 12 (Consommation responsable) · ODD 13 (Lutte climatique)

---

## 📚 Documentation

- Présentation : [`Documentation/Presentation/`](Documentation/Presentation/)
- Diagrammes : [`Documentation/Diagrams/`](Documentation/Diagrams/)
- Dossier technique : [`Documentation/`](Documentation/)

*ReFood : Moins de gaspillage, plus de solidarité* 🌱
