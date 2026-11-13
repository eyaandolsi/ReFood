# ReFood – Frontend (React + Vite)

Maquette statique de l'application cliente (données fictives dans `src/data.js`).
Quatre profils : Client, Commerçant, Association, Livreur.

## Lancer

```bash
npm install
npm run dev      # http://localhost:5173
npm run build    # production dans dist/
```

## Structure

```
src/
├── main.jsx          # point d'entrée
├── App.jsx           # en-tête, choix du profil, état global
├── data.js           # données fictives (offres, missions, rôles)
├── styles.css        # thème clair/sombre
└── components/
    ├── ClientView.jsx
    ├── MerchantView.jsx
    ├── AssoView.jsx
    ├── DriverView.jsx
    └── Side.jsx      # EcoRewards + prévision (MS5, MS6)
```

À venir : appels à l'API Gateway (Spring Cloud Gateway) et authentification Keycloak.
