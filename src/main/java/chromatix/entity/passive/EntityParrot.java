package chromatix.entity.passive;

import chromatix.entity.EntityFilter;
import chromatix.entity.EntityFlyable;
import chromatix.entity.components.HealableComponent;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.components.TameableComponent;
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author PikyCZ
 */
// TODO: All parrot behaviors / intelligence
public class EntityParrot extends EntityAnimal implements EntityFlyable {
    @Override
    @NotNull public String getIdentifier() {
        return PARROT;
    }

    public EntityParrot(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public String getOriginalName() {
        return "Parrot";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("parrot_wild", "mob");
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 1.0f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(6);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.4f);
    }

    @Override
    public @Nullable TameableComponent getComponentTameable() {
        return new TameableComponent(
                0.33f,
                Set.of(
                    ItemID.WHEAT_SEEDS,
                    ItemID.PUMPKIN_SEEDS,
                    ItemID.MELON_SEEDS,
                    ItemID.BEETROOT_SEEDS,
                    ItemID.PITCHER_POD,
                    ItemID.TORCHFLOWER_SEEDS
                )
        );
    }

    @Override
    public HealableComponent getComponentHealable() {
        return new HealableComponent(
                EntityFilter.all(
                        (self, ctx) -> !self.isRiding()
                ),
                true,
                List.of(
                    new HealableComponent.Item(
                            Item.COOKIE,
                            0,
                            List.of(new HealableComponent.Effect("fatal_poison", 1.0f, 1000.0f, 0.0f))
                    )
                )
        );
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        return new Item[]{
                Item.get(Item.FEATHER, 0, Utils.rand(1, 2))
        };
    }
}
