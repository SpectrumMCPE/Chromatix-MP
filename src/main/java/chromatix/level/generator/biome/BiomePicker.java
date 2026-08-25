package chromatix.level.generator.biome;

import chromatix.level.generator.biome.result.BiomeResult;
import chromatix.utils.random.NukkitRandom;

public abstract class BiomePicker<E extends BiomeResult> {

    protected final NukkitRandom random;

    protected BiomePicker(NukkitRandom random) {
        this.random = random;
    }

    public abstract E pick(int x, int y, int z);

}
