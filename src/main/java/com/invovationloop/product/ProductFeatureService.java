package com.invovationloop.product;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ProductFeatureService {
    private static final String STATE_RESOURCE = "active-feature-state.properties";

    public ActiveProductFeature activeFeature() {
        Properties state = loadState();
        return new ActiveProductFeature(
                value(state, "feature.id", "agent-recovery-console"),
                value(state, "feature.name", "Agent Recovery Console"),
                value(state, "feature.stage", "prototype"),
                integer(state, "feature.iteration", 1),
                integer(state, "feature.stabilityScore", 35),
                value(state, "feature.problem", "AI agents fail mid-run without clear diagnosis, repair ownership, or safe resume criteria."),
                value(state, "feature.audience", "AI agent operators, platform admins, and developers maintaining autonomous workflows"),
                "Turns a failed AI-agent run into a repair plan with evidence, admin actions, validation checks, and safe resume criteria.",
                "Paste the agent goal, failure signal, workspace state, and constraints. Use the plan to fix the issue, validate locally, then resume the agent.",
                value(state, "feature.lastImprovement", "Reset to one AI-agent recovery feature."),
                value(state, "feature.nextIterationFocus", "Improve diagnosis quality, admin repair steps, and safe resume criteria."),
                List.of(
                        "One feature only",
                        "Failure visibility before autonomy",
                        "Admin repair before retry",
                        "Local validation before resume"
                )
        );
    }

    public RecoveryPlan createRecoveryPlan(RecoveryPlanRequest request) {
        String agentGoal = clean(request == null ? "" : request.agentGoal(), "complete the current autonomous coding task");
        String failureSignal = clean(request == null ? "" : request.failureSignal(), "the agent stopped with an unclear error");
        String workspaceState = clean(request == null ? "" : request.workspaceState(), "workspace state is unknown");
        String constraints = clean(request == null ? "" : request.constraints(), "avoid destructive commands and preserve user changes");

        return new RecoveryPlan(
                titleFor(failureSignal),
                "Recovery plan for an AI agent trying to " + agentGoal + ".",
                likelyCause(failureSignal, workspaceState),
                "The agent should pause instead of retrying blindly because the workspace may need human repair under: " + constraints + ".",
                List.of(
                        "Exact error message or failed command output",
                        "Current git status and any uncommitted files",
                        "Last successful local test command",
                        "Relevant service logs, API response, or authentication state"
                ),
                List.of(
                        "Classify whether the failure is configuration, credentials, test regression, merge conflict, or unavailable service",
                        "Fix only the files or settings related to the failure",
                        "Re-run the narrow failing command first",
                        "Run the full local verification command before resuming the agent"
                ),
                List.of(
                        "No unexpected git changes outside the intended workspace",
                        "The previous failing command now exits successfully",
                        "The application starts or the affected API responds locally",
                        "The resume request includes the fix summary and remaining risk"
                ),
                riskFlags(failureSignal, constraints),
                List.of(
                        "Capture the failing phase in the agent live feed",
                        "Store the repair checklist with the blocked run",
                        "Require a human resume action after credentials, tests, or git failures",
                        "Keep future retries scoped to the original feature"
                ),
                "Resume only after the admin confirms the fix, local validation result, and whether any files were intentionally left changed.",
                "Fix the highest-confidence cause, run local validation, then resume the agent from the Innovator UI."
        );
    }

    private List<String> riskFlags(String failureSignal, String constraints) {
        String normalized = (failureSignal + " " + constraints).toLowerCase();
        if (normalized.contains("auth") || normalized.contains("credential") || normalized.contains("oci")) {
            return List.of(
                    "Credentials or session auth may be expired",
                    "Do not retry until the admin refreshes the session",
                    "Confirm the configured compartment, profile, and region before resume"
            );
        }
        if (normalized.contains("test") || normalized.contains("compile")) {
            return List.of(
                    "A code or dependency regression may be present",
                    "Do not commit until the full test suite passes",
                    "Inspect generated changes before retrying the loop"
            );
        }
        return List.of(
                "The failure may hide an unsafe workspace state",
                "Repeated retries can create noisy commits",
                "Resume only after a concrete validation signal is available"
        );
    }

    private String likelyCause(String failureSignal, String workspaceState) {
        String normalized = (failureSignal + " " + workspaceState).toLowerCase();
        if (normalized.contains("auth") || normalized.contains("credential") || normalized.contains("oci")) {
            return "The agent likely needs an OCI/session-auth or configuration repair before it can reason again.";
        }
        if (normalized.contains("test") || normalized.contains("compile")) {
            return "The agent likely produced or encountered a local code/test regression that must be fixed before resume.";
        }
        if (normalized.contains("git") || normalized.contains("push") || normalized.contains("commit")) {
            return "The agent likely reached a source-control boundary that needs admin attention.";
        }
        return "The run needs structured triage because the failing signal does not yet identify one safe repair path.";
    }

    private Properties loadState() {
        Properties properties = new Properties();
        Path sourceState = Path.of("src/main/resources/" + STATE_RESOURCE);
        if (Files.exists(sourceState)) {
            try (var input = Files.newInputStream(sourceState)) {
                properties.load(input);
                return properties;
            } catch (IOException exception) {
                throw new UncheckedIOException("Unable to read active product feature source state.", exception);
            }
        }

        ClassPathResource resource = new ClassPathResource(STATE_RESOURCE);
        if (!resource.exists()) {
            return properties;
        }
        try (var input = resource.getInputStream()) {
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new UncheckedIOException("Unable to read active product feature state.", exception);
        }
    }

    private String titleFor(String failureSignal) {
        String compactSignal = failureSignal.length() > 52 ? failureSignal.substring(0, 52).strip() + "..." : failureSignal;
        return "Agent Recovery Plan: " + compactSignal;
    }

    private String clean(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.strip();
    }

    private String value(Properties properties, String key, String fallback) {
        return clean(properties.getProperty(key), fallback);
    }

    private int integer(Properties properties, String key, int fallback) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return fallback;
        }
        try {
            return Integer.parseInt(value.strip());
        } catch (NumberFormatException exception) {
            return fallback;
        }
    }
}
