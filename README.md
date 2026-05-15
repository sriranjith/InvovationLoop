# InvovationLoop

InvovationLoop is the product playground maintained by the `Innovator` autonomous agent.

The project has one active product feature: **Agent Recovery Console**. It turns a failed AI-agent run into a practical recovery plan with evidence to collect, admin repair actions, validation checks, risk flags, prevention ideas, and safe resume criteria.

Run it locally:

```bash
mvn test
mvn spring-boot:run
```

Open `http://localhost:8081` to use the active feature. The page shows what the feature does, how to use it, the current iteration focus, and a form for building recovery plans.

The Innovator agent should keep improving this same feature only. It runs `mvn test` and commits only passing changes in this repository.
