package chromatix.level.generator.populator.normal;

import chromatix.level.Level;
import chromatix.level.Location;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.structures.ObjectDesertWell;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.math.Vector3;

public class DesertWellPopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_desert_well";

    protected static final ObjectDesertWell WELL = new ObjectDesertWell();

    @Override
    public void apply(ChunkGenerateContext context) {
        if(!shouldGenerateStructures(context)) return;

        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        int x = (chunkX << 4) + random.nextBoundedInt(15);
        int z = (chunkZ << 4) + random.nextBoundedInt(15);
        int y = level.getHeightMap(x, z);

        if(WELL.canGenerateAt(new Location(x, y, z, level))) {
            BlockManager manager = new BlockManager(level);
            WELL.generate(manager, null, new Vector3(x, y, z));
            queueObject(chunk, manager);
        }
    }

    @Override
    public String name() {
        return NAME;
    }

}
