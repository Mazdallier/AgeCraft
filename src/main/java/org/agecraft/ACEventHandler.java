package org.agecraft;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.RankManager;
import org.agecraft.core.techtree.MessageTechTreeAllComponents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ACEventHandler {

	@SubscribeEvent
	public void onPlayerNameFormat(PlayerEvent.NameFormat event) {
		if(RankManager.hasRank(event.username)) {
			event.displayname = RankManager.getPrefix(event.username) + event.displayname + RankManager.getPostfix(event.username) + EnumChatFormatting.RESET;
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		LinkedList<String> playersOnline = new LinkedList<String>();
		List<EntityPlayerMP> list = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
		for(EntityPlayerMP p : list) {
			playersOnline.add(p.getCommandSenderName());
		}
		
		AgeCraftCore.instance.techTree.packetHandler.sendToPlayer(event.player, new MessageTechTreeAllComponents(event.player.getCommandSenderName()));
		AgeCraft.log.info("[TechTree] Send all components to " + event.player.getCommandSenderName());
		
		//PacketDispatcher.sendPacketToPlayer(getClothingListPacket(), player);
		//if(!PlayerClothingServer.players.containsKey(netHandler.playerEntity.getCommandSenderName())) {
		//	PlayerClothingServer.createDefaultClothing(netHandler.playerEntity.getCommandSenderName());
		//}
		//PacketDispatcher.sendPacketToPlayer(getClothingAllUpdatePacket(playersOnline), player);
		//PacketDispatcher.sendPacketToAllPlayers(getClothingUpdatePacket(PlayerClothingServer.getPlayerClothing(netHandler.playerEntity.getCommandSenderName())));
		//AgeCraft.log.info("[Clothing] Send all clothing to " + netHandler.playerEntity.getCommandSenderName());
	}
}
