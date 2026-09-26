# Domum Ornamentum: 26.1 to 26.3 build-system port

## Preserved source base and scope

- Repository/branch: Domum-Ornamentum, `port/26.3`.
- Official source: LDTTeam `upstream/port/26.1`, `247f16ae1c18ed8593acbfa35a719883eb198fe4` (`247f16a`).
- Source environment: Minecraft 26.1, NeoForge 26.1.0.2-beta, Java 25, Gradle 9.2.0.
- Recorded manual baseline: `./gradlew.bat build` reached `compileApiJava`, then failed with category C, 10 Java/API errors and 3 access transformer warnings. The baseline checkpoint is COMPLETED; neither reproducing it nor fixing its source errors belongs to this stage.
- Preserve the incomplete official 26.x port exactly: 212 files in `src/main/java` and 20 in `src/api/java`. Record SHA-256 before editing and compare both trees after verification.
- Only build/configuration changes are authorized. No Java/API migration, compatibility stubs, source exclusions, failure suppression, feature removal, commit or push.

## Existing build architecture

This repository already uses **Tableau 0.0.87**, not the remote OperaPublicaCreator `mod.gradle`. The existing `build.gradle` configures Tableau and remains unchanged:

- Project group `com.ldtteam`, publisher `LDTTeam`, mod ID `domum_ornamentum`.
- Separate `api` source set, marked as a mod source and included in the primary jar; `main` depends on `api`.
- Tableau source/API archives, sources/Javadoc facilities and its `universal` primary-jar convention.
- LDTTeam and local Maven publication, Git-derived metadata and the existing `usingGnu3License()` convention.
- Existing Git/CurseForge features, CurseForge project 527361, and local version `1.0.0-local`.
- Java 25, existing AT file, client/server/GameTest runs, configured `clientData` datagen, existing resources and generated data under `src/datagen/generated/domum_ornamentum`.
- `gradle/dependencies.gradle` is still loaded. Its JEI/datagenerators declarations were already commented out in the official source and remain exactly as found. No library was disabled during this stage.
- No `src/test` tree exists in the inspected source. Preserve the build's existing test lifecycle; do not create substitute tests or claim runtime coverage.

## Minimum toolchain change

| Component | Source | Target |
| --- | --- | --- |
| Minecraft | 26.1 | 26.3 |
| Java | 25 | 25; local Oracle JDK 25.0.4 |
| NeoForge | 26.1.0.2-beta | 26.3.0.10-beta |
| NeoGradle | 7.1.20 through Tableau 0.0.87 | 7.1.39 |
| Gradle | 9.2.0 | 9.2.1 |
| Tableau | 0.0.87 | 0.0.87, retained |
| Foojay resolver | 1.0.0 | 1.0.0, retained |

The tagged Tableau source shows that its settings bootstrap loads `com.ldtteam.tableau:Tableau:0.0.87` on a separate buildscript classpath; that release declares NeoGradle 7.1.20. Load the same Tableau core plugin directly in `settings.gradle`, declaring NeoGradle 7.1.39 alongside it on a single classpath. This allows normal dependency conflict resolution to select the requested toolchain without replacing Tableau's source-set, run, packaging or publication facilities.

Update only Minecraft/NeoForge properties and the wrapper, regenerate wrapper files, align legacy loader metadata fields with the 26.3 MDK, and update GitHub workflow Java inputs from 21 to 25. Append wrapper LF/CRLF rules only after observed line-ending warnings; preserve all existing `.gitattributes` content.

## Stages

### 0. Record and inspect (completed)

- Files affected: ignored build evidence and porting documentation only.
- Change: preserve the existing baseline problems report and source hashes; inspect Tableau, local scripts, source sets, metadata, ATs, datagen and workflows.
- Old/new behavior: the 10 known baseline API errors remain untouched; the baseline becomes a comparison point.
- Risks: source errors can be masked by earlier errors on a newer target; disappearance from output is not evidence of a fix.
- Verification: `git rev-parse HEAD upstream/port/26.1`, source SHA-256, inspection of the existing baseline report and official references.

### 1. Update build/configuration (this stage)

