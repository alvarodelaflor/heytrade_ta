FROM openjdk:17-jdk
EXPOSE "${DOCKER_PORT}"
COPY "./target/pokedex-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT [ \
    "java", \
    "-jar", \
    "-Dserver.port=${env.PORT}", \
    "app.jar" \
]