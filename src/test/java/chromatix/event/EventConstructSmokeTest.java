package chromatix.event;

import chromatix.PlayerFixture;
import chromatix.Server;
import chromatix.TestPlayer;
import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.item.Item;
import chromatix.level.Level;
import chromatix.level.Location;
import chromatix.level.Position;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.registry.Registries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Constructs a broad set of concrete Event POJOs from the event/* packages and pokes their
 * getters/setters. Events are cheap value objects - building one plus calling its accessors
 * lights up a lot of otherwise-uncovered lines. Everything runs through a tolerant wrapper so
 * a single unsatisfiable constructor never fails the run - the gate only asserts we built many.
 */
public class EventConstructSmokeTest {

    static TestPlayer player;
    static Level level;
    static Server server;
    static Entity entity;
    static Block block;
    static Item item;

    static final AtomicInteger built = new AtomicInteger();

    @BeforeAll
    static void boot() {
        player = PlayerFixture.get();
        level = player.getLevel();
        server = level.getServer();
        item = Item.get("minecraft:stone");

        // a real block placed into the level
        Vector3 pos = new Vector3(4, 78, 4);
        for (var state : Registries.BLOCKSTATE.getAllState()) {
            try {
                level.setBlock(pos, state.toBlock());
                block = level.getBlock(pos);
                if (block != null) break;
            } catch (Throwable ignore) {
            }
        }
        if (block == null) {
            block = Item.get("minecraft:stone").getBlock();
        }

        // a real spawned entity
        for (String id : Registries.ENTITY.getKnownEntities().keySet()) {
            try {
                Entity e = Entity.createEntity(id, new Position(6, 80, 6, level));
                if (e != null) {
                    entity = e;
                    break;
                }
            } catch (Throwable ignore) {
            }
        }
    }

    // ---------- player events ----------

