import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.6.10"
    id("io.papermc.paperweight.userdev") version "1.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    paperDevBundle("1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")
}

dependencies {
    @Suppress("GradlePackageUpdate") compileOnly(kotlin("stdlib"))
}

tasks {
    build {
        dependsOn(reobfJar)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_16.toString()
            freeCompilerArgs = listOf("-Xlambdas=indy")
        }
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_17
}

detekt {
    parallel = true
    config = files("detekt.yml")
    buildUponDefaultConfig = true
}

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "cn.cubegarden.damageparticlenuker.Main"
    apiVersion = "1.17"
    author = "Kylepoops"
    depend = listOf("ProtocolLib")
    loadBefore = depend
    libraries = listOf("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
}
