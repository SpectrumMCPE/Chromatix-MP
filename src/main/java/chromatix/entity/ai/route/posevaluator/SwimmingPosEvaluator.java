package chromatix.entity.ai.route.posevaluator;

import chromatix.block.Block;
import chromatix.entity.EntityIntelligent;
import chromatix.level.Position;
import chromatix.math.AxisAlignedBB;
import chromatix.math.SimpleAxisAlignedBB;
import chromatix.math.Vector3;
import chromatix.utils.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * Position evaluator for swimming entities
 */


public class SwimmingPosEvaluator implements IPosEvaluator {
    @Override
    public boolean evalPos(@NotNull EntityIntelligent entity, @NotNull Vector3 pos) {
        String blockId = entity.level.getTickCachedBlock(Position.fromObject(pos, entity.level)).getId();
        return isPassable(entity, pos) && (blockId == Block.FLOWING_WATER || blockId == Block.WATER);
    }

    @Override
    public boolean evalStandingBlock(@NotNull EntityIntelligent entity, @NotNull Block block) {
        return true;
    }

    /**
     * Determines whether the entity can occupy the given position without colliding.
     * Specially optimized for movement through open space.
     */
    protected boolean isPassable(EntityIntelligent entity, Vector3 vector3) {
        double radius = (entity.getWidth() * entity.getScale()) * 0.5 + 0.1;
        float height = entity.getHeight() * entity.getScale();
        // In vanilla, entities don't fly hugging the ground
        AxisAlignedBB bb = new SimpleAxisAlignedBB(vector3.getX() - radius, vector3.getY() - height * 0.5, vector3.getZ() - radius, vector3.getX() + radius, vector3.getY() + height, vector3.getZ() + radius);
        return !Utils.hasCollisionTickCachedBlocks(entity.level, bb);
    }
}
