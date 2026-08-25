package chromatix.entity.ai.sensor;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.inventory.BarrelInventory;
import chromatix.inventory.ChestBoatInventory;
import chromatix.inventory.ChestInventory;
import chromatix.inventory.DoubleChestInventory;
import chromatix.inventory.HumanEnderChestInventory;
import chromatix.inventory.Inventory;
import chromatix.inventory.MinecartChestInventory;
import chromatix.inventory.ShulkerBoxInventory;
import lombok.Getter;

//Memory that stores the nearest player


@Getter
public class NearestPlayerAngryPiglinSensor implements ISensor {

    public NearestPlayerAngryPiglinSensor() {
    }

    @Override
    public void sense(EntityIntelligent entity) {
        for(Player player : entity.getViewers().values()) {
            if(player.distance(entity) < 32) {
                boolean trigger = false;
                if(player.getTopWindow().isPresent()) {
                    if(checkInventory(player.getTopWindow().get())) {
                        trigger = true;
                    }
                }
                if(player.isBreakingBlock()) {
                    if(checkBlock(player.breakingBlock)) {
                        trigger = true;
                    }
                }
                if(trigger) {
                    entity.getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, player);
                }
            }
        }
    }

    @Override
    public int getPeriod() {
        return 1;
    }

    private boolean checkInventory(Inventory inventory) {
        return inventory instanceof ChestInventory ||
                inventory instanceof DoubleChestInventory ||
                inventory instanceof HumanEnderChestInventory ||
                inventory instanceof ShulkerBoxInventory ||
                inventory instanceof BarrelInventory ||
                inventory instanceof MinecartChestInventory ||
                inventory instanceof ChestBoatInventory;
    }

    private boolean checkBlock(Block block) {
        return switch (block.getId()) {
            case Block.GOLD_BLOCK,
                 Block.GOLD_ORE,
                 Block.GILDED_BLACKSTONE,
                 Block.NETHER_GOLD_ORE,
                 Block.RAW_GOLD_BLOCK,
                 Block.DEEPSLATE_GOLD_ORE -> true;
            default -> false;
        };
    }

}
