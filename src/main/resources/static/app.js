const elements = {
    featureName: document.querySelector("#featureName"),
    featureStage: document.querySelector("#featureStage"),
    featureIteration: document.querySelector("#featureIteration"),
    featureStability: document.querySelector("#featureStability"),
    featurePurpose: document.querySelector("#featurePurpose"),
    featureUsage: document.querySelector("#featureUsage"),
    lastImprovement: document.querySelector("#lastImprovement"),
    nextFocus: document.querySelector("#nextFocus"),
    form: document.querySelector("#recoveryForm"),
    agentGoal: document.querySelector("#agentGoal"),
    failureSignal: document.querySelector("#failureSignal"),
    workspaceState: document.querySelector("#workspaceState"),
    constraints: document.querySelector("#constraints"),
    planStatus: document.querySelector("#planStatus"),
    planOutput: document.querySelector("#planOutput")
};

elements.form.addEventListener("submit", event => {
    event.preventDefault();
    createRecoveryPlan();
});

loadActiveFeature().then(createRecoveryPlan);

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

async function createRecoveryPlan() {
    elements.planStatus.textContent = "working";
    const payload = {
        agentGoal: elements.agentGoal.value,
        failureSignal: elements.failureSignal.value,
        workspaceState: elements.workspaceState.value,
        constraints: elements.constraints.value
    };
    const response = await fetch("/api/product/recovery-plan", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    const plan = await response.json();
    renderPlan(plan);
    elements.planStatus.textContent = "generated";
}

function renderPlan(plan) {
    elements.planOutput.replaceChildren(
        titleSection(plan.title),
        textSection("Summary", plan.summary),
        textSection("Likely Cause", plan.likelyCause),
        textSection("Operator Impact", plan.operatorImpact),
        listSection("Evidence To Collect", plan.evidenceToCollect),
        listSection("Admin Actions", plan.adminActions),
        listSection("Validation Checks", plan.validationChecks),
        listSection("Risk Flags", plan.riskFlags),
        listSection("Prevention Ideas", plan.preventionIdeas),
        textSection("Resume Prompt", plan.resumePrompt),
        textSection("Next Step", plan.nextStep)
    );
}

function titleSection(title) {
    const section = document.createElement("section");
    section.className = "plan-title";
    const heading = document.createElement("h3");
    heading.textContent = title;
    section.append(heading);
    return section;
}

function textSection(title, text) {
    const section = document.createElement("section");
    section.className = "plan-section";

    const heading = document.createElement("h3");
    heading.textContent = title;

    const body = document.createElement("p");
    body.textContent = text;

    section.append(heading, body);
    return section;
}

function listSection(title, items) {
    const section = document.createElement("section");
    section.className = "plan-section";

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