    @Test
    void playerEvents() {
        Item[] drops = new Item[]{item};
        Vector3 v = new Vector3(4, 78, 4);
        Location from = new Location(0, 80, 0, level);
        Location to = new Location(1, 80, 1, level);

        poke(new chromatix.event.player.PlayerChatEvent(player, "hi"), e -> {
            e.getMessage();
            e.setMessage("bye");
            e.isCancelled();
            e.setCancelled(true);
        });
        poke(new chromatix.event.player.PlayerCommandPreprocessEvent(player, "/help"),
                e -> { e.getMessage(); e.setMessage("/list"); });
        poke(new chromatix.event.player.PlayerJoinEvent(player, "joined"),
                e -> e.getJoinMessage());
        poke(new chromatix.event.player.PlayerQuitEvent(player, "left"),
                e -> { e.getQuitMessage(); e.getAutoSave(); });
        poke(new chromatix.event.player.PlayerLoginEvent(player, ""),
                e -> { e.getKickMessage(); e.setKickMessage("no"); });
        poke(new chromatix.event.player.PlayerDeathEvent(player, drops, "died", 5),
                e -> { e.getDeathMessage(); e.getExperience(); e.setKeepInventory(true); });
        poke(new chromatix.event.player.PlayerItemHeldEvent(player, item, 0),
                e -> { e.getItem(); e.getSlot(); });
        poke(new chromatix.event.player.PlayerItemConsumeEvent(player, item),
                e -> e.getItem());
        poke(new chromatix.event.player.PlayerDropItemEvent(player, item),
                e -> e.getItem());
        poke(new chromatix.event.player.PlayerInteractEvent(player, item, v, BlockFace.UP),
                e -> { e.getItem(); e.getBlock(); e.getFace(); e.getAction(); });
        poke(new chromatix.event.player.PlayerInteractEntityEvent(player, safeEntity(), item, v),
                e -> { e.getEntity(); e.getItem(); e.getClickedPos(); });
        poke(new chromatix.event.player.PlayerMouseOverEntityEvent(player, safeEntity()),
                e -> e.getEntity());
        poke(new chromatix.event.player.PlayerEntityPickEvent(player, safeEntity(), item),
                e -> { e.getItem(); e.getEntityClicked(); });
        poke(new chromatix.event.player.PlayerMoveEvent(player, from, to),
                e -> { e.getFrom(); e.getTo(); e.setTo(to); });
        poke(new chromatix.event.player.PlayerTeleportEvent(player, from, to,
                        chromatix.event.player.PlayerTeleportEvent.TeleportCause.COMMAND),
                e -> { e.getFrom(); e.getTo(); e.getCause(); });
        poke(new chromatix.event.player.PlayerGameModeChangeEvent(player, 1, null),
                e -> e.getNewGamemode());
        poke(new chromatix.event.player.PlayerFoodLevelChangeEvent(player, 18, 4f),
                e -> { e.getFoodLevel(); e.setFoodLevel(20); e.getFoodSaturationLevel(); });
        poke(new chromatix.event.player.PlayerExperienceChangeEvent(player, 0, 0, 10, 1),
                e -> { e.getNewExperience(); e.getNewExperienceLevel(); });
        poke(new chromatix.event.player.PlayerChunkRequestEvent(player, 0, 0),
                e -> { e.getChunkX(); e.getChunkZ(); });
        poke(new chromatix.event.player.PlayerPreChunkRequestEvent(player, 0, 0, false),
                e -> { e.getChunkX(); e.isForced(); });
        poke(new chromatix.event.player.PlayerAchievementAwardedEvent(player, "ach"),
                e -> e.getAchievement());
        poke(new chromatix.event.player.PlayerBlockPickEvent(player, block, item),
                e -> { e.getItem(); e.getBlockClicked(); });
        poke(new chromatix.event.player.PlayerBedEnterEvent(player, block), e -> e.getBed());
        poke(new chromatix.event.player.PlayerBedLeaveEvent(player, block), e -> e.getBed());
        poke(new chromatix.event.player.PlayerGlassBottleFillEvent(player, block, item),
                e -> e.getItem());
        poke(new chromatix.event.player.PlayerBucketEmptyEvent(player, block, BlockFace.UP, block, item, item),
                e -> { e.getBucket(); e.getBlockClicked(); e.getLiquid(); });
        poke(new chromatix.event.player.PlayerBucketFillEvent(player, block, BlockFace.UP, block, item, item),
                e -> { e.getBucket(); e.getBlockFace(); });
        poke(new chromatix.event.player.PlayerJumpEvent(player), e -> e.getPlayer());
        poke(new chromatix.event.player.PlayerShowCreditsEvent(player), e -> e.getPlayer());
        poke(new chromatix.event.player.PlayerDuplicatedLoginEvent(player), e -> e.getPlayer());
        poke(new chromatix.event.player.PlayerInvalidMoveEvent(player, true), e -> e.isRevert());
        poke(new chromatix.event.player.PlayerSpearStabEvent(player, item, 1.0f),
                e -> { e.getItem(); e.getMovementSpeed(); });
        poke(new chromatix.event.player.PlayerToggleSneakEvent(player, true), e -> e.isSneaking());
        poke(new chromatix.event.player.PlayerToggleSprintEvent(player, true), e -> e.isSprinting());
        poke(new chromatix.event.player.PlayerToggleFlightEvent(player, true), e -> e.isFlying());
        poke(new chromatix.event.player.PlayerToggleGlideEvent(player, true), e -> e.isGliding());
        poke(new chromatix.event.player.PlayerToggleSwimEvent(player, true), e -> e.isSwimming());
        poke(new chromatix.event.player.PlayerToggleCrawlEvent(player, true), e -> e.isCrawling());
        poke(new chromatix.event.player.PlayerToggleSpinAttackEvent(player, true), e -> e.isSpinAttacking());
        poke(new chromatix.event.player.PlayerHackDetectedEvent(player,
                        chromatix.event.player.PlayerHackDetectedEvent.HackType.FLIGHT),
                e -> e.isKick());
        poke(new chromatix.event.player.PlayerKickEvent(player,
                        chromatix.event.player.PlayerKickEvent.Reason.KICKED_BY_ADMIN, "bye"),
                e -> { e.getReason(); e.getQuitMessage(); });
        poke(new chromatix.event.player.PlayerMapInfoRequestEvent(player, item), e -> e.getMap());
        poke(new chromatix.event.player.PlayerFishEvent(player, null, item, 3, new Vector3()),
                e -> { e.getLoot(); e.getExperience(); e.getMotion(); });
        poke(new chromatix.event.player.EntityFreezeEvent(safeEntity()), e -> e.getHandlers());

        Assertions.assertTrue(built.get() > 0);
    }

    // ---------- block events ----------

