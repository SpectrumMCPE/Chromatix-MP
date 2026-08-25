package chromatix.level.updater.block;

import chromatix.level.updater.Updater;
import chromatix.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public class BlockStateUpdater_1_17_30 implements Updater {

    public static final Updater INSTANCE = new BlockStateUpdater_1_17_30();

    @Override
    public void registerUpdaters(CompoundTagUpdaterContext context) {
        this.updateItemFrame("minecraft:frame", context);
        this.updateItemFrame("minecraft:glow_frame", context);
    }

    private void updateItemFrame(String name, CompoundTagUpdaterContext context) {
        context.addUpdater(1, 16, 210, true) // Palette version wasn't bumped so far
                .match("name", name)
                .visit("states")
                .tryAdd("item_frame_photo_bit", (byte) 0);
    }
}
