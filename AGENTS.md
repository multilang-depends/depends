# AGENTS Guide

This file documents collaboration conventions for human and LLM agents working on this repository.

## Environment prerequisites

- Use JDK 11+ for Maven build and test.
- Initialize git submodules before development:
  - `git submodule update --init --recursive`
- Install local dependencies before running full builds:
  - Build and install `utils` module: `cd utils && mvn install -DskipTests && cd ..`
  - Install local jars: `sh ./lib_install.sh`

## Branching and commit policy

- Do not develop directly on `master`.
- Use feature branches with focused commits.
- Keep infrastructure/docs commits separate from feature implementation commits when possible.

## Language frontend migration policy

- Keep existing language frontends stable unless explicitly changing them.
- Prefer parallel introduction of new frontend options (for example `java-ts`) before replacement.
- Preserve CLI compatibility and existing analyzer output contracts.

## Testing policy

- Always run targeted tests for changed modules first.
- Before merging, run:
  - `mvn clean test`
  - `mvn clean package`
- If tests fail due to environment mismatch, fix environment first, then retry tests.

## Safety and scope

- Do not remove existing extractor implementations during migration iterations.
- Avoid irreversible git operations unless explicitly requested.
- Do not commit local IDE-only settings unless explicitly requested.
