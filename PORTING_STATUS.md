# Domum Ornamentum porting status

## Source checkpoint

- Current branch: `port/26.3`.
- Source base: official LDTTeam `upstream/port/26.1`.
- Source commit: `247f16a` (`247f16ae1c18ed8593acbfa35a719883eb198fe4`).
- Source Minecraft: 26.1.
- Source NeoForge: 26.1.0.2-beta.
- Source Java: 25.
- Source Gradle: 9.2.0.
- Source build system: Tableau 0.0.87, with NeoGradle 7.1.20.
- Baseline build: **BUILD FAILED at `compileApiJava`**, category C, manually tested before changes.
- Baseline Java/API errors: **10**.
- Baseline access transformer warnings: **3**.
- Baseline checkpoint: **COMPLETED**; its known source failures were not repaired.
- Date of this verification: 2026-09-26.

The existing baseline problems report was preserved as `build/port-baseline-26.1-problems.html` before running the target build. It contains the 10 reported diagnostics, allowing an actual comparison rather than relying only on the total.

## Target and retained build system

- Target Minecraft: **26.3**.
- Target Java: **25**; actual Java and javac **25.0.4**.
- JDK: `C:\Program Files\Java\jdk-25.0.4`.
- Target NeoForge: **26.3.0.10-beta**.
- Target NeoGradle: **7.1.39**.
- Target Gradle: **9.2.1**.
- Tableau: **0.0.87 retained**, not substantially replaced.
- Foojay resolver: **1.0.0 retained**.
- Java/API implementation: **NOT STARTED**.

`settings.gradle` now loads the same Tableau core directly together with NeoGradle 7.1.39 on one buildscript classpath. This replaces only the settings bootstrap's separate classpath mechanism. The local cache contains the resolved `userdev-7.1.39.jar`; the target API dependency report identifies NeoForge 26.3.0.10-beta.

`build.gradle` and `gradle/dependencies.gradle` remain unchanged. The `api` source set remains a mod source and part of the primary jar, and `main` still depends on it. Tableau retains its run, resource, archive and publication facilities. Previously commented JEI/datagenerators declarations remain as found in the official base; no dependency or feature was disabled by this work.

## Exact files changed

Modified tracked files:

1. `settings.gradle` - load retained Tableau 0.0.87 with the selected NeoGradle 7.1.39 dependency.
2. `gradle.properties` - Minecraft 26.1 to 26.3 and NeoForge 26.1.0.2-beta to 26.3.0.10-beta; Java stays 25.
3. `gradle/wrapper/gradle-wrapper.properties` - Gradle 9.2.1 distribution.
4. `gradle/wrapper/gradle-wrapper.jar` - regenerated wrapper.
5. `gradlew` - regenerated wrapper script.
6. `gradlew.bat` - regenerated wrapper script.
7. `src/main/resources/META-INF/neoforge.mods.toml` - remove legacy loader fields in line with the target MDK; retain all other mod metadata and the existing jar-version placeholder.
8. `.github/workflows/build.yaml` - Java input 21 to 25 for build/prerelease.
9. `.github/workflows/release.yml` - Java input 21 to 25 for release.
10. `.gitattributes` - append only wrapper LF/CRLF rules after observed wrapper line-ending warnings; original bytes/rules preserved.

Added files:

11. `PORTING_PLAN.md`.
12. `PORTING_STATUS.md`.

Ignored verification logs, source hash manifests, preserved baseline report and Gradle caches are contained within this repository's `build/` and `.gradle/` directories.

## Source preservation

- `src/main/java`: **UNCHANGED**, 212 Java files.
- `src/api/java`: **UNCHANGED**, 20 Java files.
- SHA-256 comparison: **232 files compared, zero differences**, no additions or removals.
- Recorded initial manifest: `build/port-source-hashes-before.json`.
- Git diff against `247f16a` for both Java trees is empty.
- The root build script, existing dependency script, access transformer file and generated resource tree also remain unchanged from the source base.
- No compiler errors were suppressed, no Java files excluded, no compatibility stubs created and no functionality rewritten.

## Environment and verification commands

