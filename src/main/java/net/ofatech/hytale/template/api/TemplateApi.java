package net.ofatech.hytale.template.api;

/**
 * Placeholder public API contract for this cloneable template project.
 * <p>
 * When you create a real mod from this template, rename or replace this interface with
 * mod-specific naming, for example {@code ServerCoreApi}, {@code MultiversalTalesApi}, or
 * {@code SuperPlaygroundApi}.
 * <p>
 * This interface is intentionally minimal and does not provide runtime discovery,
 * dependency resolution, or cross-mod registry behavior.
 */
public interface TemplateApi {

    String id();

    String name();

    String version();
}
