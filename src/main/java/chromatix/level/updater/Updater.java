package chromatix.level.updater;

import chromatix.level.updater.util.tagupdater.CompoundTagUpdaterContext;

public interface Updater {
    void registerUpdaters(CompoundTagUpdaterContext context);
}
