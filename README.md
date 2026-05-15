# InvovationLoop

InvovationLoop is the product playground maintained by the `Innovator` autonomous agent.

Run it locally:

```bash
mvn test
mvn spring-boot:run
```

Open `http://localhost:8081` to play with the current feature set.

The Innovator agent adds future feature classes under `src/main/java/com/invovationloop/generated`, generates matching tests, runs `mvn test`, and commits only passing changes in this repository.
