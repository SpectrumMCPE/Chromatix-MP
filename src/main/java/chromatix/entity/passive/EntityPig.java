package chromatix.entity.passive;

import chromatix.Player;
import chromatix.block.BlockID;
import chromatix.entity.ClimateVariant;
import chromatix.entity.EntityID;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.FluctuateController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.ProbabilityEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.evaluator.RiderItemControllableEvaluator;
import chromatix.entity.ai.executor.AnimalGrowExecutor;
import chromatix.entity.ai.executor.BreedingExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.FollowRiderExecutor;
import chromatix.entity.ai.executor.LookAtTargetExecutor;
import chromatix.entity.ai.executor.LoveTimeoutExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.TemptExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.AgeableComponent;
import chromatix.entity.components.BoostableComponent;
import chromatix.entity.components.BreedableComponent;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.components.RideableComponent;
import chromatix.entity.data.property.EntityProperty;
import chromatix.entity.data.property.EnumEntityProperty;
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.math.Vector3;
import chromatix.math.Vector3f;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author BeYkeRYkt (Nukkit Project)
 */
public class EntityPig extends EntityAnimal implements EntityWalkable, ClimateVariant {
    private static final String[] CLIMATE_VARIANTS = {
        "temperate",
        "warm",
        "cold"
    };

    private static final String[] SOUND_VARIANTS = {
        "default",
        "big",
        "mini"
    };

    public static final EntityProperty[] PROPERTIES = new EntityProperty[]{
        new EnumEntityProperty("minecraft:climate_variant", CLIMATE_VARIANTS, "temperate", true),
        new EnumEntityProperty("minecraft:sound_variant", SOUND_VARIANTS, "default", true)
    };

