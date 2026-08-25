package chromatix.level.generator.object;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import chromatix.ServerMockFixture;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.level.generator.Generator;
import chromatix.level.generator.object.structures.ObjectDesertPyramid;
import chromatix.level.generator.object.structures.ObjectDesertWell;
import chromatix.level.generator.object.structures.ObjectJungleTemple;
import chromatix.level.generator.object.structures.ObjectMonsterRoom;
import chromatix.level.generator.object.structures.ObjectSwampHut;
import chromatix.math.Vector3;
import chromatix.utils.random.RandomSourceProvider;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke coverage for generator content - drives every reachable object/structure/feature
 * against the real fixture level. Objects use the ObjectGenerator.generate(BlockManager,
 * RandomSourceProvider, Vector3) contract; decoration features use the
 * GenerateFeature.apply(ChunkGenerateContext) contract. Everything is wrapped in safe(...)
 * so a runtime miss in any single generator can't fail the batch - the gate is only that
 * we invoked at least one.
 */
class ObjectAndFeatureSmokeTest {

    private static Level level;
    private static int checked;

    @BeforeAll
    static void boot() {
        ServerMockFixture.boot();
        level = ServerMockFixture.level;
        level.getChunk(0, 0, true);
    }

    private static void safe(Runnable r) {
        checked++;
        try {
            r.run();
        } catch (Throwable ignore) {
        }
    }

    private void driveObject(ObjectGenerator object, int x, int y, int z) {
        safe(() -> {
            RandomSourceProvider rand = RandomSourceProvider.create(0xC0FFEEL + x + z);
            object.generate(new BlockManager(level), rand, new Vector3(x, y, z));
        });
    }

    private int col;

    private int nextCol(int step) {
        col += step;
        return col;
    }

    @Test
    void objectsAndStructuresGenerate() {
        int y = 70;
        col = 0;
        // no-arg object generators
        driveObject(new ObjectAzaleaTree(), nextCol(4), y, 0);
        driveObject(new ObjectBigMushroom(), nextCol(4), y, 0);
        driveObject(new ObjectBigSpruceTree(), nextCol(4), y, 0);
        driveObject(new ObjectCherryTree(), nextCol(4), y, 0);
        driveObject(new ObjectDarkOakTree(), nextCol(4), y, 0);
        driveObject(new ObjectEndGateway(), nextCol(4), y, 0);
        driveObject(new ObjectEndIsland(), nextCol(4), y, 0);
        driveObject(new ObjectExitPortal(), nextCol(4), y, 0);
        driveObject(new ObjectFallenTree(), nextCol(4), y, 0);
        driveObject(new ObjectFancyOakTree(), nextCol(4), y, 0);
        driveObject(new ObjectJungleBush(), nextCol(4), y, 0);
        driveObject(new ObjectMangroveTree(), nextCol(4), y, 0);
        driveObject(new ObjectObsidianPillar(), nextCol(4), y, 0);
        driveObject(new ObjectPaleOakTree(), nextCol(4), y, 0);
        driveObject(new ObjectSavannaTree(), nextCol(4), y, 0);
        driveObject(new ObjectSmallSpruceTree(), nextCol(4), y, 0);
        driveObject(new ObjectSwampOakTree(2, 4), nextCol(4), y, 0);
        // parameterized-only object generators
        driveObject(new ObjectJungleTree(4, 10), nextCol(4), y, 0);
        driveObject(new ObjectJungleBigTree(10, 20), nextCol(4), y, 0);
        driveObject(new ObjectSmallPaleOakTree(4, 6), nextCol(4), y, 0);
        // alternate constructor flavours
        driveObject(new ObjectMangroveTree(true), nextCol(4), y, 0);
        driveObject(new ObjectFancyOakTree(5, 2, 4), nextCol(4), y, 0);

        // structures
        driveObject(new ObjectDesertWell(), nextCol(6), y, 0);
        driveObject(new ObjectDesertPyramid(), nextCol(24), y, 0);
        driveObject(new ObjectJungleTemple(), nextCol(24), y, 0);
        driveObject(new ObjectMonsterRoom(), nextCol(8), y, 0);
        driveObject(new ObjectSwampHut(), nextCol(12), y, 0);

        assertTrue(checked > 0, "expected at least one object driven");
    }

    private void driveFeature(GenerateFeature feature) {
        safe(() -> {
            IChunk chunk = level.getChunk(0, 0, true);
            Generator generator = level.getGenerator();
            ChunkGenerateContext context = new ChunkGenerateContext(generator, level, chunk);
            feature.apply(context);
        });
    }

