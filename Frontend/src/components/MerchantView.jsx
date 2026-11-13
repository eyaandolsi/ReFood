import { useState } from "react";

export default function MerchantView({ offers, onPublish }) {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [qty, setQty] = useState("");
  const publish = () => {
    if (!name || !qty) return;
    onPublish({ id: Date.now(), em: "🛒", name, shop: "Ma boutique", cat: "Épicerie", price: Number(price) || 0, old: 0, qty: Number(qty), time: "18h–20h" });
    setName(""); setPrice(""); setQty("");
  };
  return (
    <>
      <div className="stats">
        <div className="stat"><b>{offers.length}</b>offres actives</div>
        <div className="stat"><b>42</b>paniers vendus</div>
        <div className="stat"><b>18</b>dons effectués</div>
        <div className="stat"><b>96 kg</b>de déchets évités</div>
      </div>
      <div className="sec">
        <h2>Mes offres</h2>
        <div className="scroll">
          <table>
            <thead><tr><th>Offre</th><th>Type</th><th>Prix</th><th>Stock</th></tr></thead>
            <tbody>
              {offers.map(o => (
                <tr key={o.id}><td>{o.em} {o.name}</td><td>{o.price > 0 ? "Payante" : "Gratuite"}</td><td>{o.price} DT</td><td>{o.qty}</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <div className="sec">
        <h2>Publier une offre</h2>
        <div className="card">
          <div className="form">
            <input placeholder="Nom de l’offre" value={name} onChange={e => setName(e.target.value)} />
            <input placeholder="Prix (0 = don)" type="number" value={price} onChange={e => setPrice(e.target.value)} />
            <input placeholder="Quantité" type="number" value={qty} onChange={e => setQty(e.target.value)} />
          </div>
          <button className="btn" onClick={publish}>Publier</button>
        </div>
      </div>
    </>
  );
}
