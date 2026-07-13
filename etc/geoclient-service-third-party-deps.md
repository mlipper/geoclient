# `geoclient-service` — Per-Package Third-Party Dependencies

Report of direct third-party library dependencies for source code in
`geoclient-service/src/main/java`, grouped by Java package.

**Scope / exclusions**

- Only direct `import` statements are counted (no transitive dependencies).
- Internal packages (`gov.nyc.doitt.gis.geoclient.*`) are excluded.
- JDK packages (`java.*`, `javax.*`) are excluded.
- Logging (`org.slf4j.*`) is excluded per request.
- `jakarta.*` and `org.jspecify.*` are treated as third-party (provided by
  non-JDK artifacts on the classpath).
- Classes referenced only through transitive types on APIs (e.g. Spring MVC
  types returned via `ResponseEntity`) are not listed unless directly imported.

---

## `gov.nyc.doitt.gis.geoclient.service`

- **Spring Boot** (`org.springframework.boot.*`)
  - `org.springframework.boot`
  - `org.springframework.boot.autoconfigure`
  - `org.springframework.boot.builder`
  - `org.springframework.boot.web.servlet.support`

## `gov.nyc.doitt.gis.geoclient.service.configuration`

- **Spring Boot Actuator** (`org.springframework.boot.actuate.*`)
  - `org.springframework.boot.actuate.web.exchanges`
- **Spring Framework** (`org.springframework.*`)
  - `org.springframework.beans.factory.annotation`
  - `org.springframework.context.annotation`
  - `org.springframework.core.convert.converter`
  - `org.springframework.core.env`
  - `org.springframework.format`
  - `org.springframework.http`
  - `org.springframework.web.servlet.config.annotation`

## `gov.nyc.doitt.gis.geoclient.service.domain`

- **Jackson 2 annotations** (`com.fasterxml.jackson.*`)
  - `com.fasterxml.jackson.annotation`

Only `GeosupportResponse` (extends `HashMap`) remains in this package; its
`@JsonRootName` annotation stays in-place. Framework-neutral HR-response
value objects previously in this package have been moved to
`gov.nyc.doitt.gis.geoclient.api.version` in `geoclient-core`.

## `gov.nyc.doitt.gis.geoclient.service.mapper`

- *(no third-party imports)*

Only `ResponseStatusMapper` remains in this package. The general-purpose
`Mapper` abstraction and other implementations moved to
`gov.nyc.doitt.gis.geoclient.api.mapper` in `geoclient-core`.
`ResponseStatusMapper` stays here because it depends on `geoclient-search`
types (moving it to core would create a cyclic module dependency).

## `gov.nyc.doitt.gis.geoclient.service.sanitizer`

- **Jakarta Servlet API** (`jakarta.servlet.*`)
  - `jakarta.servlet`
  - `jakarta.servlet.http`
- **Spring Framework** (`org.springframework.*`)
  - `org.springframework.stereotype`
  - `org.springframework.web.filter`

## `gov.nyc.doitt.gis.geoclient.service.web`

- **JSpecify** (`org.jspecify.*`)
  - `org.jspecify.annotations`
- **Spring Framework** (`org.springframework.*`)
  - `org.springframework.beans.factory.annotation`
  - `org.springframework.core.convert`
  - `org.springframework.http`
  - `org.springframework.web`
  - `org.springframework.web.bind.annotation`
  - `org.springframework.web.context.request`
  - `org.springframework.web.servlet.mvc.method.annotation`

## `gov.nyc.doitt.gis.geoclient.service.web.filter`

- **Jakarta Servlet API** (`jakarta.servlet.*`)
  - `jakarta.servlet`
  - `jakarta.servlet.http`
- **Spring Framework** (`org.springframework.*`)
  - `org.springframework.stereotype`
  - `org.springframework.web.filter`

## `gov.nyc.doitt.gis.geoclient.service.web.jackson`

- **Jackson 2 annotations** (`com.fasterxml.jackson.*`)
  - `com.fasterxml.jackson.annotation`
- **Jackson 3 XML annotations** (`tools.jackson.*`)
  - `tools.jackson.dataformat.xml.annotation`
- **Spring Boot Jackson** (`org.springframework.boot.jackson`)
  - `org.springframework.boot.jackson` (Spring Boot 4 `@JacksonMixin`)

Wire-format contract for the REST API. Concentrates all Jackson annotation
metadata for framework-neutral domain classes from `geoclient-core` and
`geoclient-parser` via `@JacksonMixin`-annotated abstract classes that
Spring Boot 4 auto-registers.

## `gov.nyc.doitt.gis.geoclient.service.web.search.response`

- **Jackson 2 annotations** (`com.fasterxml.jackson.*`)
  - `com.fasterxml.jackson.annotation`
- **Jackson 3 XML annotations** (`tools.jackson.*`)
  - `tools.jackson.dataformat.xml.annotation`

---

## Summary — libraries used by the service module

| Library grouping                       | Used in packages                                                        |
| -------------------------------------- | ----------------------------------------------------------------------- |
| Spring Framework                       | `service.configuration`, `service.sanitizer`, `service.web`, `service.web.filter` |
| Spring Boot                            | `service`                                                               |
| Spring Boot Actuator                   | `service.configuration`                                                 |
| Spring Boot Jackson                    | `service.web.jackson`                                                   |
| Jakarta Servlet API                    | `service.sanitizer`, `service.web.filter`                               |
| Jackson 2 (annotations)                | `service.domain`, `service.web.jackson`, `service.web.search.response`  |
| Jackson 3 (XML dataformat annotations) | `service.web.jackson`, `service.web.search.response`                    |
| JSpecify                               | `service.web`                                                           |

### Excluded — logging (`org.slf4j.*`)

Directly imported by:

- `gov.nyc.doitt.gis.geoclient.service.mapper`
- `gov.nyc.doitt.gis.geoclient.service.sanitizer`
- `gov.nyc.doitt.gis.geoclient.service.web`
- `gov.nyc.doitt.gis.geoclient.service.web.filter`
