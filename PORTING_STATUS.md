# Domum Ornamentum porting status

## Current batch: RenderType package migration

- Starting checkpoint: c8acad6 on port/26.3; initial Git working tree was clean.
- Only Java file changed: src/main/java/com/ldtteam/domumornamentum/client/model/baked/SpecificRenderTypeBakedModelWrapper.java.
- Old package/type: net.minecraft.client.renderer.RenderType.
- Verified Minecraft 26.3 package/type: net.minecraft.client.renderer.rendertype.RenderType, confirmed in the local patched Minecraft 26.3 RenderType.java source.
- Exact source change: replaced the single import on line 5. No fields, methods, comparisons, filtering behavior, geometry, ambient occlusion, model architecture or other imports were changed.
- Targeted previously displayed RenderType diagnostics: **6**. All six disappeared: **YES**.
- No new diagnostics appeared in this wrapper in the available output. Its other previously displayed diagnostics remain unchanged.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Project remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining total is unknown.
- Wrapper remaining displayed diagnostics: **14**, down from 20 — BakedModel 5, BakedQuad 3, ChunkRenderTypeSet 2, ItemOverrides 2, ItemTransforms 2.
- Removing the six targeted diagnostics exposed five further pre-existing errors in MateriallyTexturedGeometry and one in MateriallyTexturedModelLoader. Neither class was modified; unrelated errors were not fixed.
- Evidence: build/rendertype-package-compile.log, compared with build/wrapper-analysis-compile.log.
- Next recommended micro-batch: separately authorize only the ItemTransforms import migration in this wrapper, from net.minecraft.client.renderer.block.model.ItemTransforms to net.minecraft.client.resources.model.cuboid.ItemTransforms; target its two missing-type diagnostics while leaving getTransforms(), applyTransform() and model architecture unchanged.
- Outside this wrapper, only PORTING_STATUS.md changed. No unrelated source, API, build, resource, access-transformer, workflow or metadata changes. No commit or push.

## Previous batch: client BlockAndTintGetter package migration

- Starting checkpoint: bb8322d on port/26.3; initial Git working tree was clean.
- Only Java file changed: src/main/java/com/ldtteam/domumornamentum/client/model/baked/SpecificRenderTypeBakedModelWrapper.java.
- Old type/import: net.minecraft.world.level.BlockAndTintGetter.
- Verified Minecraft 26.3 replacement: net.minecraft.client.renderer.block.BlockAndTintGetter. Inspected its actual patched Minecraft source (client-only interface extending BlockAndLightGetter); javap on NeoForge 26.3.0.10-beta confirms BlockStateModelExtension.createGeometryKey, collectParts, particleMaterial and materialFlags accept this client type.
- The old BakedModel getModelData(...) override is not a current BlockStateModelExtension hook. This batch resolves only its parameter type; complete model-interface compatibility remains a later migration.
- Exact source change: replaced that single import. No method signatures/bodies, ModelData logic, ambient occlusion, rendering behavior, geometry or other imports were changed. BlockAndLightGetter was not substituted.
- Targeted BlockAndTintGetter diagnostics disappeared: **YES**, both previously displayed diagnostics (import and getModelData parameter) were removed. No new diagnostic appeared in this wrapper in the available compiler output.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining error total is unknown.
- Remaining displayed diagnostics in SpecificRenderTypeBakedModelWrapper: **20** — RenderType 6, BakedModel 5, BakedQuad 3, ChunkRenderTypeSet 2, ItemOverrides 2, ItemTransforms 2. All are unchanged pre-existing diagnostics.
- Two additional pre-existing IUnbakedGeometry diagnostics became visible in unchanged MateriallyTexturedGeometry. No unrelated errors were fixed.
- Evidence: build/client-blockandtintgetter-compile.log, compared with build/models-modeldata-compile.log.
- Next recommended small batch: separately authorize fluid-overlay type/import migration to BlockAndLightGetter in FramedLightBlock.java, preserving its method body and excluding client model/color code.
- Outside this wrapper, only PORTING_STATUS.md changed. No other sources, API, build files, resources, access transformers, workflows or metadata changed. No commit or push.

## Previous batch: ModelData package migration

