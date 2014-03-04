package org.agecraft.core.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.agecraft.core.AgeCraftCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityAnvil extends TileEntityExtended {

	public static class MessageTileAnvil extends EQMessageTile {
		
		public byte type;
		public byte damage;
		public byte direction;
		
		public MessageTileAnvil() {
			
		}
		
		public MessageTileAnvil(int x, int y, int z, byte type, byte damage, byte direction) {
			super(x, y, z);
			this.type = type;
			this.damage = damage;
			this.direction = direction;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeByte(type);
			target.writeByte(damage);
			target.writeByte(direction);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			type = source.readByte();
			damage = source.readByte();
			direction = source.readByte();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityAnvil tile = (TileEntityAnvil) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityAnvil();
				world.setTileEntity(x, y, z, tile);
			}
			tile.type = type;
			tile.damage = damage;
			tile.direction = direction;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public byte type;
	public byte damage;
	public byte direction;
	
	@Override
	public Packet getDescriptionPacket() {
		return AgeCraftCore.instance.packetHandler.getPacketToClient(new MessageTileAnvil(xCoord, yCoord, zCoord, type, damage, direction));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		type = nbt.getByte("Type");
		damage = nbt.getByte("Damage");
		direction = nbt.getByte("Direction");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("Type", type);
		nbt.setByte("Damage", damage);
		nbt.setByte("Direction", direction);
	}

	public int getMeta() {
		return damage + (type * 4);
	}
}
