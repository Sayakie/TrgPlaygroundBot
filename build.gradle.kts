import java.time.Instant
import java.time.format.DateTimeFormatter

plugins {
  id("com.github.johnrengelman.shadow") version "7.1.2"
  `java-library`
}

group = "io.github.sayakie"
version = "1.0-SNAPSHOT"

val theManifest = the<JavaPluginExtension>().manifest {
  attributes(
    "Specification-Title" to project.name,
    "Specification-Vendor" to "DareHaru",
    "Specification-Version" to version,
    "Implementation-Title" to project.name,
    "Implementation-Vendor" to "DareHaru",
    "Implementation-Version" to version,
    "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
    "Main-Class" to "dareharu.triggerreactor.util.Main"
  )

  /// These two are included by most CI's
  System.getenv()["GIT_COMMIT"]?.apply { attributes("Git-Commit" to this) }
  System.getenv()["GIT_BRANCH"]?.apply { attributes("Git-Branch" to this) }
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")

  flatDir {
    dirs = setOf(rootDir.resolve("lib"))
  }
}

dependencies {
  shadow("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
  shadow("com.github.seeseemelk:MockBukkit-v1.20:3.9.0")
  shadow("javax.inject:javax.inject:1")
  shadow(fileTree("dir" to "lib", "include" to listOf("*.jar")))
  shadow("com.google.guava:guava:32.1.2-jre")
  shadow("net.dv8tion:JDA:5.0.0-beta.12") {
    exclude(module = "opus-java")
  }
  shadow("com.moandjiezana.toml:toml4j:0.7.2")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks {
  withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  jar {
    manifest.from(theManifest)

    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")

    finalizedBy(shadowJar)
  }

  shadowJar {
    configurations.apply {
      clear()
      add(project.configurations.shadow.get())
    }
  }
}

tasks.test {
  useJUnitPlatform()
}
