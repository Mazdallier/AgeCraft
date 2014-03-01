package org.agecraft.prehistory.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityPrehistoryBed extends TileEntityExtended {

	public static class MessageTilePrehistoryBed extends EQMessageTile {
		
		public boolean isFoot;
		public byte direction;
		public int color;
		public boolean isOccupied;
		
		public MessageTilePrehistoryBed() {
			
		}
		
		public MessageTilePrehistoryBed(int x, int y, int z, boolean isFoot, byte direction, int color, boolean isOccupied) {
			super(x, y, z);
			this.isFoot = isFoot;
			this.direction = direction;
			this.color = color;
			this.isOccupied = isOccupied;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeBoolean(isFoot);
			target.writeByte(direction);
			target.writeInt(color);
			target.writeBoolean(isOccupied);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			isFoot = source.readBoolean();
			direction = source.readByte();
			color = source.readInt();
			isOccupied = source.readBoolean();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityPrehistoryBed tile = (TileEntityPrehistoryBed) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityPrehistoryBed();
				world.setTileEntity(x, y, z, tile);
			}
			tile.isFoot = isFoot;
			tile.direction = direction;
			tile.color = color;
			tile.isOccupied = isOccupied;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public boolean isFoot;
	public byte direction;
	public int color;
	public boolean isOccupied;
	
	@Override
	public Packet getDescriptionPacket() {		
		return PrehistoryAge.instance.packetHandler.getPacketToClient(new MessageTilePrehistoryBed(xCoord, yCoord, zCoord, isFoot, direction, color, isOccupied));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isFoot = nbt.getBoolean("IsFoot");
		direction = nbt.getByte("Direction");
		color = nbt.getInteger("Color");
		isOccupied = nbt.getBoolean("IsOccupied");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("IsFoot", isFoot);
		nbt.setByte("Direction", direction);
		nbt.setInteger("Color", color);
		nbt.setBoolean("IsOccupied", isOccupied);
	}
}
