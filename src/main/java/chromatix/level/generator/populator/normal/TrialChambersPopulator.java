package chromatix.level.generator.populator.normal;

import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.structures.StructureHelper;
import chromatix.level.generator.object.structures.jigsaw.trialchambers.TrialChambersStructure;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.level.generator.populator.placement.StructurePlacement;
import chromatix.math.BlockVector3;
import chromatix.utils.random.RandomSourceProvider;
import chromatix.utils.random.Xoroshiro128;

public class TrialChambersPopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_trial_chambers";

    public static final StructurePlacement PLACEMENT = new StructurePlacement(StructurePlacement.PlacementSettings.builder()
            .salt(94251327L)
            .minDistance(12)
            .maxDistance(34)
            .build());

    protected static final TrialChambersStructure TRIAL_CHAMBERS = new TrialChambersStructure();

    @Override
    public void apply(ChunkGenerateContext context) {
        if(!shouldGenerateStructures(context)) return;

        IChunk chunk = context.getChunk();
        if (!chunk.isOverWorld()) {
            return;
        }

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        int biome = chunk.getBiomeId(7, chunk.getHeightMap(7, 7), 7);
        RandomSourceProvider placementRandom = new Xoroshiro128(level.getSeed());
        if (!PLACEMENT.canGenerate(level.getSeed(), placementRandom, chunkX, chunkZ, biome)) {
            return;
        }

        int originX = chunkX << 4;
        int originZ = chunkZ << 4;
        int originY = findGenerationY(level, originX + 7, originZ + 7);
        StructureHelper helper = new StructureHelper(level, new BlockVector3(originX, originY, originZ));
        TRIAL_CHAMBERS.place(helper, random.fork());
    }


    protected int findGenerationY(Level level, int x, int z) {
        int minY = level.getMinHeight() + 8;
        int terrainY = level.getHeightMap(x, z) - 20;
        return Math.max(minY, Math.min(-20, terrainY));
    }

    @Override
    public String name() {
        return NAME;
    }
}
