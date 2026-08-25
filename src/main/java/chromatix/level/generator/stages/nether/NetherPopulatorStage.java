package chromatix.level.generator.stages.nether;

import chromatix.level.generator.populator.generic.PopulatorRuinedPortal;
import chromatix.level.generator.populator.nether.*;
import chromatix.level.generator.populator.nether.basalt_delta.BasaltDeltaLavaPopulator;
import chromatix.level.generator.populator.nether.basalt_delta.BasaltDeltaMagmaPopulator;
import chromatix.level.generator.populator.nether.basalt_delta.BasaltDeltaPillarPopulator;
import chromatix.level.generator.populator.nether.crimson.CrimsonFungiTreePopulator;
import chromatix.level.generator.populator.nether.crimson.CrimsonGrassesPopulator;
import chromatix.level.generator.populator.nether.crimson.CrimsonWeepingVinesPopulator;
import chromatix.level.generator.populator.nether.soulsand_valley.NetherFossilPopulator;
import chromatix.level.generator.populator.nether.warped.WarpedFungiTreePopulator;
import chromatix.level.generator.populator.nether.warped.WarpedGrassesPopulator;
import chromatix.level.generator.populator.nether.warped.WarpedTwistingVinesPopulator;
import chromatix.level.generator.stages.PopulatorStage;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

public class NetherPopulatorStage extends PopulatorStage {

    public static final String NAME = "nether_populator";

    public static final ObjectArraySet<String> POPULATORS = new ObjectArraySet<>(new String[] {
            GlowstonePopulator.NAME,
            SoulsandPopulator.NAME,
            MagmaPopulator.NAME,
            LavaOrePopulator.NAME,
            FirePopulator.NAME,
            LavaPopulator.NAME,
            NetherGoldOrePopulator.NAME,
            AncientDebrisSmallPopulator.NAME,
            AncientDebrisLargePopulator.NAME,
            NetherQuartzPopulator.NAME,
            BasaltDeltaLavaPopulator.NAME,
            BasaltDeltaPillarPopulator.NAME,
            BasaltDeltaMagmaPopulator.NAME,
            CrimsonFungiTreePopulator.NAME,
            CrimsonGrassesPopulator.NAME,
            CrimsonWeepingVinesPopulator.NAME,
            WarpedFungiTreePopulator.NAME,
            WarpedGrassesPopulator.NAME,
            WarpedTwistingVinesPopulator.NAME,
            NetherBlackstonePopulator.NAME,
            NetherGravelPopulator.NAME,
            BastionRemnantPopulator.NAME,
            NetherFortressPopulator.NAME,
            PopulatorRuinedPortal.NAME,
            NetherFossilPopulator.NAME
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
