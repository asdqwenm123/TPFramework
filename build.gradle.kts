plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

        implementation("org.hibernate.orm:hibernate-hikaricp:6.6.8.Final")
        implementation("org.hibernate.orm:hibernate-core:6.6.8.Final")
        implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
        implementation("com.mysql:mysql-connector-j:9.2.0")
        implementation("org.xerial:sqlite-jdbc:3.49.0.0")
        implementation("com.zaxxer:HikariCP:6.2.1")
    }

    tasks.assemble {
        dependsOn(tasks.reobfJar)
    }
}

subprojects {
    apply(plugin = "java")
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = listOf(project.configurations.runtimeClasspath.get())

    // 서브 모듈의 클래스 파일을 포함하도록 설정
    from(project(":api").sourceSets.main.get().output)
    from(project(":core").sourceSets.main.get().output)
    from(project(":plugin").sourceSets.main.get().output)
}

tasks.reobfJar {
    dependsOn(tasks.shadowJar)
    inputJar.set(tasks.shadowJar.flatMap { it.archiveFile })
}

tasks.build {
    dependsOn(tasks.reobfJar)
}