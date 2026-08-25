package chromatix.level.generator.populator.nether;

import chromatix.block.Block;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.populator.Populator;
import chromatix.math.NukkitMath;
import chromatix.math.Vector3;

public class LavaPopulator extends Populator {

    public static final String NAME = "nether_lava";

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        BlockManager object = new BlockManager(level);
        int amount = random.nextBoundedInt(30) - 29;

        for (int i = 0; i < amount; ++i) {
            int x = NukkitMath.randomRange(random, chunkX << 4, (chunkX << 4) + 15);
            int z = NukkitMath.randomRange(random, chunkZ << 4, (chunkZ << 4) + 15);
            int y = this.getHighestWorkableBlock(object, x, z);
            if (y <= 1) continue;
            if (random.nextInt(4) == 1) continue;
            level.setBlock(new Vector3(x, y+1, z), Block.get(Block.LAVA), true, true);
        }
    }


    private int getHighestWorkableBlock(BlockManager level, int x, int z) {
        int y;
        for (y = 127; y >= 0; y--) {
            String b = level.getBlockIdIfCachedOrLoaded(x, y, z);
            if (b == Block.AIR) {
                break;
            }
        }
        return y == 0 ? -1 : y;
    }
    @Override
    public String name() {
        return NAME;
    }
}
