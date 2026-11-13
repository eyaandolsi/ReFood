import { FREE } from "../data";

export default function AssoView({ requests, onRequest }) {
  return (
    <>
      <div className="sec">
        <h2>Dons gratuits disponibles</h2>
        <div className="grid">
          {FREE.map(d => (
            <div className="card" key={d.id}>
              <div className="em">{d.em}</div>
              <h3>{d.name}</h3>
              <div className="muted">{d.shop}</div>
              <span className="badge">{d.qty} portions</span>
              <button className="btn" disabled={requests.includes(d.id)} onClick={() => onRequest(d.id)}>
                {requests.includes(d.id) ? "Demande envoyée" : "Demander ce don"}
              </button>
            </div>
          ))}
        </div>
      </div>
      <div className="sec">
        <h2>Nos besoins déclarés</h2>
        <div className="row"><span>Produits frais · 30 portions / semaine</span><span className="status">Actif</span></div>
        <div className="row"><span>Pain et viennoiseries · 50 pièces / semaine</span><span className="status">Actif</span></div>
      </div>
    </>
  );
}