Commands were run from `C:\MC26\MineColonies-Port\Domum-Ornamentum` with process-local environment settings:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-25.0.4'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
$env:GRADLE_USER_HOME = Join-Path $PWD '.gradle/port-user-home'
$env:TEMP = Join-Path $PWD '.gradle/port-tmp'
$env:TMP = $env:TEMP
$env:JAVA_TOOL_OPTIONS = "-Djava.io.tmpdir=$env:TEMP"
java -version
javac -version
```

Both executables reported Java 25.0.4. The required `./gradlew.bat --version` reported Gradle 9.2.1, launcher JVM 25.0.4 and daemon JVM at the required JDK path. No system-wide software was installed, Windows settings were not changed and no Administrator command was required. Writable Gradle caches and temporary files were redirected into this repository.

Verification performed:

- `./gradlew.bat wrapper javaToolchains`: Gradle BUILD SUCCESSFUL; wrapper regenerated and JDK 25.0.4 detected. The old batch file reported a trailing `'scope' is not recognized` after replacing itself; subsequent invocations of the regenerated wrapper ran correctly.
- `./gradlew.bat --version`: PASS, also after regeneration.
- `./gradlew.bat build`: full build, no task exclusions or error suppression; see final result below. Console output captured with `*> build/port-26.3-build.log`, preserving the process exit status.
- `./gradlew.bat tasks --all dependencies --configuration apiCompileClasspath`: BUILD SUCCESSFUL; API target dependencies resolved, and API archive, run, test and publication tasks remain registered.
- `./gradlew.bat generatePomFileForDefaultPublication`: BUILD SUCCESSFUL, local POM only.
- `./gradlew.bat runClientData --dry-run`: BUILD SUCCESSFUL; graph includes `compileApiJava`, `compileJava` and `runClientData`. No datagen execution or resource rewriting.
- Source SHA-256 comparison and diff against the upstream revision: PASS.

The first 26.3 build invocation was interrupted before it produced a final result. Its partial log was preserved as `build/port-26.3-interrupted-build.log`; the full build was subsequently rerun with the same configuration. An interrupted attempt is not classified as a successful or failed completed build.

## Final build result and baseline comparison

- Build/configuration stage: **COMPLETED** to the authorized category C stopping point.
- Gradle configuration: **SUCCEEDED**.
- Dependency resolution: **SUCCEEDED** for Minecraft 26.3, NeoForge 26.3.0.10-beta and the API compilation classpath; no dependency-resolution failure remained.
- `compileApiJava` reached: **YES**.
- `compileJava` reached: **NO**; main compilation depends on the failed API compilation.
- Final result: **BUILD FAILED at `:compileApiJava` in 5m 22s**.
- Final category: **C - Java/API compilation errors**.
- Displayed Java/API errors: **13**; no diagnostic truncation. Gradle's repeated failure summary is not counted twice.
- Baseline comparison: **10 identical pre-existing errors plus 3 new displayed errors**. Comparison used source path, unchanged line number and full diagnostic message from both Gradle problems reports; all 10 baseline errors remain present.

| Error group | Location relative to `src/api/java/com/ldtteam/domumornamentum` | Count |
| --- | --- | ---: |
| Pre-existing recipe trigger import/use, recipe-output/resource-key mismatches | `recipe/architectscutter/ArchitectsCutterRecipeBuilder.java`: 11, 86, 91, 92, 96 | 5 |
| Pre-existing recipe override | `recipe/architectscutter/ArchitectsCutterRecipe.java`: 156 | 1 |
| Pre-existing serializer type/overrides | `recipe/architectscutter/ArchitectsCutterRecipeSerializer.java`: 8, 11, 17 | 3 |
| Pre-existing NBT `getAllKeys()` | `client/model/data/MaterialTextureData.java`: 104 | 1 |
| New: unresolved `net.minecraft.advancements.Criterion` import, field type and parameter type | `recipe/architectscutter/ArchitectsCutterRecipeBuilder.java`: 10, 32, 72 | 3 |
| **Total** | | **13** |

The three new diagnostics concern the same target API type; they are not three independently repaired features. Neither old nor new errors were fixed. Further main-source errors may become visible only after a separately authorized API migration.

Evidence: `build/port-26.3-build.log`, `build/reports/problems/problems-report.html`, the preserved 26.1 report, `build/port-26.3-config-check.log`, `build/port-26.3-pom-check.log` and `build/port-26.3-datagen-check.log`. Final source hashes are also recorded in `build/port-source-hashes-after.json`.

## Access transformers

The 26.3 transformation emitted **4 unmatched-target warnings**, compared with 3 in the 26.1 baseline:

| AT rule | 26.1 | 26.3 |
| --- | --- | --- |
| `BakedQuad` field wildcard (line 1) | Warning | Warning |
| `BakedQuad.hasAmbientOcclusion` (line 2) | Warning | Warning |
| `RecipeProvider.getName()` (line 3) | Warning | Warning |
| `LootTableProvider.getName()` (line 4) | No recorded warning | New warning |

The AT file remains active and byte-for-byte unchanged. The warnings are visible in the first target transformation log; the resumed build can reuse transformed outputs without repeating them. No strictness setting was changed to hide these warnings.

## Remaining unverified features

- **Runtime: UNVERIFIED**. No client/server/GameTest launch or functionality test was performed.
- **Datagen execution: UNVERIFIED**. The existing clientData configuration and generated data are preserved; only its task graph was checked.
- **Tests: UNVERIFIED**. No `src/test` tree exists in this checkout; the lifecycle remains present, but there is no passing test/runtime claim.
- **CI: UNVERIFIED**. GitHub Actions still delegate to OperaPublicaCreator reusable workflows `@ng7`; their Java inputs now request 25. Local Tableau compatibility does not prove compatibility of those external workflows.
- **Publishing/release: UNVERIFIED and not production-ready**. No upload, remote publish or release workflow was run. Full API/main jar contents and Javadoc completion remain unverified.
- The generated POM retains Tableau's current conventions: local version `1.0.0-local`, Git-derived fork URLs, `pom` packaging and the `GNU Lesser General Public License v3.0` label produced by the unchanged `usingGnu3License()` configuration. The mod metadata still declares `GPL3`. These publication details require review before release; they were not silently rewritten in this build-only stage.

Next stage requires separate authorization: address the API errors first, then compile main sources, review changed AT targets, and only then validate runtime/datagen and CI/publishing.

Suggested manual backup / checkpoint: `Domum-Ornamentum-26.3-build-system-C`.

No commit or push was performed.