- Starting checkpoint: 323c7d7 on port/26.3; initial Git working tree was clean.
- Scope: three rendering-model imports only. Exact Java files changed:
  - src/main/java/com/ldtteam/domumornamentum/client/model/baked/MateriallyTexturedBakedModel.java
  - src/main/java/com/ldtteam/domumornamentum/client/model/baked/RetexturedBakedModelBuilder.java
  - src/main/java/com/ldtteam/domumornamentum/client/model/baked/SpecificRenderTypeBakedModelWrapper.java
- Old type/import: net.neoforged.neoforge.client.model.data.ModelData.
- Verified NeoForge 26.3 replacement: net.neoforged.neoforge.model.data.ModelData. javap on the resolved neoforge-26.3.0.10-beta-universal.jar confirms the class, EMPTY, builder(), get(), Builder.with()/build(), and IBlockEntityExtension.getModelData() returning this type.
- Exact changes: replace that import in each of the three files. No method signatures/bodies, model integration, ambient occlusion, geometry, rendering behavior or other imports were changed.
- Previously displayed ModelData diagnostics targeted: **9** — MateriallyTexturedBakedModel 5, RetexturedBakedModelBuilder 1, SpecificRenderTypeBakedModelWrapper 3.
- All targeted ModelData package/type diagnostics disappeared: **YES**. No new ModelData-related diagnostic was displayed. Source excerpts for other missing types can still contain the name ModelData.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining error total is unknown.
- Remaining displayed diagnostics in MateriallyTexturedBakedModel: **36** — RenderType 15, BakedModel 10, ChunkRenderTypeSet 4, BakedQuad 3, ItemOverrides 2, ItemTransforms 2.
- Remaining displayed diagnostics in RetexturedBakedModelBuilder: **22** — BakedModel 11, RenderType 5, BakedQuad 4, IQuadTransformer 1, SimpleBakedModel 1.
- Remaining displayed diagnostics in SpecificRenderTypeBakedModelWrapper: **22** — RenderType 6, BakedModel 5, BakedQuad 3, BlockAndTintGetter 2, ChunkRenderTypeSet 2, ItemOverrides 2, ItemTransforms 2.
- Removing the nine targeted diagnostics exposed four further non-ModelData diagnostics in SpecificRenderTypeBakedModelWrapper and five in unchanged MateriallyTexturedGeometry. Unrelated existing source errors were not repaired. This capped build does not establish full model/rendering compatibility.
- Evidence: build/models-modeldata-compile.log, compared with build/tristate-package-compile.log.
- Next recommended small batch: separately authorize the verified fluid-overlay type/import migration to BlockAndLightGetter in FramedLightBlock.java, preserving its method body and excluding client model/color code.
- Outside these three Java files, only PORTING_STATUS.md changed. No other sources, API, build files, resources, access transformers, workflows or metadata changed. No commit or push.

## Previous batch: TriState package migration

- Starting checkpoint: 5864dc1 on port/26.3; initial Git working tree was clean.
- Only Java file changed: src/main/java/com/ldtteam/domumornamentum/client/model/baked/SpecificRenderTypeBakedModelWrapper.java. The requested client/model path without baked does not exist; this is the existing wrapper location.
- Old type/import: net.neoforged.neoforge.common.util.TriState.
- Verified Minecraft 26.3 replacement: net.minecraft.util.TriState. Inspected the local patched Minecraft enum source; javap on the resolved NeoForge 26.3.0.10-beta universal jar additionally confirms BlockStateModelPartExtension.ambientOcclusion() returns this type.
- Exact source change: replaced the single import. No method, signature, useAmbientOcclusion body, rendering behavior, model architecture or other import was changed.
- Original TriState diagnostic disappeared: **YES**. No new TriState-related diagnostic was displayed. Occurrences of TriState in source excerpts for other missing types are not TriState diagnostics.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining total is unknown.
- Remaining displayed diagnostics in this wrapper: **21** — RenderType 5; BakedModel 4; BakedQuad 3; ModelData 3 (including its missing package import); ItemOverrides 2; ItemTransforms 2; BlockAndTintGetter 1; ChunkRenderTypeSet 1.
- Comparison with the previous output: one TriState import diagnostic removed, one additional pre-existing ItemOverrides diagnostic exposed at getOverrides(). Unrelated source problems remain untouched; ambient occlusion compatibility is not established by this import-only batch.
- Evidence: build/tristate-package-compile.log, compared with build/block-world-access-compile.log.
- Next recommended small batch: separately authorize fluid-overlay type/import migration to BlockAndLightGetter in FramedLightBlock.java, preserving its method body and excluding model/color classes.
- Outside this wrapper, only PORTING_STATUS.md changed. No unrelated source, API, build, resource, access-transformer, workflow or metadata changes. No commit or push.

