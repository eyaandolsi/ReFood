import { useState } from "react";
import { OFFERS, ROLES } from "./data";
import ClientView from "./components/ClientView";
import MerchantView from "./components/MerchantView";
import AssoView from "./components/AssoView";
import DriverView from "./components/DriverView";
import Side from "./components/Side";

export default function App() {
  const [role, setRole] = useState("client");
  const [offers, setOffers] = useState(OFFERS);
  const [orders, setOrders] = useState([]);
  const [requests, setRequests] = useState([]);
  const [accepted, setAccepted] = useState([]);
  const r = ROLES.find(x => x.key === role);

  const order = o => {
    setOffers(offers.map(x => x.id === o.id ? { ...x, qty: x.qty - 1 } : x));
    setOrders([{ em: o.em, name: o.name, status: "En attente" }, ...orders]);
  };

  return (
    <div className="wrap">
      <header>
        <div className="logo">ReFood<small>Moins de gaspillage, plus de solidarité</small></div>
        <nav className="tabs" aria-label="Profil utilisateur">
          {ROLES.map(x => <button key={x.key} className="tab" aria-pressed={role === x.key} onClick={() => setRole(x.key)}>{x.label}</button>)}
        </nav>
      </header>
      <section className="hero"><h1>{r.title}</h1><p>{r.sub}</p></section>
      <div className="layout">
        <main>
          {role === "client" && <ClientView offers={offers} orders={orders} onOrder={order} />}
          {role === "merchant" && <MerchantView offers={offers} onPublish={o => setOffers([o, ...offers])} />}
          {role === "asso" && <AssoView requests={requests} onRequest={id => setRequests([...requests, id])} />}
          {role === "driver" && <DriverView accepted={accepted} onAccept={id => setAccepted(accepted.includes(id) ? accepted.filter(x => x !== id) : [...accepted, id])} />}
        </main>
        <Side role={role} />
      </div>
      <footer>Maquette statique ReFood · données fictives · Équipe Five Core</footer>
    </div>
  );
}
