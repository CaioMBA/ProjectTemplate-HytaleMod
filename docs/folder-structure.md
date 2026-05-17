# Folder Structure

```text
src/main/java/net/ofatech/hytale/template/
├── TemplatePlugin.java
├── api/
│   ├── TemplateApi.java
│   ├── contracts/
│   └── integration/
├── core/
│   ├── domain/
│   │   ├── entities/
│   │   ├── enums/
│   │   ├── events/
│   │   ├── exceptions/
│   │   ├── extensions/
│   │   ├── interfaces/
│   │   ├── mappings/
│   │   ├── models/
│   │   ├── valueobjects/
│   │   └── utils/
│   ├── service/
│   │   ├── algorithms/
│   │   ├── commandhandling/
│   │   ├── conversions/
│   │   ├── eventhandling/
│   │   ├── scheduling/
│   │   └── validation/
│   └── infrastructure/
│       ├── crosscutting/
│       │   ├── dependencyinjection/
│       │   ├── healthchecks/
│       │   ├── logging/
│       │   └── middlewares/
│       └── data/
│           ├── api/
│           ├── database/
│           ├── filesystem/
│           ├── io/
│           └── serialization/
└── platform/
    ├── adapter/
    ├── command/
    ├── entity/
    ├── event/
    ├── lifecycle/
    ├── registry/
    └── screen/
```

## What goes where

- `api`: public contracts exposed to other plugins (for your cloned project).
- `core/domain`: entities, value objects, domain events, enums.
- `core/service`: business services, algorithms, validations, orchestration.
- `core/infrastructure`: file/database/API access and technical implementations.
- `platform`: Hytale API interactions such as commands and event handlers.

## Example content for real mods

- `core/service/algorithms`: reward scoring algorithms.
- `core/service/validation`: config schema and business rule validators.
- `core/infrastructure/data/filesystem`: JSON/YAML file readers/writers.
- `core/infrastructure/data/api`: external REST client wrappers.
- `platform/command`: command executors with Hytale command types.
- `platform/event`: handlers for Hytale event classes.
