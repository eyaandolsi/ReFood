import { MISSIONS } from "../data";

export default function DriverView({ accepted, onAccept }) {
  return (
    <div className="sec">
      <h2>Missions</h2>
      {MISSIONS.map(m => (
        <div className="row" key={m.id}>
          <div>
            <b>{m.kind}</b> · {m.from} → {m.to}
            <div className="muted">{m.km} km</div>
          </div>
          <button className={accepted.includes(m.id) ? "btn alt" : "btn"} style={{ marginTop: 0 }} onClick={() => onAccept(m.id)}>
            {accepted.includes(m.id) ? "Acceptée ✓" : "Accepter"}
          </button>
        </div>
      ))}
    </div>
  );
}
