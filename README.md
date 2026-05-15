# InvovationLoop

InvovationLoop is the product playground maintained by the `Innovator` autonomous agent.

The project now starts with one active product feature: **Opportunity Brief Studio**. It turns an industry, audience, problem, and constraint into a practical opportunity brief with MVP scope, validation steps, success metrics, risks, and a next action.

Run it locally:

```bash
mvn test
mvn spring-boot:run
```

Open `http://localhost:8081` to use the active feature. The page shows what the feature does, how to use it, the current iteration focus, and a form for generating briefs.

The Innovator agent should keep improving this one feature until it is useful and stable. It runs `mvn test` and commits only passing changes in this repository. Once the active feature is stable, the next product feature can be selected deliberately.