## Previous batch: block world-access interface migration

- Starting checkpoint: b351925 on port/26.3; initial Git working tree was clean.
- Exactly two Java files changed:
  - src/main/java/com/ldtteam/domumornamentum/block/decorative/DynamicTimberFrameBlock.java
  - src/main/java/com/ldtteam/domumornamentum/block/decorative/TimberFrameBlock.java
- Old override in both files: public boolean shouldDisplayFluidOverlay(final BlockState state, final BlockAndTintGetter level, final BlockPos pos, final FluidState fluidState), using the obsolete net.minecraft.world.level.BlockAndTintGetter type.
- Verified actual NeoForge 26.3.0.10-beta API with javap on the resolved universal jar: IBlockExtension.shouldDisplayFluidOverlay(BlockState, net.minecraft.world.level.BlockAndLightGetter, BlockPos, FluidState) returns boolean. IBlockStateExtension.shouldDisplayFluidOverlay(net.minecraft.world.level.BlockAndLightGetter, BlockPos, FluidState) also returns boolean.
- New override in both files: public boolean shouldDisplayFluidOverlay(final BlockState state, final BlockAndLightGetter level, final BlockPos pos, final FluidState fluidState).
- TimberFrameBlock's explicit import changed to net.minecraft.world.level.BlockAndLightGetter. DynamicTimberFrameBlock uses its existing net.minecraft.world.level.* import.
- Both method bodies remain exactly return true; fluid-overlay behavior and all placement/state logic were preserved. No global or client-renderer type replacement was performed.
- Targeted previously displayed diagnostics: **3**. All three disappeared: **YES**.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed compiler errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining error total is unknown.
- Remaining displayed diagnostics in the two changed files: **0**; no new diagnostics appeared in either file in the available output. This capped build does not establish complete source compatibility.
- Three additional pre-existing model diagnostics became visible in unchanged SpecificRenderTypeBakedModelWrapper: BakedQuad, ModelData and RenderType. Unrelated errors were not fixed.
- Evidence: build/block-world-access-compile.log, compared with build/modeldata-package-compile.log.
- Recommended next small batch: separately authorize the same verified fluid-overlay type/import migration in FramedLightBlock.java, preserving its method body and excluding client model/color code.
- Outside these two Java files, only PORTING_STATUS.md changed. No other sources, API, build files, resources, access transformers, metadata or workflows were modified. No commit or push.

## Previous batch: ModelData package migration

- Starting checkpoint: 024c51f on port/26.3; initial Git working tree was clean.
- Only Java file changed: src/main/java/com/ldtteam/domumornamentum/entity/block/DynamicTimberFrameBlockEntity.java.
- Exact previous source state: unresolved simple type ModelData in getModelData(), with no ModelData import in this file. The legacy API package used elsewhere in the old rendering code is net.neoforged.neoforge.client.model.data.ModelData; no such import was replaced in this particular file.
- Verified NeoForge 26.3 replacement: net.neoforged.neoforge.model.data.ModelData. javap on the resolved neoforge-26.3.0.10-beta-universal.jar confirms IBlockEntityExtension.getModelData() returns this type and ModelData exposes builder().
- Source change: added import net.neoforged.neoforge.model.data.ModelData; only. getModelData() logic, texture cache refresh, model property assignment and all behavior remain unchanged.
- Original ModelData diagnostic disappeared: **YES**.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 8s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full remaining error total is unknown.
- Remaining displayed diagnostics in DynamicTimberFrameBlockEntity.java: **0**. No new diagnostics in this class appear in the available output; the capped build does not establish complete main-source compatibility.
- Diagnostic comparison: the original ModelData error was removed; one additional pre-existing BakedQuad error became visible in unchanged SpecificRenderTypeBakedModelWrapper. Unrelated errors were not fixed.
- Evidence: build/modeldata-package-compile.log, compared with build/component-input-compile.log.
- Next recommended small batch: separately authorize fluid-overlay signatures using BlockAndLightGetter in DynamicTimberFrameBlock, FramedLightBlock and TimberFrameBlock; preserve overlay behavior and leave rendering/model classes outside that batch.
- Outside this Java file, only PORTING_STATUS.md changed. No other sources, API, build files, resources, access transformers, workflows or metadata were modified. No commit or push.

