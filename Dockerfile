FROM gradle:8-jdk11-alpine as builder

# Copy local code to the container image.
COPY build.gradle.kts .
COPY gradle.properties .
COPY src ./src

# Build a release artifact.
RUN gradle installDist

FROM amazoncorretto:11-alpine-jdk

RUN mkdir /app \
 && addgroup -g "639" app \
 && adduser -u "639" -G app -D -h /app app \
 && chown -R app:app /app

COPY --from=builder --chown=app:app /home/gradle/build/install/gradle /app/
WORKDIR /app/bin
USER app
CMD ["./gradle"]