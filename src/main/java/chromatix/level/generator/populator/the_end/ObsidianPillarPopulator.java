package chromatix.level.generator.populator.the_end;

import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.ObjectObsidianPillar;
import chromatix.level.generator.populator.Populator;
import chromatix.math.Vector2;
import chromatix.math.Vector3;

public class ObsidianPillarPopulator extends Populator {

    public static final String NAME = "the_end_obsidian_pillar";

    private Vector2[] pillarPos;

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        if(pillarPos == null) {
            pillarPos = new Vector2[10];
            for(int i = 0; i < 10; i++) {
                int x = (int) (42d * Math.cos(2d * (-Math.PI + (Math.PI / 10d) * i)));
                int z = (int) (42d * Math.sin(2d * (-Math.PI + (Math.PI / 10d) * i)));
                pillarPos[i] = new Vector2(x, z);
            }
        }
        for(int i = 0; i < pillarPos.length; i++) {
            Vector2 p = pillarPos[i];
            if(p.getFloorX() >> 4 == chunkX && p.getFloorY() >> 4 == chunkZ) {
                BlockManager object = new BlockManager(level);
                ObjectObsidianPillar pillar = new ObjectObsidianPillar();
                pillar.i = i;
                pillar.generate(object, null, new Vector3(p.x, level.getHeightMap(p.getFloorX(), p.getFloorY()), p.y));
                queueObject(chunk, object);
            }
        }
    }

    @Override
    public String name() {
        return NAME;
    }
}
