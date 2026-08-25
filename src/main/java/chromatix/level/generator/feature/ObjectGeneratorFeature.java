package chromatix.level.generator.feature;

import chromatix.block.Block;
import chromatix.block.BlockLiquid;
import chromatix.block.Supportable;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.math.NukkitMath;
import chromatix.math.Vector3;
import chromatix.registry.Registries;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

import static chromatix.level.generator.stages.normal.NormalTerrainStage.SEA_LEVEL;

public abstract class ObjectGeneratorFeature extends GenerateFeature implements Supportable {

    public abstract ObjectGenerator getGenerator(RandomSourceProvider random);

    public int getMin() {
        return 5;
    }

    public int getMax() {
        return 6;
    }

    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return true;
    }

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        this.random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ)+ name().hashCode());
        int amount = NukkitMath.randomRange(random, getMin(), getMax());
        Vector3 v = new Vector3();
        BlockManager object = new BlockManager(level);
        for (int i = 0; i < amount; ++i) {
            int x = random.nextInt(15);
            int z = random.nextInt(15);
            int y = chunk.getHeightMap(x, z);
            if (y < level.getMinHeight()) {
                continue;
            }
            v.setComponents(x + (chunkX << 4), y, z + (chunkZ << 4));
            if(!canSpawnHere(Registries.BIOME.get(level.getBiomeId(v.getFloorX(), v.getFloorY(), v.getFloorZ())).second())) continue;
            while(checkBlock(level.getBlock(v))) {
                v.y--;
            }
            if(isSupportDirt(level.getBlock(v))) {
                getGenerator(random).generate(object, random, v.add(0, 1, 0));
            }
        }

        queueObject(chunk, object);
    }

    protected boolean checkBlock(Block bl) {
        return (bl.canBeReplaced() || !bl.isFullBlock()) && !(bl instanceof BlockLiquid) && bl.getY() > SEA_LEVEL;
    }
}