- Files affected: `settings.gradle`, `gradle.properties`, wrapper files, mod metadata, workflow Java inputs, wrapper attributes and the two porting documents.
- Change: select the target toolchain while retaining Tableau 0.0.87 and the unchanged root build/dependency script.
- Old behavior: 26.1 dependencies, NeoGradle 7.1.20 and wrapper 9.2.0; workflow inputs request Java 21 despite the Java 25 source target.
- New behavior: target dependencies resolve on Java 25 and the build reaches `compileApiJava` or `compileJava` with unmodified sources.
- Risks: plugin classpath compatibility, changed AT targets and pre-existing plus new Java API failures. Preserve diagnostics without adjusting compiler strictness.
- Verification: `java -version`, `javac -version`, `./gradlew.bat --version`, `./gradlew.bat wrapper javaToolchains`, `./gradlew.bat build`.
- Additional checks: `tasks --all dependencies --configuration apiCompileClasspath`, `generatePomFileForDefaultPublication`, `runClientData --dry-run`, source hash/diff comparison and `git diff --check`.
- Acceptance: SUCCESS or category C. Category A configuration or B dependency failure is not an acceptable endpoint.

### 2. API migration (NOT STARTED; separate authorization required)

- Files affected later: only API/main Java and AT rules demonstrated to need changes.
- Change: first address the shared API source-set failures, then main compilation; preserve existing functionality and the official port as the base.
- Old behavior: known incomplete 26.1 API port, including recipe/serializer, advancement and NBT incompatibilities.
- New behavior: actual 26.3 APIs without stubs or disabled features.
- Risks: fixing API compilation will reveal additional main-source errors not visible at this checkpoint; AT warnings must be migrated with the related implementation.
- Verification: `./gradlew.bat compileApiJava`, `./gradlew.bat compileJava`, then the complete build, with no exclusions.

### 3. Runtime/data/release validation (NOT STARTED)

- Files affected later: narrowly scoped build/resource fixes supported by actual failures.
- Change: test material/model behavior, recipes, game launches and datagen; inspect API/main contents of distribution artifacts and validate CI/publishing.
- Old behavior: Tableau conventions and external OperaPublicaCreator `@ng7` workflows are retained.
- New behavior: verified runtime and release artifacts after a successful source port.
- Risks: a valid POM or dry-run task graph does not validate jar contents, datagen providers, remote workflow compatibility or uploads. Review Tableau's generated Git-derived URLs, local version, packaging and license label before release.
- Verification: full build, client/server/GameTest as applicable, actual `runClientData`, artifact inspection and separately authorized CI/publishing checks.

Recommended order: finish the build boundary, compare baseline/current diagnostics, migrate API then main source only with authorization, validate runtime/datagen, and finally validate publication/CI.

## Workspace safety and references

During verification set `JAVA_HOME=C:\Program Files\Java\jdk-25.0.4`, prepend its `bin` to `PATH`, and check both executables. Stop if Gradle uses another Java major version.

Keep writable cache and temporary output within this repository: `GRADLE_USER_HOME=.gradle/port-user-home`, `TEMP`/`TMP=.gradle/port-tmp` and Java `java.io.tmpdir` at the same absolute path. No system-wide installation, Windows settings change or Administrator command is needed.

- [Official 26.3 NeoGradle MDK](https://github.com/NeoForgeMDKs/MDK-26.3-NeoGradle): target versions, Java and project/metadata conventions.
- [Tableau 0.0.87 version catalog](https://github.com/ldtteam/Tableau/blob/tableau-0.0.87/gradle/libs.versions.toml): original NeoGradle 7.1.20.
- [Tableau 0.0.87 bootstrap](https://github.com/ldtteam/Tableau/blob/tableau-0.0.87/bootstrap/src/main/java/com/ldtteam/tableau/bootstrap/BootstrapSettingsPlugin.java): original classpath/application mechanism.
- [Tableau NeoGradle integration](https://github.com/ldtteam/Tableau/blob/tableau-0.0.87/modules/neogradle/src/main/java/com/ldtteam/tableau/neogradle/NeoGradleProjectPlugin.java): preserved source-set, run and AT behavior.
- [NeoForge migration primers](https://docs.neoforged.net/primer/docs/): later API work, not implemented in this stage.

Final measurements, error comparison and exact changed files are in `PORTING_STATUS.md`.
