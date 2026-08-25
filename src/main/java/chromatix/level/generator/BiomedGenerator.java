package chromatix.level.generator;

import chromatix.level.Level;
import chromatix.level.generator.biome.BiomePicker;

public interface BiomedGenerator {

    BiomePicker createBiomePicker(Level level);

}
