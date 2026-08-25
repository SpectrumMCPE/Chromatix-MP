package chromatix.level.generator.feature;

import chromatix.block.Supportable;
import chromatix.block.Block;
import chromatix.block.BlockSweetBerryBush;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.TreeGenerator;
import chromatix.level.generator.object.BeeNestGenerator;
import chromatix.level.generator.object.legacytree.LegacyBirchTree;
import chromatix.level.generator.object.legacytree.LegacyOakTree;
import chromatix.math.NukkitMath;
import chromatix.math.Vector3;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;

public abstract class LegacyTreeGeneratorFeature extends GenerateFeature implements Supportable {

    public abstract TreeGenerator getGenerator(RandomSourceProvider random);

    public int getMin() {
        return 5;
    }

    public int getMax() {
        return 6;
    }

    public String getRequiredTag() {
        return BiomeTags.OVERWORLD;
    }

    protected float getBeeNestChance() {
        return 0F;
    }

    @Override
    public final void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        this.random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ) ^ name().hashCode());
        int amount = NukkitMath.randomRange(random, getMin(), getMax());
        Vector3 v = new Vector3();
        BlockManager manager = new BlockManager(level);
        for (int i = 0; i < amount; ++i) {
            int x = random.nextInt(15);
            int z = random.nextInt(15);
            int y = chunk.getHeightMap(x, z);
            if (y < level.getMinHeight()) {
                continue;
            }
            BlockManager object = new BlockManager(level);
            v.setComponents(x + (chunkX << 4), y, z + (chunkZ << 4));
            if (!Registries.BIOME.containsTag(getRequiredTag(), level.getBiomeId(v.getFloorX(), v.getFloorY(), v.getFloorZ()))) continue;
            if(isSupportDirt(level.getBlock(v))) {
                TreeGenerator generator = getGenerator(random);
                if(generator == null) return;
                Vector3 treePosition = new Vector3(v.getFloorX(), v.getFloorY() + 1, v.getFloorZ());
                if (generator.generate(object, random, treePosition)
                        && (generator instanceof LegacyOakTree || generator instanceof LegacyBirchTree)
                        && random.nextFloat() < getBeeNestChance()) {
                    BeeNestGenerator.place(object, random, treePosition);
                }
                manager.merge(object);
            }
        }
        queueObject(chunk, manager);
    }
}