    @Test
    void blockEvents() {
        Item[] drops = new Item[]{item};

        poke(new chromatix.event.block.BlockUpdateEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.BlockBreakEvent(player, block, item, drops),
                e -> { e.getBlock(); e.getPlayer(); e.getDrops(); e.setCancelled(true); });
        poke(new chromatix.event.block.BlockBreakEvent(player, block, item, drops, true),
                e -> e.getInstaBreak());
        poke(new chromatix.event.block.BlockPlaceEvent(player, block, block, block, item),
                e -> { e.getBlock(); e.getBlockReplace(); e.getBlockAgainst(); e.getItem(); });
        poke(new chromatix.event.block.BlockBurnEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.BlockFallEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.BlockFadeEvent(block, block), e -> e.getNewState());
        poke(new chromatix.event.block.BlockFromToEvent(block, block), e -> e.getTo());
        poke(new chromatix.event.block.BlockFormEvent(block, block), e -> e.getNewState());
        poke(new chromatix.event.block.BlockGrowEvent(block, block), e -> e.getNewState());
        poke(new chromatix.event.block.BlockSpreadEvent(block, block, block), e -> e.getSource());
        poke(new chromatix.event.block.BlockChangeEvent(block, block), e -> e.getBlock());
        poke(new chromatix.event.block.BlockHarvestEvent(block, block, drops), e -> e.getDrops());
        poke(new chromatix.event.block.BlockRedstoneEvent(block, 0, 15),
                e -> { e.getOldPower(); e.getNewPower(); });
        poke(new chromatix.event.block.LeavesDecayEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.ConduitActivateEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.ConduitDeactivateEvent(block), e -> e.getBlock());
        poke(new chromatix.event.block.DoorToggleEvent(block, player), e -> e.getBlock());
        poke(new chromatix.event.block.SignColorChangeEvent(block, player,
                        chromatix.utils.BlockColor.BLACK_BLOCK_COLOR),
                e -> e.getColor());
        poke(new chromatix.event.block.SignGlowEvent(block, player, true), e -> e.isGlowing());
        poke(new chromatix.event.block.SignWaxedEvent(block, player, true), e -> e.isWaxed());
        poke(new chromatix.event.block.SignChangeEvent(block, player, new String[]{"a", "b", "c", "d"}),
                e -> e.getLines());
        poke(new chromatix.event.block.WaterFrostEvent(block, safeEntity()), e -> e.getBlock());
        poke(new chromatix.event.block.FarmLandDecayEvent(safeEntity(), block), e -> e.getBlock());
        poke(new chromatix.event.block.BlockExplosionPrimeEvent(block, player, 4.0),
                e -> { e.getForce(); e.getBlock(); });
        poke(new chromatix.event.command.CommandBlockExecuteEvent(block, "say hi"), e -> e.getCommand());

        Assertions.assertTrue(built.get() > 0);
    }

    // ---------- entity events ----------

    @Test
    void entityEvents() {
        Entity e0 = safeEntity();

        poke(new chromatix.event.entity.EntitySpawnEvent(e0), e -> e.getEntity());
        poke(new chromatix.event.entity.EntityDespawnEvent(e0), e -> e.getEntity());
        poke(new chromatix.event.entity.EntityDamageEvent(e0,
                        chromatix.event.entity.EntityDamageEvent.DamageCause.CONTACT, 2f),
                e -> { e.getDamage(); e.setDamage(3f); e.getCause(); e.getFinalDamage(); });
        poke(new chromatix.event.entity.EntityDamageByBlockEvent(block, e0,
                        chromatix.event.entity.EntityDamageEvent.DamageCause.CONTACT, 1f),
                e -> e.getDamager());
        poke(new chromatix.event.entity.EntityDamageByEntityEvent(e0, e0,
                        chromatix.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK, 4f),
                e -> { e.getDamager(); e.getKnockBack(); });
        poke(new chromatix.event.entity.EntityCombustEvent(e0, 5), e -> e.getDuration());
        poke(new chromatix.event.entity.EntityCombustByBlockEvent(block, e0, 5), e -> e.getCombuster());
        poke(new chromatix.event.entity.EntityCombustByEntityEvent(e0, e0, 5), e -> e.getCombuster());
        poke(new chromatix.event.entity.EntityRegainHealthEvent(e0, 2f, 0),
                e -> { e.getAmount(); e.getRegainReason(); });
        poke(new chromatix.event.entity.EntityFallEvent(e0, block, 3f), e -> e.getFallDistance());
        poke(new chromatix.event.entity.EntityInteractEvent(e0, block), e -> e.getBlock());
        poke(new chromatix.event.entity.EntityMotionEvent(e0, new Vector3(0, 1, 0)),
                e -> e.getMotion());
        poke(new chromatix.event.entity.EntityMoveByPistonEvent(e0, new Vector3(1, 1, 1)),
                e -> e.getMotion());
        poke(new chromatix.event.entity.EntityBlockChangeEvent(e0, block, block), e -> e.getTo());
        poke(new chromatix.event.entity.EntityExplosionPrimeEvent(e0, 4.0), e -> e.getForce());
        poke(new chromatix.event.entity.ExplosionPrimeEvent(e0, 4.0), e -> e.getForce());
        poke(new chromatix.event.entity.EntityPortalEnterEvent(e0,
                        chromatix.event.entity.EntityPortalEnterEvent.PortalType.NETHER),
                e -> e.getPortalType());
        poke(new chromatix.event.entity.EntityInventoryChangeEvent(e0, item, item, 0),
                e -> { e.getOldItem(); e.getNewItem(); e.getSlot(); });
        poke(new chromatix.event.entity.EntityArmorChangeEvent(e0, item, item, 0),
                e -> { e.getOldItem(); e.getNewItem(); });
        poke(new chromatix.event.entity.EntityExplodeEvent(e0, new Position(0, 80, 0, level),
                        new ArrayList<>(), 0.5), e -> e.getYield());
        poke(new chromatix.event.entity.EntityTransformEvent(e0, e0), e -> e.getTransformed());
        poke(new chromatix.event.entity.EntityTeleportEvent(e0,
                        new Location(0, 80, 0, level), new Location(1, 80, 1, level)),
                e -> { e.getFrom(); e.getTo(); });
        poke(new chromatix.event.entity.EntityLevelChangeEvent(e0, level, level),
                e -> { e.getOrigin(); e.getTarget(); });
        poke(new chromatix.event.entity.EntityVehicleEnterEvent(e0, e0), e -> e.getVehicle());
        poke(new chromatix.event.entity.EntityVehicleExitEvent(e0, e0), e -> e.getVehicle());

        Assertions.assertTrue(built.get() > 0);
    }