## Previous batch: block-entity component input signature

- Starting checkpoint: b2b1961 on port/26.3; initial Git working tree was clean.
- Only Java file changed: src/main/java/com/ldtteam/domumornamentum/entity/block/DynamicTimberFrameBlockEntity.java. The requested block/entities path does not exist; this is the existing class location.
- Old signature: protected void applyImplicitComponents(final BlockEntity.DataComponentInput componentInput).
- Actual Minecraft 26.3 BlockEntity signature: protected void applyImplicitComponents(DataComponentGetter components), importing net.minecraft.core.component.DataComponentGetter. Verified in the local NeoForge-patched 26.3 sources, BlockEntity.java line 337.
- Migrated override: protected void applyImplicitComponents(final DataComponentGetter componentInput). Added only the directly required import; no method-call changes were needed.
- Method body preserved: super call, TEXTURE_DATA lookup with MaterialTextureData.EMPTY, random-material fallback for empty texture data, and updateTextureDataWith invocation remain unchanged.
- Original component-input diagnostic disappeared: **YES**. No DataComponentInput, DataComponentGetter or applyImplicitComponents diagnostics in the full compiler output.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification: ./gradlew.bat compileJava — **BUILD FAILED in 2s**, exit code 1; compileApiJava UP-TO-DATE.
- Remaining displayed compiler errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full error total is unknown.
- Remaining displayed diagnostics in this file: **1**, the pre-existing unresolved ModelData return type in getModelData() at line 585 (previously line 584). No new displayed diagnostics in this file.
- Removing the component-input diagnostic exposed one further RenderType diagnostic in unchanged SpecificRenderTypeBakedModelWrapper. No unrelated errors were fixed.
- Evidence: build/component-input-compile.log; comparison with build/direction-property-compile.log accounts for the import's line-number shift.
- Next recommended small batch: separately authorize the fluid-overlay signature migration to BlockAndLightGetter in DynamicTimberFrameBlock, FramedLightBlock and TimberFrameBlock, preserving overlay behavior and leaving model/color code outside that batch.
- Outside the single Java file, only PORTING_STATUS.md changed. No API, build, resource, access-transformer, workflow, wrapper or metadata changes. No commit or push.

## Previous batch: DirectionProperty -> EnumProperty<Direction>

- Starting checkpoint: ace767e on port/26.3; initial Git working tree was clean.
- Batch scope: type/import migration only in these **4 Java files**:
  - src/main/java/com/ldtteam/domumornamentum/block/DOStairBlock.java
  - src/main/java/com/ldtteam/domumornamentum/block/ArchitectsCutterBlock.java
  - src/main/java/com/ldtteam/domumornamentum/block/decorative/DynamicTimberFrameBlock.java
  - src/main/java/com/ldtteam/domumornamentum/block/decorative/TimberFrameBlock.java
- Changed each FACING declaration to EnumProperty<Direction>. Replaced the three explicit DirectionProperty imports with EnumProperty; DOStairBlock already imports the properties package. Added the required Direction import only in TimberFrameBlock.
- Existing property objects preserved: HorizontalDirectionalBlock.FACING in DOStairBlock and ArchitectsCutterBlock; BlockStateProperties.FACING in DynamicTimberFrameBlock and TimberFrameBlock. No behavior, constructors, states or serialization changed.
- Previously visible diagnostics targeted: **7**.
- All DirectionProperty diagnostics disappeared: **YES**; zero occurrences in the complete compiler log.
- Verified java -version and javac -version: **25.0.4** before Gradle.
- Verification command: ./gradlew.bat compileJava.
- Result: **BUILD FAILED in 2s**, exit code 1, at compileJava; compileApiJava remains UP-TO-DATE.
- Remaining displayed errors: **100**, plus 20 warnings. javac's diagnostic limit remains 100; the full error total is unknown.
- Newly displayed diagnostics: seven existing rendering/model errors in unchanged SpecificRenderTypeBakedModelWrapper, exposed after removing the seven DirectionProperty diagnostics.
- No new displayed errors in the four migrated files. DOStairBlock and ArchitectsCutterBlock have no displayed errors; DynamicTimberFrameBlock retains one and TimberFrameBlock retains two pre-existing BlockAndTintGetter errors. Diagnostic comparison accounts for the added import line.
- Log: build/direction-property-compile.log. Unrelated compilation errors were not fixed.
- Next recommended batch from the previous analysis: migrate the block-entity component input signature from BlockEntity.DataComponentInput to DataComponentGetter in DynamicTimberFrameBlockEntity, as a separately authorized small batch.
- Outside the four Java files, only PORTING_STATUS.md was changed. API sources, other main sources, build files, resources and access transformers were preserved. No commit or push.

