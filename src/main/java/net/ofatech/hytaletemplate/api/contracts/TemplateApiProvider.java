package net.ofatech.hytaletemplate.api.contracts;

import net.ofatech.hytaletemplate.api.TemplateApi;

import java.util.Optional;

/**
 * Contract for exposing this plugin's public API to external integrations.
 *
 * This does not perform discovery by itself.
 * It only defines the shape of an object capable of providing the API.
 */
public interface TemplateApiProvider {
    Optional<TemplateApi> getApi();
}
