package org.agecraft;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.agecraft.core.RankManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ACEventHandler {

	@SubscribeEvent
	public void onPlayerNameFormat(PlayerEvent.NameFormat event) {
		if(RankManager.hasRank(event.username)) {
			event.displayname = RankManager.getPrefix(event.username) + event.displayname + RankManager.getPostfix(event.username) + EnumChatFormatting.RESET;
		}
	}
}
