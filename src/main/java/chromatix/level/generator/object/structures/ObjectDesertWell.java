package chromatix.level.generator.object.structures;

import chromatix.block.BlockSandstone;
import chromatix.block.BlockSandstoneSlab;
import chromatix.block.BlockState;
import chromatix.block.BlockWater;
import chromatix.level.Level;
import chromatix.level.Location;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.RuledObjectGenerator;
import chromatix.math.Vector3;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;
import chromatix.utils.random.RandomSourceProvider;
import chromatix.utils.random.Xoroshiro128;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class ObjectDesertWell extends ObjectGenerator implements RuledObjectGenerator {

    protected static final BlockState SANDSTONE = BlockSandstone.PROPERTIES.getDefaultState();
    protected static final BlockState WATER = BlockWater.PROPERTIES.getDefaultState();
    protected static final BlockState SANDSTONE_SLAB = BlockSandstoneSlab.PROPERTIES.getDefaultState();
    protected final Xoroshiro128 random = new Xoroshiro128();


    @Override
    public boolean generate(BlockManager level, RandomSourceProvider rand, Vector3 position) {
        int x = position.getFloorX();
        int y = position.getFloorY();
        int z = position.getFloorZ();
        for (int dy = -1; dy <= 0; ++dy) {
            for (int dx = -2; dx <= 2; ++dx) {
                for (int dz = -2; dz <= 2; ++dz) {
                    level.setBlockStateAt(x + dx, y + dy, z + dz, SANDSTONE);
                }
            }
        }
        level.setBlockStateAt(x, y, z, WATER);
        level.setBlockStateAt(x - 1, y, z, WATER);
        level.setBlockStateAt(x + 1, y, z, WATER);
        level.setBlockStateAt(x, y, z - 1, WATER);
        level.setBlockStateAt(x, y, z + 1, WATER);
        for (int dx = -2; dx <= 2; ++dx) {
            for (int dz = -2; dz <= 2; ++dz) {
                if (dx == -2 || dx == 2 || dz == -2 || dz == 2) {
                    level.setBlockStateAt(x + dx, y + 1, z + dz, SANDSTONE);
                }
            }
        }
        level.setBlockStateAt(x + 2, y + 1, z, SANDSTONE_SLAB);
        level.setBlockStateAt(x - 2, y + 1, z, SANDSTONE_SLAB);
        level.setBlockStateAt(x, y + 1, z + 2, SANDSTONE_SLAB);
        level.setBlockStateAt(x, y + 1, z - 2, SANDSTONE_SLAB);
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dz = -1; dz <= 1; ++dz) {
                if (dx == 0 && dz == 0) {
                    level.setBlockStateAt(x + dx, y + 4, z + dz, SANDSTONE);
                } else {
                    level.setBlockStateAt(x + dx, y + 4, z + dz, SANDSTONE_SLAB);
                }
            }
        }
        for (int dy = 1; dy <= 3; ++dy) {
            level.setBlockStateAt(x - 1, y + dy, z - 1, SANDSTONE);
            level.setBlockStateAt(x - 1, y + dy, z + 1, SANDSTONE);
            level.setBlockStateAt(x + 1, y + dy, z - 1, SANDSTONE);
            level.setBlockStateAt(x + 1, y + dy, z + 1, SANDSTONE);
        }
        return true;
    }

    @Override
    public String getName() {
        return "desert_well";
    }

    @Override
    public boolean canGenerateAt(Location location) {
        int x = location.getFloorX();
        int y = location.getFloorY();
        int z = location.getFloorZ();
        Level level = location.getLevel();
        random.setSeed(level.getSeed() ^ (x + y + z));

        int biome = level.getBiomeId(x, y, z);
        BiomeDefinitionData definition = Registries.BIOME.get(biome).second();
        if (!Registries.BIOME.containsTag(BiomeTags.DESERT, definition) || random.nextBoundedInt(500) != 0) {
            return false;
        }

        if (y > 128) {
            return false;
        }

        if (!level.getBlock(x, y, z).hasTag(BlockTags.SAND)) {
            return false;
        }

        for (int dx = -2; dx <= 2; ++dx) {
            for (int dz = -2; dz <= 2; ++dz) {
                if (level.getBlock(x + dx, y - 1, z + dz).isAir() && level.getBlock(x + dx, y - 2, z + dz).isAir()) {
                    return false;
                }
            }
        }
        return true;
    }
}