## Previous checkpoint: API source porting completed

- Date: **2026-09-26**.
- Branch: **port/26.3**.
- Starting checkpoint: **3bea189**, with a clean Git working tree.
- Scope: **API source porting only**; the committed build/configuration migration was preserved.
- Minecraft: **26.3**; NeoForge: **26.3.0.10-beta**.
- Java/javac: **25.0.4**; Gradle: **9.2.1**; NeoGradle: **7.1.39**.
- compileApiJava error count before: **13** (10 inherited from the official 26.1 baseline and 3 additional Criterion diagnostics).
- compileApiJava error count after: **0**. All 13 reported API errors are resolved.
- Command: ./gradlew.bat compileApiJava — **BUILD SUCCESSFUL in 9s**, exit code 0.
- Command: ./gradlew.bat build — **BUILD FAILED in 7s**, exit code 1.
- Full build reached :compileJava: **YES**, with :compileApiJava UP-TO-DATE.
- Final category: **MAIN-C** — the API compiles; main source compilation still requires porting.
- Remaining main compilation errors: **100 displayed** (the default diagnostic limit); the full total is not established. All 100 displayed compiler errors originate in src/main/java. Repeated diagnostics in the Gradle failure summary and Javadoc are not counted as additional Java compilation errors.
- Main compilation also reported **20 warnings**.
- The full build additionally failed at :javadoc on unresolved main-source APIs (100 displayed Javadoc errors).
- :apiJavadoc completed with one existing unresolved @see warning in MaterialTextureData: BlockEntity#saveToItem(ItemStack, HolderLookup.Provider). This non-blocking documentation reference was left outside the compilation-only scope.
- Work stopped after the full build exposed main-source failures. No main API migration was attempted.

## Exact files changed in this API stage

All paths below are relative to this repository.

1. src/api/java/com/ldtteam/domumornamentum/recipe/architectscutter/ArchitectsCutterRecipeBuilder.java
   - Move Criterion and RecipeUnlockedTrigger imports to net.minecraft.advancements.triggers.
   - Create a ResourceKey<Recipe<?>> in Registries.RECIPE for output and advancement rewards.
   - Resolve the recipe holder through RecipeOutput.lookup(Registries.RECIPE).getOrThrow(recipeKey) for the unlock trigger, following the actual 26.3 RecipeUnlockAdvancementBuilder.
   - Preserve Identifier-based public save entry points, advancement identifiers, OR requirements, supplied criteria, and the no-criteria branch that emits no advancement.
2. src/api/java/com/ldtteam/domumornamentum/recipe/architectscutter/ArchitectsCutterRecipeSerializer.java
   - Replace the invalid implementation of the now-final RecipeSerializer record with a factory creating the native record.
   - Reuse the exact existing persistent and network codecs; no placeholder serializer or codec changes.
3. src/api/java/com/ldtteam/domumornamentum/recipe/ModRecipeSerializers.java
   - Register through that factory, retaining the serializer ID and the same native serializer/codecs.
4. src/api/java/com/ldtteam/domumornamentum/recipe/architectscutter/ArchitectsCutterRecipe.java
   - Retain getResultItem(HolderLookup.Provider) and its implementation as a Domum preview helper used by existing cutter UI/recipe integration callers.
   - Remove its obsolete @Override, since the actual 26.3 Recipe interface has no such method, and document its role. Assembly and matching behavior remain unchanged.
