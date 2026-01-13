# Use Eclipse Temurin JDK 21 (recommended for production)
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY pom.xml ./

# Copy source code
COPY src ./src

# Install Maven
RUN apk add --no-cache maven

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage - use smaller JRE image
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Install su-exec for dropping privileges
RUN apk add --no-cache su-exec

# Create database directory for persistent data
RUN mkdir -p /app/database

# Copy the built JAR from builder stage
COPY --from=builder /app/target/MythicMate-1.0-SNAPSHOT.jar /app/mythicmate.jar

# Copy entrypoint script
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Create a non-root user for security
RUN addgroup -g 1000 mythicmate && \
    adduser -D -u 1000 -G mythicmate mythicmate && \
    chown -R mythicmate:mythicmate /app

# Expose no ports (Discord bot doesn't need to listen)
# But if you add a health check endpoint later, expose it here

# Health check (optional - checks if the process is still running)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD pgrep -f "java.*mythicmate.jar" || exit 1

# Run as root initially so entrypoint can fix permissions, then drop to mythicmate user
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
