package chromatix.level.generator.populator.normal;

import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.structures.StructureHelper;
import chromatix.level.generator.object.structures.jigsaw.trailruins.TrailRuinsStructure;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.level.generator.populator.placement.StructurePlacement;
import chromatix.math.BlockVector3;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import chromatix.utils.random.Xoroshiro128;

/**
 * Trail Ruins for PowerNukkitX
 * @author Buddelbubi
 * @since 2026/03/31
 */
public class TrailRuinsPopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_trail_ruins";

    public static final StructurePlacement PLACEMENT = new StructurePlacement(StructurePlacement.PlacementSettings.builder()
            .salt(83469867L)
            .minDistance(8)
            .maxDistance(34)
            .isBiomeValid(biome -> Registries.BIOME.getTags(biome).contains(BiomeTags.HAS_STRUCTURE_TRAIL_RUINS))
            .build());

    private static final TrailRuinsStructure TRAIL_RUINS = new TrailRuinsStructure();

    @Override
    public void apply(ChunkGenerateContext context) {
        if(!shouldGenerateStructures(context)) return;

        IChunk chunk = context.getChunk();
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
        int originY = findGenerationY(chunk, level);
        StructureHelper helper = new StructureHelper(level, new BlockVector3(originX, originY, originZ));
        TRAIL_RUINS.place(helper, random.fork());
    }

    private int findGenerationY(IChunk chunk, Level level) {
        int worldX = (chunk.getX() << 4) + 7;
        int worldZ = (chunk.getZ() << 4) + 7;
        int y = level.getHeightMap(worldX, worldZ);
        while (y > level.getMinHeight() && level.getBlock(worldX, y, worldZ).canBeReplaced()) {
            y--;
        }
        return Math.max(level.getMinHeight() + 1, y - 15);
    }

    @Override
    public String name() {
        return NAME;
    }
}
