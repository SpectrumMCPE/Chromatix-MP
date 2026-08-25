package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.event.player.PlayerFormRespondedEvent;
import chromatix.event.player.PlayerHackDetectedEvent;
import chromatix.event.player.PlayerSettingsRespondedEvent;
import chromatix.form.element.custom.ElementCustom;
import chromatix.form.element.custom.ElementDropdown;
import chromatix.form.element.custom.ElementInput;
import chromatix.form.element.custom.ElementSlider;
import chromatix.form.element.custom.ElementStepSlider;
import chromatix.form.element.custom.ElementToggle;
import chromatix.form.response.CustomResponse;
import chromatix.form.response.ElementResponse;
import chromatix.form.response.Response;
import chromatix.form.window.CustomForm;
import chromatix.form.window.Form;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.data.ModalFormCancelReason;
import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket;

/**
 * @author Kaooot
 */
@Slf4j
public class ModalFormResponseHandler implements PacketHandler<ModalFormResponsePacket> {

    @Override
    public void handle(ModalFormResponsePacket packet, PlayerSessionHolder holder, Server server) {
        final PlayerHandle playerHandle = holder.getPlayerHandle();
        Player player = playerHandle.player;
        if (!player.spawned || !player.isAlive()) {
            return;
        }

        if (!playerHandle.packetRateLimiter.tryFormResponse()) {
            PlayerHackDetectedEvent event = new PlayerHackDetectedEvent(
                    playerHandle.player, PlayerHackDetectedEvent.HackType.MODAL_SPAM);
            playerHandle.player.getServer().getPluginManager().callEvent(event);
            if (event.isKick()) {
                playerHandle.player.getSession().close("Exceeding modal spam rate-limit");
            }
            return;
        }

        String jsonResponse = packet.getJsonResponse();
        ModalFormCancelReason cancelReason = packet.getFormCancelReason().orElse(null);

        if (jsonResponse != null && jsonResponse.length() > 1024) {
            player.close("§cPacket handling error");
            return;
        }

        String formData = jsonResponse == null ? "" : jsonResponse.trim();

        if (playerHandle.getFormWindows().containsKey(packet.getFormID())) {
            Form<?> window = playerHandle.getFormWindows().remove(packet.getFormID());

            Response response = window.respond(player, formData, cancelReason);

            PlayerFormRespondedEvent event = new PlayerFormRespondedEvent(player, packet.getFormID(), window, response);
            player.getServer().getPluginManager().callEvent(event);
        } else if (playerHandle.getServerSettings().containsKey(packet.getFormID())) {
            Form<?> window = playerHandle.getServerSettings().get(packet.getFormID());

            Response response = window.respond(player, formData, cancelReason);

            PlayerSettingsRespondedEvent event = new PlayerSettingsRespondedEvent(player, packet.getFormID(), window, response);
            player.getServer().getPluginManager().callEvent(event);

            // Apply responses as default settings
            if (!event.isCancelled() && window instanceof CustomForm customForm && response != null) {
                ((CustomResponse) response).getResponses().forEach((i, res) -> {
                    ElementCustom e = customForm.elements().get(i);
                    switch (e) {
                        case ElementDropdown dropdown -> dropdown.defaultOption(((ElementResponse) res)
                                .elementId());
                        case ElementInput input -> input.defaultText(String.valueOf(res));
                        case ElementSlider slider -> slider.defaultValue((Float) res);
                        case ElementToggle toggle -> toggle.defaultValue((Boolean) res);
                        case ElementStepSlider stepSlider -> stepSlider.defaultStep(((ElementResponse) res)
                                .elementId());
                        default -> log.warn("Illegal element {} within ServerSettings", e);
                    }
                });
            }
        } else log.warn("{} sent unknown form id {}", player.getName(), packet.getFormID());
    }
}
