package chromatix.level.generator.stages.end;

import chromatix.level.generator.populator.the_end.ChorusFlowerPopulator;
import chromatix.level.generator.populator.the_end.EndCityPopulator;
import chromatix.level.generator.populator.the_end.EndGatewayPopulator;
import chromatix.level.generator.populator.the_end.EndIslandPopulator;
import chromatix.level.generator.populator.the_end.EnderDragonPopulator;
import chromatix.level.generator.populator.the_end.ExitPortalPopulator;
import chromatix.level.generator.populator.the_end.ObsidianPillarPopulator;
import chromatix.level.generator.stages.PopulatorStage;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

public class TheEndPopulatorStage extends PopulatorStage {

    public static final String NAME = "the_end_populator";

    public static final ObjectArraySet<String> POPULATORS = new ObjectArraySet<>(new String[] {
            ObsidianPillarPopulator.NAME,
            EnderDragonPopulator.NAME,
            ExitPortalPopulator.NAME,
            ChorusFlowerPopulator.NAME,
            EndCityPopulator.NAME,
            EndGatewayPopulator.NAME,
            EndIslandPopulator.NAME
    });

    @Override
    public ObjectArraySet<String> populators() {
        return POPULATORS;
    }

    @Override
    public String name() {
        return NAME;
    }
}
