package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.item.ItemTool;
import chromatix.item.enchantment.Enchantment;
import chromatix.utils.random.NukkitRandom;
import org.jetbrains.annotations.NotNull;

public class BlockAmethystCluster extends BlockAmethystBud {
    public static final BlockProperties PROPERTIES = new BlockProperties(AMETHYST_CLUSTER, CommonBlockProperties.MINECRAFT_BLOCK_FACE);

    public BlockAmethystCluster() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockAmethystCluster(BlockState blockState) {
        super(blockState);
    }

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    private static final NukkitRandom RANDOM = new NukkitRandom();

    @Override
    protected String getNamePrefix() {
        return "Cluster";
    }

    @Override
    public int getLightLevel() {
        return 5;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    private static final int[][] FORTUNE_DROPS = {
            {4},
            {8, 4, 4},
            {12, 8, 4, 4},
            {16, 12, 8, 4, 4}
    };

    @Override
    public Item[] getDrops(Item item) {
        if (!item.isPickaxe()) {
            return new Item[]{Item.get(ItemID.AMETHYST_SHARD, 0, 2)};
        }

        int fortuneLevel = item.getEnchantmentLevel(Enchantment.ID_FORTUNE_DIGGING);
        fortuneLevel = Math.max(0, Math.min(3, fortuneLevel));

        int[] choices = FORTUNE_DROPS[fortuneLevel];
        int amount = choices[RANDOM.nextBoundedInt(choices.length - 1)];

        return new Item[]{Item.get(ItemID.AMETHYST_SHARD, 0, amount)};
    }
}
