package chromatix.item;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.block.BlockID;
import chromatix.entity.effect.Effect;
import chromatix.entity.effect.EffectType;

public class ItemPoisonousPotato extends ItemPotato {

    public ItemPoisonousPotato() {
        this(0, 1);
    }

    public ItemPoisonousPotato(Integer meta) {
        this(meta, 1);
    }

    public ItemPoisonousPotato(Integer meta, int count) {
        super(POISONOUS_POTATO, meta, count, "Poisonous Potato");
        this.block = Block.get(BlockID.POTATOES);
    }

    @Override
    public boolean onEaten(Player player) {
        if (0.6F >= Math.random()) {
            player.addEffect(Effect.get(EffectType.POISON).setDuration(80));
        }
        return super.onEaten(player);
    }

    @Override
    public int getNutrition() {
        return 2;
    }

    @Override
    public float getSaturation() {
        return 1.2F;
    }
}