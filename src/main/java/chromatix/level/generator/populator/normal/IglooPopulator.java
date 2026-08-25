package chromatix.level.generator.populator.normal;

import chromatix.block.*;
import chromatix.entity.Entity;
import chromatix.entity.data.profession.Profession;
import chromatix.entity.effect.PotionType;
import chromatix.entity.mob.EntityZombieVillagerV2;
import chromatix.entity.passive.EntityVillagerV2;
import chromatix.item.Item;
import chromatix.item.ItemPotion;
import chromatix.item.ItemSplashPotion;
import chromatix.level.Level;
import chromatix.level.Position;
import chromatix.level.biome.BiomeID;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.RandomizableContainer;
import chromatix.level.generator.populator.Populator;
import chromatix.level.generator.populator.PopulatorStructure;
import chromatix.level.generator.populator.placement.StructurePlacement;
import chromatix.level.structure.PNXStructure;
import chromatix.math.BlockVector3;
import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.DoubleTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.ListTag;
import chromatix.registry.Registries;

import java.util.Random;

public class IglooPopulator extends Populator implements PopulatorStructure {

    public static final String NAME = "normal_igloo";

    public static final StructurePlacement PLACEMENT = new StructurePlacement(StructurePlacement.PlacementSettings.builder()
            .salt(14357618L)
            .minDistance(8)
            .maxDistance(32)
            .isBiomeValid(biome -> biome == BiomeID.ICE_PLAINS || biome == BiomeID.COLD_TAIGA || biome == BiomeID.SNOWY_SLOPES)
            .build());

    protected final PNXStructure TOP = (PNXStructure) Registries.STRUCTURE.get("igloo/top");
    protected final PNXStructure MIDDLE = (PNXStructure) Registries.STRUCTURE.get("igloo/middle");
    protected final PNXStructure BOTTOM = (PNXStructure) Registries.STRUCTURE.get("igloo/bottom");

    protected static final ChestPopulator CHEST_POPULATOR = new ChestPopulator();

    @Override
    public void apply(ChunkGenerateContext context) {
        if(!shouldGenerateStructures(context)) return;

        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        int biome = chunk.getBiomeId(3, chunk.getHeightMap(3, 3), 3);
        if (PLACEMENT.canGenerate(level.getSeed(), random, chunkX, chunkZ, biome)) {
            boolean hasLaboratory = random.nextBoolean();

            BlockVector3 size = new BlockVector3(TOP.getSizeX(), TOP.getSizeY(), TOP.getSizeZ());
            int sumY = 0;
            int blockCount = 0;

            for (int x = 0; x < size.getX(); x++) {
                for (int z = 2; z < size.getZ() + 2; z++) {
                    int y = chunk.getHeightMap(x, z);

                    Block block = chunk.getBlockState(x, y, z).toBlock();
                    while (block.canBeReplaced() && y > 64) {
                        block = chunk.getBlockState(x, --y, z).toBlock();
                    }

                    sumY += Math.max(64, y);
                    blockCount++;
                }
            }
            BlockManager object = new BlockManager(level);
            BlockVector3 vec = new BlockVector3(chunkX << 4, sumY / blockCount, (chunkZ << 4) + 2);
            TOP.preparePlace(new Position(vec.x, vec.y, vec.z, level), object);
            if (hasLaboratory) {

                int yOffset = MIDDLE.getSizeY();
                vec.x += 2;
                vec.z += 4;

                for (int i = 0; i < random.nextBoundedInt(8) + 3; ++i) {
                    vec.y -= yOffset;
                    MIDDLE.preparePlace(new Position(vec.x, vec.y, vec.z, level), object);
                }

                vec.x -= 2;
                vec.z -= 6;
                vec.y -= BOTTOM.getSizeY();

                BOTTOM.preparePlace(new Position(vec.x, vec.y, vec.z, level), object);
                object.addHook(() -> {
                    var villager = Entity.createEntity(Entity.VILLAGER_V2, chunk, Entity.getDefaultNBT(vec.asVector3().add(2.5, 1, 1.5)));
                    var zombie = Entity.createEntity(Entity.ZOMBIE_VILLAGER_V2, chunk, Entity.getDefaultNBT(vec.asVector3().add(4.5, 1, 1.5)));
                    villager.spawnToAll();
                    zombie.spawnToAll();
                });
            } else
                object.setBlockStateAt(new BlockVector3(vec.x + 3, vec.y, vec.z + 5), BlockSnow.PROPERTIES.getDefaultState());
            for (Block block : object.getBlocks()) {
                if (block instanceof BlockStructureBlock) object.setBlockStateAt(block, BlockAir.STATE);
                if (block instanceof BlockIce)
                    object.setBlockStateAt(block, BlockPackedIce.PROPERTIES.getDefaultState());
                if (block instanceof BlockBrewingStand stand) {
                    object.addHook(() -> {
                        stand.getOrCreateBlockEntity().getInventory().setResult(2, ItemSplashPotion.get(ItemPotion.SPLASH_POTION, PotionType.WEAKNESS.id()));
                    });
                }
                if (block instanceof BlockChest chest) {
                    object.addHook(() -> {
                        CHEST_POPULATOR.create(chest.getOrCreateBlockEntity().getInventory(), random);
                    });
                }
                if (block instanceof BlockBed bed) {
                    object.addHook(() -> {
                        bed.createBlockEntity(new CompoundTag().putByte("color", 14));
                    });
                }
                if (block instanceof BlockFlowerPot pot) {
                    object.addHook(() -> {
                        pot.setFlower(Item.get(Block.CACTUS));
                    });
                }
            }
            queueObject(chunk, object);
        }
    }

    @Override
    public String name() {
        return NAME;
    }

    protected static class ChestPopulator extends RandomizableContainer {
        public ChestPopulator() {
            PoolBuilder pool1 = new PoolBuilder()
                    .register(new ItemEntry(Item.APPLE, 0, 3, 15))
                    .register(new ItemEntry(Item.COAL, 0, 4, 15))
                    .register(new ItemEntry(Item.GOLD_NUGGET, 0, 3, 10))
                    .register(new ItemEntry(Item.STONE_AXE, 2))
                    .register(new ItemEntry(Item.ROTTEN_FLESH, 10))
                    .register(new ItemEntry(Item.EMERALD, 1))
                    .register(new ItemEntry(Block.WHEAT, 0, 3, 2, 10));
            this.pools.put(pool1.build(), new RollEntry(8, 2, pool1.getTotalWeight()));

            PoolBuilder pool2 = new PoolBuilder()
                    .register(new ItemEntry(Item.GOLDEN_APPLE, 1));
            this.pools.put(pool2.build(), new RollEntry(1, pool2.getTotalWeight()));
        }
    }
}
