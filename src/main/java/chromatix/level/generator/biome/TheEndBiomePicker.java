package chromatix.level.generator.biome;

import chromatix.level.generator.biome.result.TheEndBiomeResult;
import chromatix.utils.random.NukkitRandom;

public class TheEndBiomePicker extends BiomePicker<TheEndBiomeResult> {

    private final static TheEndBiomeResult RESULT = new TheEndBiomeResult();

    public TheEndBiomePicker() {
        super(new NukkitRandom());
    }

    @Override
    public TheEndBiomeResult pick(int x, int y, int z) {
        return RESULT;
    }
}
