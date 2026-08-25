package chromatix.level.updater.item;

import chromatix.block.BlockID;
import chromatix.block.property.CommonBlockProperties;
import chromatix.level.updater.Updater;
import chromatix.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public class ItemUpdater_1_21_110 implements Updater {

    public static final Updater INSTANCE = new ItemUpdater_1_21_110();

    @Override
    public void registerUpdaters(CompoundTagUpdaterContext context) {
        context.addUpdater(1, 21, 110, false, false)
                .match("Name", BlockID.LIGHTNING_ROD)
                .visit("Block")
                .visit("states")
                .tryAdd(CommonBlockProperties.POWERED_BIT.getName(), (byte) 0);

        context.addUpdater(1, 21, 110)
                .match("Name", "minecraft:chain")
                .edit("Name", helper -> helper.replaceWith("Name", "minecraft:iron_chain"));

    }
}
