package chromatix.level.generator.object;

import chromatix.level.Location;

@Deprecated(forRemoval = true)
public interface RuledObjectGenerator {

    String getName();

    boolean canGenerateAt(Location location);
}