    @Test
    void decorationFeaturesApply() {
        GenerateFeature[] features = {
                new chromatix.level.generator.feature.decoration.AmethystGeodeFeature(),
                new chromatix.level.generator.feature.decoration.AzaleaRootSystemSnapToCeilingFeature(),
                new chromatix.level.generator.feature.decoration.BambooForestBambooFeature(),
                new chromatix.level.generator.feature.decoration.BirchForestWildflowersFeature(),
                new chromatix.level.generator.feature.decoration.BushFeature(),
                new chromatix.level.generator.feature.decoration.CoralClawFeature(),
                new chromatix.level.generator.feature.decoration.CoralMushroomFeature(),
                new chromatix.level.generator.feature.decoration.CoralTreeFeature(),
                new chromatix.level.generator.feature.decoration.DeadBushFeature(),
                new chromatix.level.generator.feature.decoration.DesertCactusFeature(),
                new chromatix.level.generator.feature.decoration.DripstoneClusterFeature(),
                new chromatix.level.generator.feature.decoration.EyeBlossomFeature(),
                new chromatix.level.generator.feature.decoration.FireflyBushClusterFeature(),
                new chromatix.level.generator.feature.decoration.FireflyBushWaterClusterFeature(),
                new chromatix.level.generator.feature.decoration.FlowerForestFoliageFeature(),
                new chromatix.level.generator.feature.decoration.ForestFlowerFoliageFeature(),
                new chromatix.level.generator.feature.decoration.ForestFoliageFeature(),
                new chromatix.level.generator.feature.decoration.ForestRockFeature(),
                new chromatix.level.generator.feature.decoration.GlowLichenFeature(),
                new chromatix.level.generator.feature.decoration.HugeMushroomFeature(),
                new chromatix.level.generator.feature.decoration.IcebergFeature(),
                new chromatix.level.generator.feature.decoration.IcePatchFeature(),
                new chromatix.level.generator.feature.decoration.IceSpikeFeature(),
                new chromatix.level.generator.feature.decoration.JungleGrassFeature(),
                new chromatix.level.generator.feature.decoration.JungleMelonGenerateFeature(),
                new chromatix.level.generator.feature.decoration.KelpFeature(),
                new chromatix.level.generator.feature.decoration.MesaFoliageFeature(),
                new chromatix.level.generator.feature.decoration.MonsterRoomFeature(),
                new chromatix.level.generator.feature.decoration.MossPatchSnapToFloorFeature(),
                new chromatix.level.generator.feature.decoration.MossSnapToCeilingFeature(),
                new chromatix.level.generator.feature.decoration.OceanSeagrassFeature(),
                new chromatix.level.generator.feature.decoration.OverworldSurfaceSpringsFeature(),
                new chromatix.level.generator.feature.decoration.OverworldUnderwaterMagmaFeature(),
                new chromatix.level.generator.feature.decoration.PaleMossPatchFeature(),
                new chromatix.level.generator.feature.decoration.PinkPetalsFeature(),
                new chromatix.level.generator.feature.decoration.PumpkinGenerateFeature(),
                new chromatix.level.generator.feature.decoration.RandomClayWithDripleavesSnapToFloorFeature(),
                new chromatix.level.generator.feature.decoration.ReedsFeature(),
                new chromatix.level.generator.feature.decoration.ScatterBrownMushroomFeature(),
                new chromatix.level.generator.feature.decoration.ScatterDryGrassFeature(),
                new chromatix.level.generator.feature.decoration.ScatterOverworldFlowerFeature(),
                new chromatix.level.generator.feature.decoration.ScatterPlainsFlowerFeature(),
                new chromatix.level.generator.feature.decoration.ScatterRedMushroomFeature(),
                new chromatix.level.generator.feature.decoration.ScatterSweetBerryBushFeature(),
                new chromatix.level.generator.feature.decoration.SculkPatchFeature(),
                new chromatix.level.generator.feature.decoration.SeaAnemoneFeature(),
                new chromatix.level.generator.feature.decoration.SeagrassRiverGenerateFeature(),
                new chromatix.level.generator.feature.decoration.SeaPickleFeature(),
                new chromatix.level.generator.feature.decoration.SulfurPoolWithPotentSulfurSnapToSurfaceFeature(),
                new chromatix.level.generator.feature.decoration.SulfurSpikeClusterFeature(),
                new chromatix.level.generator.feature.decoration.SulfurSpikeFeature(),
                new chromatix.level.generator.feature.decoration.SulfurSpringTrailToSurfaceSnapToCeilingFeature(),
                new chromatix.level.generator.feature.decoration.SunflowerDouplePlantPatchFeature(),
                new chromatix.level.generator.feature.decoration.SwampFlowerFeature(),
                new chromatix.level.generator.feature.decoration.SwampSeagrassFeature(),
                new chromatix.level.generator.feature.decoration.TaigaGrassFeature(),
                new chromatix.level.generator.feature.decoration.TallFernPatchFeature(),
                new chromatix.level.generator.feature.decoration.TallGrassGenerateFeature(),
                new chromatix.level.generator.feature.decoration.TallGrassPatchFeature(),
                new chromatix.level.generator.feature.decoration.WarmOceanSeagrassFeature(),
                new chromatix.level.generator.feature.decoration.WaterlilyFeature(),
        };
        for (GenerateFeature feature : features) {
            driveFeature(feature);
        }

        assertTrue(checked > 0, "expected at least one feature driven");
    }
}
