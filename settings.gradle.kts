val pluginGroup = providers.gradleProperty("plugin_group").getOrElse("net.ofatech")
val pluginName = providers.gradleProperty("plugin_name").getOrElse("ProjectTemplateHytaleMod")
val pluginMain = providers.gradleProperty("plugin_main").getOrElse("net.ofatech.hytaletemplate.TemplatePlugin")

rootProject.name = pluginName

plugins {
    // See documentation on https://scaffoldit.dev
    id("dev.scaffoldit") version "0.2.+"
}

// Would you like to do a split project?
// Create a folder named "common", then configure details with `common { }`

hytale {
    usePatchline("release")
    useVersion("latest")

    repositories {
        // Any external repositories besides: MavenLocal, MavenCentral, HytaleMaven, and CurseMaven
    }

    dependencies {
        // Any external dependency you also want to include
        implementation("com.google.code.gson:gson:2.10.1")
    }

    manifest {
        Group = pluginGroup
        Name = pluginName
        Main = pluginMain
    }
}
