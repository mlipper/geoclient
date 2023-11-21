package com.digitalclash.geoclient.gradle

import java.nio.file.Files

import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import spock.lang.TempDir
import spock.lang.Specification

import static com.digitalclash.geoclient.gradle.internal.GeoclientConfigResolver.DEFAULT_JNI_VERSION
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_LINUX
import static com.digitalclash.geoclient.gradle.internal.GeosupportConfigResolver.DEFAULT_HOME_WINDOWS

class ConfigurationTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile
    String defaultGeosupportHome

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
        defaultGeosupportHome = DEFAULT_HOME_LINUX
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            defaultGeosupportHome = DEFAULT_HOME_WINDOWS
        }
    }

    def printFile(File file) {
        if(file.exists()) {
            println("Contents of ${file}:")
            println(Files.readString(file.toPath()))
        } else {
            println("File ${file} does not exist.")
        }
    }

    def configurationTest() {
        given:
        settingsFile << "rootProject.name = 'goat-farm'"
        buildFile << """
plugins {
    id 'java-library'
    id 'com.digitalclash.geoclient.gradle.geosupport-integration-test'
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

geosupportApplication {
    geosupport {
        geofiles = '${defaultGeosupportHome}/fls/'
    }
    integrationTestOptions {
        testName = 'geosupportIntegrationTest'
    }
}

geosupportIntegrationTest {
    useJUnitPlatform()
    testLogging {
        info {
            events "failed", "skipped", "passed"
            showStandardStreams = true
        }
    }
}

tasks.register('printConfigurations') {
    def allConfigurations = configurations.all
    doLast {
        println "Configurations:"
        allConfigurations.each { c ->
            println "\${c.name}"
            println '----------------------------'
            def allArtifacts = c.allArtifacts
            if (allArtifacts) {
                allArtifacts?.each { a ->
                    String aType = a.getType()
                    aType = aType ? " (\${a.getType()})": ''
                    println "  [artifact] \${a.getName()}\${aType}: \${a.getFile()}"
                }
            } else {
                println '  [artifact] none'
            }
            println()
        }
    }
}

tasks.register('printSourceSets') {
    doLast{
        sourceSets.each { srcSet ->
            println "[\${srcSet.name}]"
            println "-->Source directories: \${srcSet.allJava.srcDirs}"
            println "-->Output directories: \${srcSet.output.classesDirs.files}"
            println "-->Compile classpath:"
            srcSet.compileClasspath.files.each {
                println "  \${it.path}"
            }
            println ""
        }
    }
}

tasks.register('config') {
    //dependsOn tasks.printConfigurations
    //dependsOn tasks.printSourceSets
    dependsOn tasks.geosupportIntegrationTest
    doLast {
        println 'done.'
    }
}
        """
        printFile(buildFile)
        File iTestJavaSrcDir = new File(testProjectDir, "src/geosupportIntegrationTest/java/com/example")
        iTestJavaSrcDir.mkdirs()
        File iTestResourcesDir = new File(testProjectDir, "src/geosupportIntegrationTest/resources")
        iTestResourcesDir.mkdirs()
        File junitSrcFile = new File(iTestJavaSrcDir, 'GoatTest.java')
        junitSrcFile << """
        package com.example;

        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.assertEquals;

        public class GoatTest {
            @Test
            public void testEnvironment() {
                System.out.println(String.format("GEOFILES: %s", System.getenv("GEOFILES")));
                assertEquals("${defaultGeosupportHome}/fls/", System.getenv("GEOFILES"));
            }
        }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            //.withArguments('config')
            .withArguments('geosupportIntegrationTest')
            .withPluginClasspath()
            .forwardOutput()
            .build()

        then:
        result.task(":geosupportIntegrationTest").outcome == SUCCESS
    }
}
