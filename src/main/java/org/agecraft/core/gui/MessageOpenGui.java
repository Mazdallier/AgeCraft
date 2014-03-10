package org.agecraft.core.gui;

import org.agecraft.AgeCraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import elcon.mods.elconqore.network.EQMessage;

public class MessageOpenGui extends EQMessage {

	public int dimensionID;
	public String player;
	public int x;
	public int y;
	public int z;
	public int guiID;
	
	public MessageOpenGui() {
	}
	
	public MessageOpenGui(int dimensionID, String player, int x, int y, int z, int guiID) {
		this.dimensionID = dimensionID;
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		this.guiID = guiID;
	}
	
	@Override
	public void encodeTo(ByteBuf target) {
		target.writeShort(dimensionID);
		writeString(target, player);
		target.writeInt(x);
		target.writeInt(y);
		target.writeInt(z);
		target.writeShort(guiID);
	}
	
	@Override
	public void decodeFrom(ByteBuf source) {
		dimensionID = source.readShort();
		player = readString(source);
		x = source.readInt();
		y = source.readInt();
		z = source.readInt();
		guiID = source.readShort();
	}
	
	@Override
	public void handle() {
		WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dimensionID);
		EntityPlayer p = world.getPlayerEntityByName(player);
		p.openGui(AgeCraft.instance, guiID, world, x, y, z);
	}
}
