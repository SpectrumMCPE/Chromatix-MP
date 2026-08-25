package chromatix.level.generator.feature.ore;

import chromatix.block.BlockDeepslateEmeraldOre;
import chromatix.block.BlockEmeraldOre;
import chromatix.block.BlockID;
import chromatix.block.BlockState;

public class EmeraldOreGenerationFeature extends OreGeneratorFeature {

    private static final BlockState TYPE_STONE = BlockEmeraldOre.PROPERTIES.getDefaultState();
    private static final BlockState TYPE_DEEPSLATE = BlockDeepslateEmeraldOre.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:overworld_underground_emerald_ore_feature";

    @Override
    public BlockState getState(BlockState original) {
        return switch (original.getIdentifier())  {
            case BlockID.STONE -> TYPE_STONE;
            case BlockID.DEEPSLATE -> TYPE_DEEPSLATE;
            default -> original;
        };
    }

    @Override
    public int getClusterCount() {
        return 100;
    }

    @Override
    public int getClusterSize() {
        return 3;
    }

    @Override
    public int getMinHeight() {
        return -16;
    }

    @Override
    public int getMaxHeight() {
        return 420;
    }

    @Override
    public String name() {
        return NAME;
    }
}
