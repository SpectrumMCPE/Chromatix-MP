package chromatix.level.updater.block;

import chromatix.block.BlockID;
import chromatix.block.property.CommonBlockProperties;
import chromatix.level.updater.Updater;
import chromatix.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public class BlockStateUpdater_1_21_110 implements Updater {

    public static final Updater INSTANCE = new BlockStateUpdater_1_21_110();
    @Override
    public void registerUpdaters(CompoundTagUpdaterContext ctx) {
        ctx.addUpdater(1, 21, 110)
                .match("name", "minecraft:chain")
                .edit("name", helper -> {
                    helper.replaceWith("name", "minecraft:iron_chain");
                });

        ctx.addUpdater(1, 21, 110)
                .match("name", BlockID.LIGHTNING_ROD)
                .visit("states")
                .tryAdd(CommonBlockProperties.POWERED_BIT.getName(), (byte) 0);

    }
}