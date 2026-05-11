# Using This Template

Use this checklist to rename and customize the template safely:

1. Rename `rootProject.name` in `settings.gradle.kts`.
2. Rename the Java package `dev.ofatech.hytale.template` to your namespace.
3. Update `src/main/resources/manifest.json` group/name/main.
4. Update `src/main/resources/config.default.json` with your defaults.
5. Update `src/main/resources/messages.default.json` with your messages.
6. Replace TemplatePlugin identifiers (plugin id, name, etc.) used in the API.
7. Remove example features and modules you do not need.
8. Run `./gradlew build` to verify the build.
9. Run `./gradlew devServer` if the task is available in your environment.

Tip: keep changes small and verify frequently to make rollback easy.

