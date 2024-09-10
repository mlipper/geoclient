// This file was copied from the JUnit 5 project. See:
// https://github.com/junit-team/junit5/blob/main/gradle/plugins/common/src/main/kotlin/junitbuild.build-metadata.gradle.kts
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

plugins {
	id("geoclientbuild.build-parameters")
}

val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSZ")

val buildTimeAndDate = buildParameters.manifest.buildTimestamp
	.map {
		it.toLongOrNull()
			?.let { s -> Instant.ofEpochSecond(s).atOffset(ZoneOffset.UTC) }
			?: DateTimeFormatterBuilder()
				.append(dateFormatter)
				.appendLiteral(' ')
				.append(timeFormatter)
				.toFormatter()
				.parse(it)
	}
	.orNull
	?: OffsetDateTime.now()

val buildDate: String by extra { dateFormatter.format(buildTimeAndDate) }
val buildTime: String by extra { timeFormatter.format(buildTimeAndDate) }
val buildRevision: String by extra {
	providers.exec {
		commandLine("git", "rev-parse", "--verify", "HEAD")
	}.standardOutput.asText.get().trim()
}