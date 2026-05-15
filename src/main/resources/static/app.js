const elements = {
    featureName: document.querySelector("#featureName"),
    featureStage: document.querySelector("#featureStage"),
    featureIteration: document.querySelector("#featureIteration"),
    featureStability: document.querySelector("#featureStability"),
    featurePurpose: document.querySelector("#featurePurpose"),
    featureUsage: document.querySelector("#featureUsage"),
    lastImprovement: document.querySelector("#lastImprovement"),
    nextFocus: document.querySelector("#nextFocus"),
    form: document.querySelector("#briefForm"),
    industry: document.querySelector("#industry"),
    audience: document.querySelector("#audience"),
    problem: document.querySelector("#problem"),
    constraints: document.querySelector("#constraints"),
    briefStatus: document.querySelector("#briefStatus"),
    briefOutput: document.querySelector("#briefOutput")
};

elements.form.addEventListener("submit", event => {
    event.preventDefault();
    createBrief();
});

loadActiveFeature().then(createBrief);

async function loadActiveFeature() {
    const response = await fetch("/api/product/active");
    const feature = await response.json();
    elements.featureName.textContent = feature.name;
    elements.featureStage.textContent = feature.stage;
    elements.featureIteration.textContent = feature.iteration;
    elements.featureStability.textContent = `${feature.stabilityScore}/100`;
    elements.featurePurpose.textContent = feature.whatItDoes;
    elements.featureUsage.textContent = feature.howToUse;
    elements.lastImprovement.textContent = feature.lastImprovement;
    elements.nextFocus.textContent = feature.nextIterationFocus;
}

async function createBrief() {
    elements.briefStatus.textContent = "working";
    const payload = {
        industry: elements.industry.value,
        audience: elements.audience.value,
        problem: elements.problem.value,
        constraints: elements.constraints.value
    };
    const response = await fetch("/api/product/brief", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    const brief = await response.json();
    renderBrief(brief);
    elements.briefStatus.textContent = "generated";
}

function renderBrief(brief) {
    elements.briefOutput.replaceChildren(
        titleSection(brief.title),
        textSection("Summary", brief.summary),
        textSection("Job To Be Done", brief.jobToBeDone),
        textSection("Proposed Solution", brief.proposedSolution),
        textSection("Why Now", brief.whyNow),
        listSection("MVP Scope", brief.mvpScope),
        listSection("Validation Plan", brief.validationPlan),
        listSection("Success Metrics", brief.successMetrics),
        listSection("Risks", brief.risks),
        textSection("Next Step", brief.nextStep)
    );
}

function titleSection(title) {
    const section = document.createElement("section");
    section.className = "brief-title";
    const heading = document.createElement("h3");
    heading.textContent = title;
    section.append(heading);
    return section;
}

function textSection(title, text) {
    const section = document.createElement("section");
    section.className = "brief-section";

    const heading = document.createElement("h3");
    heading.textContent = title;

    const body = document.createElement("p");
    body.textContent = text;

    section.append(heading, body);
    return section;
}

function listSection(title, items) {
    const section = document.createElement("section");
    section.className = "brief-section";

    const heading = document.createElement("h3");
    heading.textContent = title;

    const list = document.createElement("ul");
    (items || []).forEach(item => {
        const li = document.createElement("li");
        li.textContent = item;
        list.append(li);
    });

    section.append(heading, list);
    return section;
}
