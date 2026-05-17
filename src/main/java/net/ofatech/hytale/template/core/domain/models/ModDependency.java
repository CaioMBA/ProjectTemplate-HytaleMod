package net.ofatech.hytale.template.core.domain.models;

import net.ofatech.hytale.template.core.domain.enums.DependencyType;

public record ModDependency (
    String modId,
    DependencyType type,
    String displayName,
    String minimumVersion
){}
