/* Grouped SVG chart. Uses the same rows as the report table; no CDN required.
 * Author: John Aquino | Since: 20261022 */
(() => {
    "use strict";
    const svg = document.getElementById("matchChart");
    const rows = Array.from(document.querySelectorAll(".match-row"));
    if (!svg || rows.length === 0) return;
    const ns = "http://www.w3.org/2000/svg";
    const left = 65, top = 25, plotHeight = 240, groupWidth = 145;
    const width = Math.max(760, left + rows.length * groupWidth + 25);
    const height = 400, bottom = top + plotHeight;
    svg.setAttribute("width", width);
    svg.setAttribute("height", height);
    svg.setAttribute("viewBox", `0 0 ${width} ${height}`);
    function add(tag, attrs, text) {
        const el = document.createElementNS(ns, tag);
        Object.entries(attrs).forEach(([key, value]) => el.setAttribute(key, value));
        if (text !== undefined) el.textContent = text;
        svg.appendChild(el);
        return el;
    }
    let max = 1;
    rows.forEach(row => { max = Math.max(max, Number(row.dataset.teamPoints), Number(row.dataset.opponentPoints)); });
    const ceiling = Math.ceil(max / 5) * 5;
    for (let tick = 0; tick <= 5; tick++) {
        const y = bottom - tick * plotHeight / 5;
        add("line", {x1: left, y1: y, x2: width - 20, y2: y, stroke: "#d9e1db"});
        add("text", {x: left - 10, y: y + 4, "text-anchor": "end", "font-size": 12}, String(ceiling * tick / 5));
    }
    add("text", {x: 18, y: 160, transform: "rotate(-90 18 160)", "font-size": 13}, "Points scored");
    rows.forEach((row, index) => {
        const x = left + index * groupWidth + 20;
        const values = [Number(row.dataset.teamPoints), Number(row.dataset.opponentPoints)];
        values.forEach((value, series) => {
            const barHeight = value / ceiling * plotHeight;
            const barX = x + series * 43;
            const bar = add("rect", {x: barX, y: bottom - barHeight, width: 35, height: barHeight,
                fill: series === 0 ? "#77dd77" : "#506b9d"});
            const title = document.createElementNS(ns, "title");
            title.textContent = `${series === 0 ? "Team" : "Opponent"}: ${value}`;
            bar.appendChild(title);
            add("text", {x: barX + 17, y: bottom - barHeight - 5, "text-anchor": "middle", "font-size": 12}, String(value));
        });
        add("text", {x: x + 38, y: bottom + 22, "text-anchor": "middle", "font-size": 12}, row.dataset.date);
        const opponent = row.dataset.opponent;
        add("text", {x: x + 38, y: bottom + 42, "text-anchor": "middle", "font-size": 12}, opponent.length > 17 ? opponent.slice(0, 16) + "…" : opponent);
        add("text", {x: x + 38, y: bottom + 61, "text-anchor": "middle", "font-size": 12}, "#" + row.dataset.id);
    });
    add("text", {x: width / 2, y: height - 15, "text-anchor": "middle", "font-size": 13}, "Match date / opponent / ID");
})();
