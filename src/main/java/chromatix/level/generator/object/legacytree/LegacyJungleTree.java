package chromatix.level.generator.object.legacytree;

import chromatix.block.property.enums.WoodType;
import chromatix.level.generator.object.BlockManager;
import chromatix.utils.random.RandomSourceProvider;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class LegacyJungleTree extends LegacyTreeGenerator {
    @Override
    public WoodType getType() {
        return WoodType.JUNGLE;
    }

    @Override
    public void placeObject(BlockManager level, int x, int y, int z, RandomSourceProvider random) {
        this.treeHeight = random.nextInt(6) + 4;
        super.placeObject(level, x, y, z, random);
    }
}
