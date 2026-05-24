package net.ofatech.hytaletemplate.api;

/**
 * Public API contract exposed by this plugin.
 *
 * When this template is cloned into a real mod, rename this interface:
 * - ServerCoreApi
 * - MultiversalTalesApi
 * - SuperPlaygroundApi
 *
 * Other mods should depend on this API contract, not on internal classes
 * from core or platform.
 */
public interface TemplateApi {
    String id();

    String name();

    String version();

    boolean isReady();
}
