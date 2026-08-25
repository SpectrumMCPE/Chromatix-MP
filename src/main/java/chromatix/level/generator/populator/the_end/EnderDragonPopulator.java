package chromatix.level.generator.populator.the_end;

import chromatix.entity.Entity;
import chromatix.entity.mob.EntityEnderDragon;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.populator.Populator;
import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.DoubleTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.ListTag;

import java.util.Arrays;
import java.util.Random;

public class EnderDragonPopulator extends Populator {

    public static final String NAME = "the_end_dragon";


    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        if(chunk.getX() == 1 && chunk.getZ() == 0) { //We check for 1, 0 because 0, 0 gets loaded on level load. The dragon may not spawn then.
            if(Arrays.stream(chunk.getLevel().getEntities()).anyMatch(entity -> entity instanceof EntityEnderDragon)) return;
            CompoundTag nbt = new CompoundTag()
                    .putList("Pos", new ListTag<DoubleTag>()
                            .add(new DoubleTag( 0.5))
                            .add(new DoubleTag(128))
                            .add(new DoubleTag(0.5)))
                    .putList("Motion", new ListTag<DoubleTag>()
                            .add(new DoubleTag(0))
                            .add(new DoubleTag(0))
                            .add(new DoubleTag(0)))
                    .putList("Rotation", new ListTag<FloatTag>()
                            .add(new FloatTag(new Random().nextFloat() * 360))
                            .add(new FloatTag(0)));
            Entity entity = Entity.createEntity(Entity.ENDER_DRAGON, chunk, nbt);
            entity.spawnToAll();
        }
    }

    @Override
    public String name() {
        return NAME;
    }

}