// Copied from JUnit 5 project. See:
// https://github.com/junit-team/junit5/blob/main/gradle/plugins/common/src/main/kotlin/License.kt
import org.gradle.api.file.RegularFile
import java.net.URI

data class License(val name: String, val url: URI, val headerFile: RegularFile)
