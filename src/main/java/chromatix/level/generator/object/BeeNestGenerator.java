package chromatix.level.generator.object;

import chromatix.block.BlockBeeNest;
import chromatix.block.BlockBeehive;
import chromatix.block.BlockFlower;
import chromatix.block.BlockFloweringAzalea;
import chromatix.block.property.CommonBlockProperties;
import chromatix.blockentity.BlockEntityBeehive;
import chromatix.entity.Entity;
import chromatix.entity.EntityID;
import chromatix.math.BlockFace;
import chromatix.math.BlockVector3;
import chromatix.math.Vector3;
import chromatix.utils.random.RandomSourceProvider;

public final class BeeNestGenerator {
    private BeeNestGenerator() {
    }

    public static boolean place(BlockManager level, RandomSourceProvider random, Vector3 treePosition) {
        return place(level, treePosition, random.nextInt(2, 4));
    }

    public static boolean place(BlockManager level, Vector3 treePosition, int beeCount) {
        int x = treePosition.getFloorX();
        int minY = treePosition.getFloorY();
        int z = treePosition.getFloorZ();
        for (int leafY = minY + 1; leafY <= minY + 32; leafY++) {
            BlockVector3 nestPosition = new BlockVector3(x, leafY - 1, z + 1);
            if (level.getBlockIfCachedOrLoaded(x, leafY - 1, z).isAir()
                    || !level.getBlockIfCachedOrLoaded(nestPosition.asVector3()).isAir()
                    || level.getBlockIfCachedOrLoaded(nestPosition.up().asVector3()).isAir()) {
                continue;
            }
            placeAt(level, nestPosition, beeCount);
            return true;
        }
        return false;
    }

    public static void placeAt(BlockManager level, BlockVector3 nestPosition, int beeCount) {
        level.setBlockStateAt(nestPosition, BlockBeeNest.PROPERTIES.getBlockState(
                CommonBlockProperties.DIRECTION.createValue(BlockFace.SOUTH.getHorizontalIndex()),
                CommonBlockProperties.HONEY_LEVEL.createValue(0)
        ));
        level.addHook(() -> populate(level, nestPosition, beeCount));
    }

    public static boolean hasNearbyFlower(BlockManager manager, Vector3 position) {
        int centerX = position.getFloorX();
        int y = position.getFloorY();
        int centerZ = position.getFloorZ();
        for (int x = centerX - 2; x <= centerX + 2; x++) {
            for (int z = centerZ - 2; z <= centerZ + 2; z++) {
                if (x == centerX && z == centerZ) {
                    continue;
                }
                if (manager.getBlockIfCachedOrLoaded(x, y, z) instanceof BlockFlower
                        || manager.getBlockIfCachedOrLoaded(x, y, z) instanceof BlockFloweringAzalea) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void populate(BlockManager manager, BlockVector3 position, int beeCount) {
        if (!(manager.getLevel().getBlock(position.x, position.y, position.z) instanceof BlockBeehive hive)) {
            return;
        }
        BlockEntityBeehive blockEntity = hive.getOrCreateBlockEntity();
        if (blockEntity == null) {
            return;
        }

        for (int i = 0; i < beeCount; i++) {
            Entity bee = Entity.createEntity(EntityID.BEE,
                    manager.getLevel().getChunk(position.x >> 4, position.z >> 4),
                    Entity.getDefaultNBT(new Vector3(position.x + 0.5, position.y, position.z + 0.5)));
            if (bee != null) {
                blockEntity.addOccupant(bee, 600, false, false);
            }
        }
        blockEntity.saveNBT();
    }
}
