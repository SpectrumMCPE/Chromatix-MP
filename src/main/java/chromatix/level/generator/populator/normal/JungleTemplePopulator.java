package chromatix.level.generator.populator.normal;

import chromatix.level.Level;
import chromatix.level.biome.BiomeID;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.structures.ObjectJungleTemple;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.level.generator.populator.placement.StructurePlacement;
import chromatix.math.Vector3;

public class JungleTemplePopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_jungle_temple";

    protected static final ObjectJungleTemple JUNGLE_TEMPLE = new ObjectJungleTemple();
    public static final StructurePlacement PLACEMENT = new StructurePlacement(StructurePlacement.PlacementSettings.builder()
            .salt(14357619L)
            .minDistance(8)
            .maxDistance(32)
            .isBiomeValid(biome -> biome == BiomeID.JUNGLE)
            .build());

    @Override
    public void apply(ChunkGenerateContext context) {
        if(!shouldGenerateStructures(context)) return;

        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        int biome = chunk.getBiomeId(7, chunk.getHeightMap(7, 7), 7);
        if (!PLACEMENT.canGenerate(level.getSeed(), random, chunkX, chunkZ, biome)) {
            return;
        }

        random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        int x = (chunkX << 4) + random.nextBoundedInt(15);
        int z = (chunkZ << 4) + random.nextBoundedInt(15);
        int y = level.getHeightMap(x, z);

        BlockManager manager = new BlockManager(level);
        JUNGLE_TEMPLE.generate(manager, random, new Vector3(x, y, z));
        queueObject(chunk, manager);
    }

    @Override
    public String name() {
        return NAME;
    }

}
