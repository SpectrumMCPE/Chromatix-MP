package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.Server;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityItemFrame;
import chromatix.event.player.PlayerMapInfoRequestEvent;
import chromatix.item.Item;
import chromatix.item.ItemFilledMap;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import chromatix.plugin.InternalPlugin;
import chromatix.scheduler.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.packet.MapInfoRequestPacket;

/**
 * @author Kaooot
 */
@Slf4j
public class MapInfoRequestHandler implements PacketHandler<MapInfoRequestPacket> {

    @Override
    public void handle(MapInfoRequestPacket packet, PlayerSessionHolder holder, Server server) {
        Player player = holder.getPlayer();


        if (packet.getMapUniqueID() <= 0) {
            log.debug("Player {} sent an invalid map id {}", player.getName(), packet.getMapUniqueID());
            return;
        }

        if (!player.isAlive() || player.level == null) {
            log.debug("Player {} tried to request map info while dead or without a level loaded", player.getName());
            return;
        }

        Item mapItem = null;
        int index = 0;
        var offhand = false;

        for (var entry : player.getOffhandInventory().getContents().entrySet()) {
            var item1 = entry.getValue();
            if (checkMapItemValid(item1, packet)) {
                mapItem = item1;
                index = entry.getKey();
                offhand = true;
            }
        }

        if (mapItem == null) {
            for (var entry : player.getInventory().getContents().entrySet()) {
                var item1 = entry.getValue();
                if (checkMapItemValid(item1, packet)) {
                    mapItem = item1;
                    index = entry.getKey();
                }
            }
        }

        if (mapItem == null) {
            for (BlockEntity be : player.level.getBlockEntities().values()) {
                if (be instanceof BlockEntityItemFrame itemFrame && checkMapItemValid(itemFrame.getItem(), packet)) {
                    ((ItemFilledMap) itemFrame.getItem()).sendImage(player);
                    break;
                }
            }
        }

        if (mapItem != null) {
            PlayerMapInfoRequestEvent event;
            player.getServer().getPluginManager().callEvent(event = new PlayerMapInfoRequestEvent(player, mapItem));

            if (!event.isCancelled()) {
                ItemFilledMap map = (ItemFilledMap) mapItem;
                if (map.trySendImage(player)) {
                    return;
                }

                final int finalIndex = index;
                final boolean finalOffhand = offhand;
                player.getLevel().getScheduler().scheduleAsyncTask(InternalPlugin.INSTANCE, new AsyncTask() {
                    @Override
                    public void onRun() {
                        int zoom = Math.max(1, map.getMapScale());
                        int mapSize = 128 * zoom;
                        int halfMapSize = mapSize >> 1;
                        int startX = Math.floorDiv(player.getFloorX() + halfMapSize, mapSize) * mapSize - halfMapSize;
                        int startZ = Math.floorDiv(player.getFloorZ() + halfMapSize, mapSize) * mapSize - halfMapSize;
                        map.renderMap(player.getLevel(), startX, startZ, zoom);
                        if (finalOffhand) {
                            if (checkMapItemValid(player.getOffhandInventory().getUnclonedItem(finalIndex), packet))
                                player.getOffhandInventory().setItem(finalIndex, map);
                        } else {
                            if (checkMapItemValid(player.getInventory().getUnclonedItem(finalIndex), packet))
                                player.getInventory().setItem(finalIndex, map);
                        }
                        map.sendImage(player);
                    }
                });
            }
        }
    }

    protected boolean checkMapItemValid(Item item, MapInfoRequestPacket pk) {
        return item instanceof ItemFilledMap itemMap && itemMap.getMapId() == pk.getMapUniqueID();
    }
}