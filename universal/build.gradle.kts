import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
}

applyPlatformAndCoreConfiguration()
applyShadowConfiguration()

repositories {
    maven {
        name = "tcoded"
        url = uri("https://repo.tcoded.com/releases")
    }
}

configurations {
    compileClasspath.get().extendsFrom(create("shadeOnly"))
}

dependencies {
    "api"(project(":nuvotifier-api"))
    "api"(project(":nuvotifier-common"))
    "api"(project(":nuvotifier-bukkit"))
    "api"(project(":nuvotifier-bungeecord"))
    "api"(project(":nuvotifier-sponge"))
    "api"(project(":nuvotifier-fabric"))
    "api"(project(":nuvotifier-velocity"))
}

tasks.named<Jar>("jar") {
    val projectVersion = project.version
    inputs.property("projectVersion", projectVersion)
    manifest {
        attributes("Implementation-Version" to projectVersion)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    configurations = listOf(project.configurations["shadeOnly"], project.configurations["runtimeClasspath"])

    dependencies {
        include(dependency(":nuvotifier-api"))
        include(dependency(":nuvotifier-common"))
        include(dependency(":nuvotifier-bukkit"))
        include(dependency(":nuvotifier-bungeecord"))
        include(dependency(":nuvotifier-sponge"))
        include(dependency(":nuvotifier-fabric"))
        include(dependency(":nuvotifier-velocity"))
        include(dependency("com.tcoded:FoliaLib"))
    }

    relocate("com.tcoded.folialib", "com.vexsoftware.votifier.lib.folialib")

    exclude("GradleStart**")
    exclude(".cache");
    exclude("LICENSE*")
    exclude("META-INF/services/**")
    exclude("META-INF/maven/**")
    exclude("META-INF/versions/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    exclude("**/module-info.class")
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}