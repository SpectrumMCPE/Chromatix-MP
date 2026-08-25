package chromatix.level.generator.populator.normal;

import chromatix.level.Level;
import chromatix.level.biome.BiomeID;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.structures.StructureHelper;
import chromatix.level.generator.object.structures.jigsaw.ancientcity.AncientCityStructure;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.level.generator.populator.placement.StructurePlacement;
import chromatix.math.BlockVector3;
import chromatix.utils.random.RandomSourceProvider;
import chromatix.utils.random.Xoroshiro128;

public class AncientCityPopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_ancient_city";
    private static final int GENERATION_Y = -51;

    public static final StructurePlacement PLACEMENT = new StructurePlacement(StructurePlacement.PlacementSettings.builder()
            .salt(0x616E6369656E744CL)
            .minDistance(8)
            .maxDistance(24)
            .biomeSampleY(GENERATION_Y)
            .isBiomeValid(biome -> biome == BiomeID.DEEP_DARK)
            .build());

    protected static final AncientCityStructure ANCIENT_CITY = new AncientCityStructure();

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
        int biome = chunk.getBiomeId(7, GENERATION_Y, 7);
        RandomSourceProvider placementRandom = new Xoroshiro128(level.getSeed());
        if (!PLACEMENT.canGenerate(level.getSeed(), placementRandom, chunkX, chunkZ, biome)) {
            return;
        }

        int originX = chunkX << 4;
        int originZ = chunkZ << 4;
        StructureHelper helper = new StructureHelper(level, new BlockVector3(originX, GENERATION_Y, originZ));
        ANCIENT_CITY.place(helper, random.fork());
        queueObject(chunk, helper);
    }

    @Override
    public String name() {
        return NAME;
    }
}
