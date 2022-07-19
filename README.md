정통마켓 백엔드 레포지토리입니다.

## Build Spring Boot Image

```txt
.\gradlew bootBuildImage  
```

- Image Name: `settings.gradle` > `rootProject.name` (only `[a-z0-9][.][_][-]`)
- Image Version: `build.gradle` > `version`
  - [Semantic Versioning 2.0.0](https://semver.org/lang/ko/)

## Run Docker Compose

1. Create `.env`.

    Check [`.env.example`](./.env)

2. Create `db/password.txt`.

3. Run a command below.

    ```txt
    docker compose up
    ```

## (for Dev) Run MySQL only

```txt
docker compose -f .\docker-compose.db.yml up
```
