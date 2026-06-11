# Changes Since 2.0.3 (Toward 2.0.4)

This summary covers major changes after tag `2.0.3`.

- Total commits reviewed: 212
- Baseline tag commit: `2aeb7b0f` (2025-06-09)

## Executive Summary

Since `2.0.3`, Geoclient has undergone significant modernization and stabilization work. The release stream upgrades the platform to Java 21, Spring Boot 4.0.x, and Gradle 9.5.x, while improving test/coverage orchestration and CI reporting. Native/JNI integration has been restructured and hardened, including refactoring around `geoclient-native`, better runtime resource tuning, and more reproducible amd64 container workflows. In parallel, the codebase continues to simplify architecture and packaging, improve documentation and Javadoc generation, and deliver targeted API/response-behavior fixes for better correctness and maintainability.

## Major Changes

## 1. Build and Platform Modernization

- Gradle upgraded incrementally from 9.2.1 to 9.5.1.
- Spring Boot upgraded from 3.5.x to 4.0.6 (including intermediate 4.0.3 and 4.0.5).
- Project aligned to Java 21 tooling and runtime expectations.
- Testing infrastructure migrated to the JVM Test Suite model.
- Build logic increasingly extracted into convention/custom plugins.

## 2. Native/JNI and Runtime Stability

- Native C code split back into a dedicated native project (`geoclient-native`).
- JNI/native Gradle integration refactored in multiple steps.
- Gradle JVM resources increased to reduce JNI-related segfault risk.
- JNI integration tests quieted by reducing noisy stdout/stderr logging.
- Cross-platform/container compatibility improved with explicit amd64 guidance.

## 3. Architecture and Code Organization

- Search controller and related integration tests moved into the web package structure.
- Spring-based type conversion code moved into configuration package.
- Streetcode endpoint handling consolidated in `RestController` via service-type mapping.
- Additional package-level cleanup and class moves improved cohesion.

## 4. Documentation and Developer Experience

- Dedicated `documentation` project added and integrated with build tasks.
- Aggregate Javadoc generation and cleanup tasks introduced/refined.
- Documentation updates for API behavior, logging caveats, and JNI notes.
- Copilot contextual instructions added to help contributors and tooling.

## 5. CI/CD and Build Pipeline Improvements

- CI reporting and coverage task behavior improved (Gradle + Azure Pipelines).
- Build behavior adjusted so global build can avoid unnecessary dist/asciidoc work.
- Test ordering and Jacoco sequencing refined to ensure reliable coverage output.
- Devcontainer setup moved to a custom Dockerfile with amd64 architecture enforcement.

## 6. API and Behavior Fixes

- Global error handling updated to preserve response `Content-Type`.
- Fixes for response format/order and Jackson-related markup behavior.
- OpenAPI definition updated to reflect latest API shape.
- Various endpoint and request-handling corrections (including search/intersection docs).

## 7. Cleanup and Quality Work

- Extensive Javadoc, warning cleanup, and formatting/checkstyle hygiene.
- Removal of generated or obsolete artifacts from source paths.
- Ongoing refactors to reduce technical debt and keep modules focused.

## Overall Direction

The changes since `2.0.3` represent a substantial platform and build-system modernization, plus continued architectural cleanup. The release trajectory emphasizes:

- newer toolchains (Gradle 9.5.x, Spring Boot 4.0.x, Java 21),
- more maintainable build logic and documentation workflows,
- improved JNI/native stability and container reproducibility,
- and incremental API correctness and response-format reliability.