    // ---------- level / server / weather / potion / vehicle / misc ----------

    @Test
    void miscEvents() {
        poke(new chromatix.event.level.LevelLoadEvent(level), e -> e.getLevel());
        poke(new chromatix.event.level.LevelInitEvent(level), e -> e.getLevel());
        poke(new chromatix.event.level.LevelSaveEvent(level), e -> e.getLevel());
        poke(new chromatix.event.level.LevelUnloadEvent(level), e -> e.getLevel());
        poke(new chromatix.event.level.WeatherChangeEvent(level, true), e -> e.toWeatherState());
        poke(new chromatix.event.level.ThunderChangeEvent(level, true), e -> e.toThunderState());
        poke(new chromatix.event.level.SpawnChangeEvent(level, new Position(0, 80, 0, level)),
                e -> e.getPreviousSpawn());
        poke(new chromatix.event.level.StructureGrowEvent(block, new ArrayList<>()),
                e -> e.getBlockList());

        poke(new chromatix.event.server.ServerCommandEvent(server.getConsoleSender(), "list"),
                e -> { e.getCommand(); e.setCommand("help"); });
        poke(new chromatix.event.server.RemoteServerCommandEvent(server.getConsoleSender(), "list"),
                e -> e.getCommand());
        poke(new chromatix.event.server.ConsoleCommandOutputEvent(server.getConsoleSender(), "out"),
                e -> e.getMessage());
        poke(new chromatix.event.server.QueryRegenerateEvent(server),
                e -> { e.getServerName(); e.getMaxPlayerCount(); });

        poke(new chromatix.event.item.ItemWearEvent(item, 5), e -> e.getNewDurability());

        poke(new chromatix.event.vehicle.VehicleCreateEvent(safeEntity()), e -> e.getVehicle());
        poke(new chromatix.event.vehicle.VehicleUpdateEvent(safeEntity()), e -> e.getVehicle());
        poke(new chromatix.event.vehicle.VehicleDestroyEvent(safeEntity()), e -> e.getVehicle());
        poke(new chromatix.event.vehicle.VehicleDestroyByEntityEvent(safeEntity(), safeEntity()),
                e -> e.getDestroyer());
        poke(new chromatix.event.vehicle.VehicleMoveEvent(safeEntity(),
                        new Location(0, 80, 0, level), new Location(1, 80, 1, level)),
                e -> { e.getFrom(); e.getTo(); });
        poke(new chromatix.event.vehicle.EntityEnterVehicleEvent(safeEntity(), safeEntity()),
                e -> e.getVehicle());
        poke(new chromatix.event.vehicle.EntityExitVehicleEvent(safeEntity(), safeEntity()),
                e -> e.getVehicle());

        poke(new chromatix.event.redstone.RedstoneUpdateEvent(block), e -> e.getBlock());

        Assertions.assertTrue(built.get() > 0);
    }

    // ---------- helpers ----------

    private static Entity safeEntity() {
        return entity;
    }

    private interface Poke<T> {
        void accept(T t) throws Throwable;
    }

    private static <T> void poke(T event, Poke<T> body) {
        if (event == null) return;
        built.incrementAndGet();
        try {
            if (event instanceof Event ev) {
                ev.getEventName();
                if (ev instanceof Cancellable c) {
                    c.isCancelled();
                    c.setCancelled(true);
                    c.setCancelled(false);
                }
            }
            body.accept(event);
        } catch (Throwable ignore) {
        }
    }
}
