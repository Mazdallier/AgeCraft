package org.agecraft.core.techtree;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessage;

public class MessageTechTreeComponent extends EQMessage {

	public String player;
	public String pageName;
	public String name;
	public boolean unlocked;
	
	public MessageTechTreeComponent() {
		
	}
	
	public MessageTechTreeComponent(String player, String pageName, String name, boolean unlocked) {
		this.player = player;
		this.pageName = pageName;
		this.name = name;
		this.unlocked = unlocked;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		writeString(target, player);
		writeString(target, pageName);
		writeString(target, name);
		target.writeBoolean(unlocked);
	}

	@Override
	public void decodeFrom(ByteBuf source) {
		player = readString(source);
		pageName = readString(source);
		name = readString(source);
		unlocked = source.readBoolean();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle() {
		if(EQUtilClient.getPlayer().getCommandSenderName().equals(player)) {
			if(unlocked) {
				TechTreeClient.unlockComponent(pageName, name);
			} else {
				TechTreeClient.lockComponent(pageName, name);
			}
		}
	}
}
