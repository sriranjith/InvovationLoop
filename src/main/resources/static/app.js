const inputs = {
    novelty: document.querySelector("#novelty"),
    reliability: document.querySelector("#reliability"),
    simplicity: document.querySelector("#simplicity")
};

const labels = {
    novelty: document.querySelector("#noveltyValue"),
    reliability: document.querySelector("#reliabilityValue"),
    simplicity: document.querySelector("#simplicityValue")
};

const featureGrid = document.querySelector("#featureGrid");
const projectGrid = document.querySelector("#projectGrid");
const activeFeature = document.querySelector("#activeFeature");
const scoreValue = document.querySelector("#scoreValue");
const refreshButton = document.querySelector("#refreshButton");

const usableProjects = [
    {
        title: "Innovator Agent",
        url: "http://localhost:8080",
        role: "autonomous maintainer",
        summary: "Start or stop the agent, watch its thinking, and let it commit tested changes into InvovationLoop."
    },
    {
        title: "InvovationLoop Playground",
        url: "http://localhost:8081",
        role: "feature playground",
        summary: "Play with generated features, tune scores, and inspect the usable innovations the agent has added."
    }
];

Object.entries(inputs).forEach(([key, input]) => {
    input.addEventListener("input", () => {
        labels[key].textContent = input.value;
    });
});

refreshButton.addEventListener("click", loadFeatures);
renderProjects();
loadFeatures();

function renderProjects() {
    projectGrid.replaceChildren(...usableProjects.map(project => {
        const card = document.createElement("article");
        card.className = "project-card";

        const role = document.createElement("span");
        role.className = "project-role";
        role.textContent = project.role;

        const title = document.createElement("h3");
        title.textContent = project.title;

        const summary = document.createElement("p");
        summary.textContent = project.summary;

        const link = document.createElement("a");
        link.href = project.url;
        link.textContent = "Open";
        link.className = "open-link";

        card.append(role, title, summary, link);
        return card;
    }));
}

async function loadFeatures() {
    const response = await fetch("/api/features");
    const features = await response.json();
    renderFeatures(features);
}

function renderFeatures(features) {
    if (!features.length) {
        const empty = document.createElement("p");
        empty.textContent = "No features are available yet.";
        featureGrid.replaceChildren(empty);
        return;
    }

    featureGrid.replaceChildren(...features.map(feature => {
        const card = document.createElement("article");
        card.className = "feature-card";

        const title = document.createElement("h3");
        title.textContent = feature.title;

        const category = document.createElement("span");
        category.className = "category";
        category.textContent = feature.category;

        const summary = document.createElement("p");
        summary.className = "feature-summary";
        summary.textContent = feature.summary;

        const purpose = detailBlock("What it does", feature.purpose || feature.summary);
        const usage = detailBlock("How to use it", feature.usage || "Adjust the sliders, then press Play.");

        const play = document.createElement("button");
        play.className = "play";
        play.type = "button";
        play.textContent = "Play";
        play.addEventListener("click", () => playFeature(feature, card));

        const result = document.createElement("div");
        result.className = "result";
        result.setAttribute("aria-live", "polite");

        card.append(category, title, summary, purpose, usage, play, result);
        return card;
    }));
}

function detailBlock(labelText, bodyText) {
    const block = document.createElement("div");
    block.className = "feature-detail";

    const label = document.createElement("span");
    label.textContent = labelText;

    const body = document.createElement("p");
    body.textContent = bodyText;

    block.append(label, body);
    return block;
}

async function playFeature(feature, card) {
    const payload = {
        novelty: Number(inputs.novelty.value),
        reliability: Number(inputs.reliability.value),
        simplicity: Number(inputs.simplicity.value)
    };
    const response = await fetch(`/api/features/${feature.id}/play`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    const result = await response.json();
    activeFeature.textContent = result.title;
    scoreValue.textContent = result.score;
    const resultElement = card.querySelector(".result");
    resultElement.className = `result ${result.recommendation}`;
    resultElement.textContent = `${result.recommendation} at ${result.score}`;
}
