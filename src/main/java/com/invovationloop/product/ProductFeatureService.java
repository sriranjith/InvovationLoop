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
                value(state, "feature.id", "opportunity-brief-studio"),
                value(state, "feature.name", "Opportunity Brief Studio"),
                value(state, "feature.stage", "prototype"),
                integer(state, "feature.iteration", 1),
                integer(state, "feature.stabilityScore", 35),
                value(state, "feature.problem", "Turn broad industry problems into a useful product brief."),
                value(state, "feature.audience", "Builders, founders, product teams, and operators"),
                "Turns an industry, audience, problem, and constraint into a practical product opportunity brief.",
                "Fill in the problem input, generate a brief, then use the validation plan with one real target user.",
                value(state, "feature.lastImprovement", "Initial useful brief generator"),
                value(state, "feature.nextIterationFocus", "Improve evidence gathering and prioritization"),
                List.of(
                        "One active feature at a time",
                        "Useful output before clever automation",
                        "Local tests before every commit",
                        "Iterate until the product is stable"
                )
        );
    }

    public OpportunityBrief createBrief(OpportunityBriefRequest request) {
        String industry = clean(request == null ? "" : request.industry(), "the selected industry");
        String audience = clean(request == null ? "" : request.audience(), "teams who feel this pain");
        String problem = clean(request == null ? "" : request.problem(), "a costly workflow that still relies on manual judgment");
        String constraints = clean(request == null ? "" : request.constraints(), "limited time, limited budget, and a need to prove value quickly");

        String title = titleFor(industry, problem);
        return new OpportunityBrief(
                title,
                "A focused product opportunity for " + audience + " in " + industry + ".",
                audience,
                "When " + audience + " face " + problem + ", they need a practical way to decide what to do next without adding more process.",
                "Create a lightweight workspace that captures the problem, proposes a first workflow, and turns the result into a testable next action.",
                "The opportunity is useful now because many teams need faster prioritization while operating under " + constraints + ".",
                List.of(
                        "Capture the problem, affected audience, and operating constraints",
                        "Generate a plain-language opportunity brief",
                        "Recommend a small validation experiment",
                        "Track the current iteration and stability of the product feature"
                ),
                List.of(
                        "Interview five target users and compare their current workaround",
                        "Run one manual concierge version of the brief workflow",
                        "Measure whether users can decide a next action in under ten minutes",
                        "Keep the feature local until the workflow proves repeatable"
                ),
                List.of(
                        "Time from problem entry to useful brief",
                        "Percent of briefs that lead to a concrete next action",
                        "Number of validation conversations completed",
                        "Repeat usage by the same team"
                ),
                List.of(
                        "The problem may be too broad without a narrower audience",
                        "Generated recommendations may need stronger evidence",
                        "Users may need collaboration or export before it becomes part of real work"
                ),
                "Use this brief with one real user, record what was unclear, then let Innovator improve this same feature."
        );
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

    private String titleFor(String industry, String problem) {
        String compactProblem = problem.length() > 44 ? problem.substring(0, 44).strip() + "..." : problem;
        return "Opportunity Brief: " + compactProblem + " in " + industry;
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
