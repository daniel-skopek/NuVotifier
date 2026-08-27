import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
}

applyPlatformAndCoreConfiguration()
applyCommonArtifactoryConfig()
applyShadowConfiguration()

repositories {
    maven {
        name = "paper"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "tcoded"
        url = uri("https://repo.tcoded.com/releases")
    }
}

configurations {
    compileClasspath.get().extendsFrom(create("shadeOnly"))
}

dependencies {
    "compileOnly"("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
    "implementation"("com.tcoded:FoliaLib:${Versions.FOLIALIB}")
    "api"(project(":nuvotifier-api"))
    "api"(project(":nuvotifier-common"))
}


tasks.named<Copy>("processResources") {
    val internalVersion = project.ext["internalVersion"]
    inputs.property("internalVersion", internalVersion)
    filesMatching("plugin.yml") {
        expand("internalVersion" to internalVersion)
    }
}

tasks.named<Jar>("jar") {
    val projectVersion = project.version
    inputs.property("projectVersion", projectVersion)
    manifest {
        attributes("Implementation-Version" to projectVersion)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations["shadeOnly"], project.configurations["runtimeClasspath"])

    dependencies {
        include(dependency(":nuvotifier-api"))
        include(dependency(":nuvotifier-common"))
        include(dependency("com.tcoded:FoliaLib"))
    }

    relocate("com.tcoded.folialib", "com.vexsoftware.votifier.lib.folialib")
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
