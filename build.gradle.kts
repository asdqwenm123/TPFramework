plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    id("com.gradleup.shadow") version "9.0.0-beta8"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.papermc.paperweight.userdev")

    group = "kr.tpmc"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    }

    tasks.assemble {
        dependsOn(tasks.reobfJar)
    }

}

subprojects {
    apply(plugin = "java")
}

tasks.jar {
    from(project(":api").sourceSets.main.get().output)
    from(project(":core").sourceSets.main.get().output)
    from(project(":plugin").sourceSets.main.get().output)
}
