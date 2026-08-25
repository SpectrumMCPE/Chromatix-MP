package chromatix.level.updater.item;

import chromatix.level.updater.Updater;
import chromatix.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public class ItemUpdater_1_20_60 implements Updater {
    public static final Updater INSTANCE = new ItemUpdater_1_20_60();

    @Override
    public void registerUpdaters(CompoundTagUpdaterContext ctx) {
        ctx.addUpdater(1, 20, 60)
                .match("Name", "minecraft:scute")
                .edit("Name", helper -> {
                    helper.getRootTag().put("Name", "minecraft:turtle_scute");
                });
    }
}
