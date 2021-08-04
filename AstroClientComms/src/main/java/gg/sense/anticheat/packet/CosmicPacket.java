package gg.sense.anticheat.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gg.sense.anticheat.packet.client.*;
import gg.sense.anticheat.packet.client.ClientPacket;

/**
 * @author Decencies
 * @since 13/07/2021 17:08
 */
public class CosmicPacket {

    private static final BiMap<Short, Class<? extends ClientPacket>> REGISTRY = HashBiMap.create();
    private static short currentId;

    static {
        register(CChunkDataRequestPacket.class);
        register(CClaimCoordPacket.class);
        register(CCooldownRequestPacket.class);
        register(emptypacket1.class);
        register(CFactionInfoRequestPacket.class);
        register(emptypacket2.class);
        register(CHandshakePacket.class);
        register(CInfoPacket.class);
        register(fps.class);
        register(CModsPacket.class);
        register(CPermissionRequestPacket.class);
        register(CPingAlliesPacket.class);
        register(CPlayerFactionRequestPacket.class);
        register(CResolutionPacket.class);
        register(CSetChannelPacket.class);
        register(CStartEmotePacket.class);
        register(CTimeRequestPacket.class);
        register(CVoicePacket.class);
        register(CWorldBorderRequestPacket.class);
        register(SChunkDataPacket.class);
        register(SClearPromosPacket.class);
        register(SCooldownPacket.class);
        register(SCreateZonePacket.class);
        register(SDefaultSkinnedPlayersPacket.class);
        register(SEmotesResponsePacket.class);
        register(SEnchantListPacket.class);
        register(SEventPacket.class);
        register(SFactionInfoResponsePacket.class);
        register(SFactionListResponsePacket.class);
        register(SFeatureSetPacket.class);
        register(SFocusedPlayerPacket.class);
        register(SItemCooldownsListPacket.class);
        register(SKeepAlive.class);
        register(SNotificationPacket.class);
        register(SPermissionResponsePacket.class);
        register(SPingAlliesPacket.class);
        register(SPlayerFactionResponsePacket.class);
        register(SPlayerHeadersPacket.class);
        register(SPlayerOutlineColor.class);
        register(SPopUpPacket.class);
        register(SRoyalePacket.class);
        register(SSetEntitySizePacket.class);
        register(SSetTeamPacket.class);
        register(SSetWorldNamePacket.class);
        register(SShowEmotePacket.class);
        register(STimeResponsePacket.class);
        register(SVoicePacket.class);
        register(SWorldBorderResponsePacket.class);
        register(SHitSplatPacket.class);
        register(SHideNametags.class);
        register(CWorldNameRequestPacket.class);
        register(emptypacket3.class);
        register(SShowXPBarPacket.class);
        register(SAddXPPacket.class);
        register(SXPTextPacket.class);
        register(SSetXPPacket.class);
        register(SFlagBossPacket.class);
        register(SHighlightBlockPacket.class);
        register(SRevealBlockPacket.class);
        register(SSetMapImagePacket.class);
        register(SSetEntityProperties.class);
        register(CMobHelperStatusPacket.class);
        register(emptypacket4.class);
        register(SSetBlockProperties.class);
        register(SScoreboardPacket.class);
        register(SPollSendPacket.class);
        register(CPollResponsePacket.class);
        register(SSetWaypointPacket.class);
        register(SPurchaseFeedPacket.class);
        register(STooltipPacket.class);
        register(SClearTooltipPacket.class);
        register(SSetHealthBarPacket.class);
        register(colorMap.class);
        register(SBarAPIPacket.class);
    }

    static void register(Class<? extends ClientPacket> packet) {
        REGISTRY.put(CosmicPacket.currentId++, packet);
    }

    static short getPacketId(Class<? extends ClientPacket> packet) {
        return REGISTRY.inverse().get(packet);
    }


}
