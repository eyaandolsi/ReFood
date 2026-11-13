
export default function Side({ role }) {
  return (
    <aside className="side">
      <div className="card">
        <h3>🌱 EcoRewards</h3>
        <div className="price">240 points</div>
        <span className="badge">Niveau 3 · Anti-gaspi Argent</span>
        <div className="bar"><i style={{ width: "68%" }} /></div>
        <div className="muted">60 points avant le prochain badge</div>
      </div>
      <div className="card">
        <h3>Classement</h3>
        <div className="lb"><span>1. Sami</span><b>520</b></div>
        <div className="lb"><span>2. Lina</span><b>410</b></div>
        <div className="lb"><span>3. Vous</span><b>240</b></div>
      </div>
      {role === "merchant" && (
        <div className="card">
          <h3>🤖 Prévision des surplus</h3>
          <div className="muted">Food Intelligence : surplus probable demain</div>
          <div className="chart" style={{ marginBottom: 22 }}>
            {[["L", 40], ["M", 55], ["M", 35], ["J", 70], ["V", 90], ["S", 60]].map(([d, h], i) => (
              <div key={i} style={{ height: h + "%" }}><span>{d}</span></div>
            ))}
          </div>
        </div>
      )}
    </aside>
  );
}
