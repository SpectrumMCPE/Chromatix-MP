package chromatix.level.generator.material;

import chromatix.block.BlockState;
import chromatix.level.generator.densityfunction.DensityFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface MaterialFiller {
    @Nullable
    BlockState calculate(DensityFunction.FunctionContext context);
}
