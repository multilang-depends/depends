# Java Tree-sitter Frontend Migration Plan

## Goals

- Keep existing `java` (ANTLR) frontend unchanged and fully available.
- Add a new parallel frontend option `java-ts` for A/B comparison.
- Reuse current semantic model and dependency generation pipeline as much as possible.
- Deliver iteratively with test evidence for each milestone.

## Branch and Delivery Strategy

- Working branch: `feat/java-treesitter-migration`
- Iterative delivery model:
  - Each iteration includes code, tests, and a short result summary.
  - Keep changes isolated and reversible.
  - Do not remove or break existing Java frontend.

## Constraints and Compatibility

- Current build targets Java 8.
- Tree-sitter Java integration must be validated for:
  - Java 8 compatibility
  - Maven dependency resolution
  - Native library loading on macOS (and ideally Linux/Windows)
- If dependency/runtime constraints block progress, pause and request user intervention.

## Iteration Plan

### Iteration 0: Baseline and scaffolding

Scope:
- Confirm current Java parser test baseline is stable.
- Introduce migration docs and traceability files.

Validation:
- Run core Java extractor tests to establish baseline signal.

Exit criteria:
- Baseline test run is recorded.

### Iteration 1: Register a parallel language `java-ts`

Scope:
- Add `JavaTreeSitterProcessor` with language name `java-ts`.
- Register processor in `LangRegister`.
- Add a placeholder `JavaTreeSitterFileParser` wired into extractor pipeline.
- Ensure CLI completion exposes `java-ts`.

Validation:
- Compile project.
- Execute at least one CLI smoke run with `java-ts`.

Exit criteria:
- `depends java-ts <src> <output>` is accepted and runs through pipeline.

### Iteration 2: Minimal semantic extraction (file/package/import/type)

Scope:
- Implement Tree-sitter parse flow for Java source.
- Map AST nodes to existing semantic context for:
  - package
  - import
  - class/interface/enum declarations
  - basic extends/implements

Validation:
- New focused tests for minimal relations.
- Compare with existing `java` output on sample fixtures.

Exit criteria:
- `IMPORT`, `INHERIT`, `IMPLEMENT`, and `CONTAIN` basic behavior works.

### Iteration 3: Methods, fields, and signatures

Scope:
- Add method/constructor/field extraction.
- Capture return types, parameters, and throw types where available.

Validation:
- Add/extend tests against existing Java fixtures.
- Relation checks: `PARAMETER`, `RETURN`, `THROW`.

Exit criteria:
- Method-level structural entities and key relation types are generated.

### Iteration 4: Expression-level dependencies

Scope:
- Add expression mapping for:
  - call
  - create
  - use/set
  - cast

Validation:
- Targeted expression tests.
- Differential comparison against ANTLR frontend on representative examples.

Exit criteria:
- Core expression relations (`CALL`, `CREATE`, `USE`, `SET`, `CAST`) are populated.

### Iteration 5: Robustness and A/B report

Scope:
- Parse error tolerance and edge-case handling.
- Run both `java` and `java-ts` on selected Java projects and compare outputs.

Validation:
- Produce a gap report:
  - supported relation parity
  - known mismatches
  - performance observations

Exit criteria:
- `java-ts` has a clear readiness status and next-step backlog.

## Testing Protocol per Iteration

- Unit tests first for new node mapping logic where feasible.
- Integration tests through parser/extractor tests.
- CLI smoke test for end-to-end verification.
- Run lints/compilation checks after substantive edits.

## Intervention Points (User Action Required)

I will only ask for your intervention when one of these happens:

- Tree-sitter Java dependency cannot be resolved in your environment.
- Native library loading fails and requires machine-level install/config.
- Licensing or dependency policy decision is needed.
- Large behavior mismatch requires choosing trade-offs (strict parity vs. pragmatic fallback).

## Immediate Next Step

Start Iteration 1 now:
- Add `java-ts` processor registration and parser skeleton.
- Add first smoke test coverage and run compile/tests.