5. src/api/java/com/ldtteam/domumornamentum/client/model/data/MaterialTextureData.java
   - Migrate legacy NBT reading to CompoundTag.keySet() and getStringOr(key, "").
   - Retrieve the block value with BuiltInRegistries.BLOCK.getValue, rather than the new Optional holder-returning get.
   - Preserve the NBT string mapping format, empty-tag behavior and defaulted block registry lookup.
6. PORTING_STATUS.md — this status update; the only changed project file outside src/api/java.

No project files were added. Ignored build outputs/logs and hash evidence were generated under build/; Gradle cache and temporary files remain under .gradle/.

## API-stage verification and preservation

Actual signatures were inspected in the NeoForge-patched Minecraft 26.3 sources under build/neoForm/neoFormJoined26.3-1/steps/transformSource/transformed, including Recipe, RecipeSerializer, RecipeOutput, RecipeUnlockAdvancementBuilder, RecipeUnlockedTrigger, Criterion, CompoundTag, Registry and DefaultedRegistry.

Before each Gradle invocation, java -version and javac -version both reported **25.0.4**, using C:\Program Files\Java\jdk-25.0.4. The process-local environment is documented in the historical section below. No build options or compiler settings were changed to suppress errors.

Evidence:

- Initial Git status: build/api-stage-git-before.txt — clean.
- Initial tracked/nonignored file hashes: build/api-stage-files-before.json.
- Main source SHA-256 manifests: build/api-stage-main-before.json and build/api-stage-main-after.json.
- API compilation log: build/api-stage-compile.log.
- Full build log: build/api-stage-full-build.log.
- **212 main Java files compared: zero content differences, additions or removals.**
- Build files, dependency declarations, wrapper, workflows, mod metadata, publishing configuration, access transformers and resources are unchanged in this stage.
- git diff --check: **PASS**.
- No compatibility stubs, unsafe casts, error suppression, source exclusions or disabled functionality were introduced.

## Outstanding validation and next stage

- **Four access transformer warnings remain unresolved**: BakedQuad field wildcard, BakedQuad.hasAmbientOcclusion, RecipeProvider.getName() and LootTableProvider.getName(). They were recorded during the previous 26.3 transformation; this stage reused its up-to-date outputs. The active AT file was not modified.
- **Runtime, datagen execution and tests remain UNVERIFIED**. Compilation does not establish runtime behavior or generated recipe/advancement correctness.
- **CI and publishing/release remain UNVERIFIED** and must not be considered production-ready.
- The next recommended stage is separately authorized src/main/java migration. Current diagnostics include block properties, renderer/model/color APIs and block-entity APIs; none were repaired here.
- After main compilation is repaired, manually validate cutter crafting and previews, recipe unlocking/datagen, serializer synchronization and legacy material NBT loading.
- Suggested manual backup / checkpoint: **Domum-Ornamentum-26.3-api-compile-success-MAIN-C**.
- No commit or push was performed.

The remaining sections retain the earlier build/configuration checkpoint as history. Their 13-error API result and unchanged API-source statements describe the state before this API stage.

## Historical build/configuration checkpoint (before API source porting)

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
- Java/API implementation at the build/configuration checkpoint: **NOT STARTED**. The subsequent API-only stage is recorded above.

`settings.gradle` now loads the same Tableau core directly together with NeoGradle 7.1.39 on one buildscript classpath. This replaces only the settings bootstrap's separate classpath mechanism. The local cache contains the resolved `userdev-7.1.39.jar`; the target API dependency report identifies NeoForge 26.3.0.10-beta.

`build.gradle` and `gradle/dependencies.gradle` remain unchanged. The `api` source set remains a mod source and part of the primary jar, and `main` still depends on it. Tableau retains its run, resource, archive and publication facilities. Previously commented JEI/datagenerators declarations remain as found in the official base; no dependency or feature was disabled by this work.

## Files changed in the earlier build/configuration stage

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

## Source preservation in the earlier build/configuration stage

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

## Earlier build/configuration result and baseline comparison

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

The subsequent API source stage has now been completed as recorded at the top of this document. Further work requires authorization to port main sources and review AT targets, followed by runtime/datagen and CI/publishing validation.

Suggested manual backup / checkpoint: `Domum-Ornamentum-26.3-build-system-C`.

No commit or push was performed.