    public EntityPig(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    @NotNull public String getIdentifier() {
        return PIG;
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public boolean isRideable() {
        return !this.isBaby();
    }

    @Override
    public boolean requireSaddleToMount() {
        return true;
    }

    @Override
    public @Nullable RideableComponent getComponentRideable() {
        boolean crounchingSkipInteract = this.isSaddled();
        Set<String> riders = crounchingSkipInteract ? Set.of("player") : Set.of("baby_zombie", "baby_husk");
        float yOffset = crounchingSkipInteract ? 0.63f : 0.7f;
        String interectText = crounchingSkipInteract ? "action.interact.ride.horse" : "";

        return new RideableComponent(
                0,
                crounchingSkipInteract,
                RideableComponent.DismountMode.DEFAULT,
                riders,
                interectText,
                0.0f,
                false,
                false,
                1,
                List.of(new RideableComponent.Seat(
                        0,
                        1,
                        new Vector3f(0.0f, yOffset, 0.0f),
                        null,
                        null,
                        null,
                        null
                ))
        );
    }

    @Override
    public boolean canBeSaddled() {
        return true;
    }

    @Override
    public String getItemControllable() {
        return Item.CARROT_ON_A_STICK;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(10);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.25f);
    }

    @Override
    public String getOriginalName() {
        return "Pig";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("pig", "mob");
    }

    @Override
    public String[] getSoundVariants() {
        return SOUND_VARIANTS;
    }

    @Override
    public @Nullable BreedableComponent getComponentBreedable() {
        return new BreedableComponent(
                Set.of(
                    ItemID.CARROT,
                    ItemID.POTATO,
                    BlockID.BEETROOT
                ),
                List.of(
                    new BreedableComponent.BreedsWith(EntityID.PIG, EntityID.PIG)
                ),
                false
        );
    }

    @Override
    public AgeableComponent getComponentAgeable() {
        return new AgeableComponent(
                null,
                1200f,
                List.of(
                    new AgeableComponent.FeedItem(ItemID.CARROT),
                    new AgeableComponent.FeedItem(ItemID.POTATO),
                    new AgeableComponent.FeedItem(BlockID.BEETROOT)
                ),
                null,
                null,
                null
        );
    }

    @Override
    public @Nullable BoostableComponent getComponentBoostable() {
        return new BoostableComponent(
            1.35f,
            3.0f,
            List.of(
                    new BoostableComponent.BoostItem(
                        ItemID.CARROT_ON_A_STICK,
                        2,
                        ItemID.FISHING_ROD
                    )
            )
        );
    }

    @Override
    public void initEntity() {
        super.initEntity();

        if(nbt.contains("variant")) {
            setVariant(Variant.get(getNbt().getString("variant")));
        } else setVariant(getBiomeVariant(getLevel().getBiomeId((int) x, (int) y, (int) z)));

        this.initSoundVariantProperty();
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        List<Item> drops = new ArrayList<>();

        int amount = Utils.rand(1, 3 + looting);
        Item porkchop = Item.get(this.isOnFire() ? Item.COOKED_PORKCHOP : Item.PORKCHOP, 0, amount);

        drops.add(porkchop);

        if (isSaddled()) {
            drops.add(Item.get(Item.SADDLE, 0, 1));
        }

        return drops.toArray(Item.EMPTY_ARRAY);
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        boolean superResult = super.onInteract(player, item, clickedPos);
        if (superResult) return true;

        if (this.isBaby()) return false;

        if (!item.isNull()) {
            if (item.getId() == Item.SADDLE && !this.isSaddled()) {
                if (!player.isCreative()) player.getInventory().decreaseCount(player.getInventory().getHeldItemIndex());
                getLevel().addLevelSoundEvent(this, SoundEvent.SADDLE, -1, getIdentifier(), false, false);
                setSaddle(true);
                return true;

            } else if (item.getId() == Item.SHEARS && this.isSaddled()) {
                Item saddleItem = Item.get(Item.SADDLE, 0, 1);
                if (player.getInventory().canAddItem(saddleItem)) {
                    player.getInventory().addItem(saddleItem);
                } else {
                    this.getLevel().dropItem(clickedPos, saddleItem);
                }
                setSaddle(false);
                return false;
            }
        }

        if (isSaddled()) mountEntity(player, true);
        return false;
    }

    private static final Set<String> TEMPT_ITEMS = Set.of(
        ItemID.POTATO,
        ItemID.CARROT,
        ItemID.CARROT_ON_A_STICK,
        BlockID.BEETROOT
    );

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                    new Behavior(
                        new LoveTimeoutExecutor(20 * 30),
                            e -> e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                        3, 1
                    ),
                    new Behavior(
                        new AnimalGrowExecutor(),
                            all(
                                e -> e.isAgeable(),
                                e -> e.isBaby(),
                                e -> !e.isGrowthPaused(),
                                e -> e.getTicksGrowLeft() > 0
                            ),
                        1, 1, 1200
                    )
                )
                .behaviors(
                    new Behavior(
                        new PlaySoundExecutor(Sound.MOB_PIG_SAY),
                            new RandomSoundEvaluator(),
                        8,1
                    ),
                    new Behavior(
                        new FollowRiderExecutor(),
                            new RiderItemControllableEvaluator(),
                        7, 1
                    ),
                    new Behavior(
                        new BreedingExecutor(16, 200, 0.25f),
                            all(
                                e -> !e.isBaby(),
                                e -> e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE)
                            ),
                        6, 1
                    ),
                    new Behavior(
                        new FlatRandomRoamExecutor(this.getMovementSpeedDefault() * 1.25f, 18, 8, true, 80, true, 10),
                            all(
                                e -> e.passengers.isEmpty(),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_BE_ATTACKED_TIME, 0, 80)
                            ),
                        5, 1
                    ),
                    new Behavior(
                        new TemptExecutor(1.2f, TEMPT_ITEMS),
                            all(
                                e -> !e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                                e -> TemptExecutor.hasTemptingPlayer(e, false, 10, TEMPT_ITEMS)
                            ),
                        3, 1
                    ),
                    new Behavior(
                        new LookAtTargetExecutor(CoreMemoryTypes.NEAREST_PLAYER, 100),
                            all(
                                new ProbabilityEvaluator(4, 10),
                                e -> e.getMemoryStorage().notEmpty(CoreMemoryTypes.NEAREST_PLAYER),
                                e -> {
                                    Player p = e.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
                                    return p != null && !e.isPassenger(p);
                                },
                                e -> e.passengers == null || e.passengers.isEmpty()
                            ),
                        1, 1, 100
                    ),
                    new Behavior(
                        new FlatRandomRoamExecutor(this.getMovementSpeedDefault(), 12, 100, false, -1, true, 10),
                            (entity -> true),
                        1, 1
                    )
                )
                .sensors(
                    new NearestPlayerSensor(8, 0, 20))
                .controllers(
                    new WalkController(),
                    new LookController(true, true),
                    new FluctuateController()
                )
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

}
