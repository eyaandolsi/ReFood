export const OFFERS = [
  { id: 1, em: "🥖", name: "Panier boulangerie", shop: "Boulangerie El Medina", cat: "Boulangerie", price: 4, old: 12, qty: 5, time: "18h–19h" },
  { id: 2, em: "🥬", name: "Panier fruits et légumes", shop: "Primeur Ben Salah", cat: "Fruits et légumes", price: 5, old: 14, qty: 3, time: "17h30–19h" },
  { id: 3, em: "🍲", name: "Plats cuisinés", shop: "Traiteur Dar Zarrouk", cat: "Plats préparés", price: 6, old: 18, qty: 2, time: "20h–21h" },
  { id: 4, em: "🥛", name: "Produits laitiers", shop: "Supérette Carthage", cat: "Épicerie", price: 3, old: 9, qty: 8, time: "18h–20h" },
];
export const FREE = [
  { id: 11, em: "🥐", name: "Viennoiseries invendues", shop: "Café Le Lac", qty: 20 },
  { id: 12, em: "🍞", name: "Pain et pâtisseries", shop: "Pâtisserie Sidi Bou", qty: 12 },
  { id: 13, em: "🍎", name: "Fruits de saison", shop: "Primeur Ben Salah", qty: 15 },
];
export const MISSIONS = [
  { id: 21, from: "Boulangerie El Medina", to: "Client : Sana K.", kind: "Commande", km: 2.4 },
  { id: 22, from: "Café Le Lac", to: "Association Espoir", kind: "Don", km: 4.1 },
  { id: 23, from: "Traiteur Dar Zarrouk", to: "Client : Omar B.", kind: "Commande", km: 1.8 },
];
export const CATS = ["Toutes", "Boulangerie", "Fruits et légumes", "Plats préparés", "Épicerie"];
export const ROLES = [
  { key: "client", label: "Client", title: "Des paniers à prix réduit près de chez vous", sub: "Réservez les surplus du jour et évitez le gaspillage." },
  { key: "merchant", label: "Commerçant", title: "Valorisez vos surplus", sub: "Publiez une offre en quelques clics, vendue ou donnée." },
  { key: "asso", label: "Association", title: "Recevez des dons alimentaires", sub: "Déclarez vos besoins et demandez les surplus gratuits." },
  { key: "driver", label: "Livreur", title: "Missions de livraison disponibles", sub: "Acceptez une mission et confirmez la livraison." },
];
