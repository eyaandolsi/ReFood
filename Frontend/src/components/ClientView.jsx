import { useState } from "react";
import { CATS } from "../data";

export default function ClientView({ offers, orders, onOrder }) {
  const [cat, setCat] = useState("Toutes");
  const list = offers.filter(o => cat === "Toutes" || o.cat === cat);
  return (
    <>
      <div className="sec">
        <h2>Offres disponibles</h2>
        <div className="chips">
          {CATS.map(c => <button key={c} className="chip" aria-pressed={cat === c} onClick={() => setCat(c)}>{c}</button>)}
        </div>
        <div className="grid">
          {list.map(o => (
            <div className="card" key={o.id}>
              <div className="em">{o.em}</div>
              <h3>{o.name}</h3>
              <div className="muted">{o.shop} · retrait {o.time}</div>
              <div className="price">{o.price} DT<s>{o.old} DT</s></div>
              <span className="badge">{o.qty} restant{o.qty > 1 ? "s" : ""}</span>
              <button className="btn" disabled={o.qty === 0} onClick={() => onOrder(o)}>{o.qty === 0 ? "Épuisé" : "Commander"}</button>
            </div>
          ))}
        </div>
      </div>
      <div className="sec">
        <h2>Mes commandes</h2>
        {orders.length === 0 && <p className="muted">Aucune commande pour le moment.</p>}
        {orders.map((o, i) => (
          <div className="row" key={i}><span>{o.em} {o.name}</span><span className="status">{o.status}</span></div>
        ))}
      </div>
    </>
  );
}
