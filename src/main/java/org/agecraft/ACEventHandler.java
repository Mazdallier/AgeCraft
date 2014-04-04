package org.agecraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.RankManager;
import org.agecraft.core.clothing.MessageClothingAllUpdate;
import org.agecraft.core.clothing.MessageClothingList;
import org.agecraft.core.clothing.MessageClothingUpdate;
import org.agecraft.core.clothing.PlayerClothingServer;
import org.agecraft.core.techtree.MessageTechTreeAllComponents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class ACEventHandler {

	@SubscribeEvent
	public void onPlayerRespan(PlayerRespawnEvent event) {
		event.player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		event.player.setHealth(100.0F);
	}
	
	@SubscribeEvent
	public void onPlayerNameFormat(PlayerEvent.NameFormat event) {
		if(RankManager.hasRank(event.username)) {
			event.displayname = RankManager.getPrefix(event.username) + event.displayname + RankManager.getPostfix(event.username) + EnumChatFormatting.RESET;
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		ArrayList<String> playersOnline = new ArrayList<String>();
		List<EntityPlayerMP> list = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
		for(EntityPlayerMP p : list) {
			playersOnline.add(p.getCommandSenderName());
		}
		
		AgeCraftCore.instance.packetHandler.sendToPlayer(event.player, new MessageTechTreeAllComponents(event.player.getCommandSenderName()));
		AgeCraft.log.info("[TechTree] Send all components to " + event.player.getCommandSenderName());
		
		AgeCraftCore.instance.packetHandler.sendToPlayer(event.player, new MessageClothingList());
		if(!PlayerClothingServer.players.containsKey(event.player.getCommandSenderName())) {
			PlayerClothingServer.createDefaultClothing(event.player.getCommandSenderName());
		}
		AgeCraftCore.instance.packetHandler.sendToPlayer(event.player, new MessageClothingAllUpdate(playersOnline));
		AgeCraftCore.instance.packetHandler.sendToAllPlayers(new MessageClothingUpdate(PlayerClothingServer.getPlayerClothing(event.player.getCommandSenderName())));
		AgeCraft.log.info("[Clothing] Send all clothing to " + event.player.getCommandSenderName());
	}
}
