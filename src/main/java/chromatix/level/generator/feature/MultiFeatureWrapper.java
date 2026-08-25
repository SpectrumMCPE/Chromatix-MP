package chromatix.level.generator.feature;

import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.registry.Registries;

public abstract class MultiFeatureWrapper extends GenerateFeature {

    protected abstract String[] getFeatures();

    @Override
    public final void apply(ChunkGenerateContext context) {
        for(String name : getFeatures()) {
            GenerateFeature feature = Registries.GENERATE_FEATURE.get(name);
            feature.setRoot(this.root);
            feature.apply(context);
        }
    }
}
