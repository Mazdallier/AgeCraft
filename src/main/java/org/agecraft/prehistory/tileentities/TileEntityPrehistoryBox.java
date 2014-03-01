package org.agecraft.prehistory.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityPrehistoryBox extends TileEntityExtended {

	public static class MessageTilePrehistoryBox extends EQMessageTile {
		
		public int woodType;
		public boolean hasLid;
		public ItemStack[] stacks;
		
		public MessageTilePrehistoryBox() {
		}
		
		public MessageTilePrehistoryBox(int x, int y, int z, int woodType, boolean hasLid, ItemStack[] stacks) {
			super(x, y, z);
			this.woodType = woodType;
			this.hasLid = hasLid;
			this.stacks = stacks;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeShort(woodType);
			target.writeBoolean(hasLid);
			for(int i = 0; i < stacks.length; i++) {
				writeItemStack(target, stacks[i]);
			}
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			woodType = source.readShort();
			hasLid = source.readBoolean();
			stacks = new ItemStack[4];
			for(int i = 0; i < 4; i++) {
				stacks[i] = readItemStack(source);
			}
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityPrehistoryBox tile = (TileEntityPrehistoryBox) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityPrehistoryBox();
				world.setTileEntity(x, y, z, tile);
			}
			tile.woodType = woodType;
			tile.hasLid = hasLid;
			tile.stacks = stacks;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public int woodType;
	public boolean hasLid;

	public ItemStack stacks[] = new ItemStack[4];

	@Override
	public Packet getDescriptionPacket() {
		return PrehistoryAge.instance.packetHandler.getPacketToClient(new MessageTilePrehistoryBox(xCoord, yCoord, zCoord, woodType, hasLid, stacks));
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		woodType = nbt.getInteger("WoodType");
		hasLid = nbt.getBoolean("HasLid");
		NBTTagList list = nbt.getTagList("Stacks", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.getCompoundTagAt(i);
			int slot = tag.getByte("Slot") & 255;
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
			if(stack != null && slot >= 0 && slot < 4) {
				stacks[slot] = stack;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < stacks.length; i++) {
			if(stacks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stacks[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		nbt.setTag("Stacks", list);
	}
}
