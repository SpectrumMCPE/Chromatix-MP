package chromatix.level.generator.feature.ore;

import chromatix.block.BlockState;

public abstract class AbstractOreUpperGeneratorFeature extends OreGeneratorFeature {

    @Override
    public boolean canBeReplaced(BlockState state) {
        return state == STONE;
    }

}
