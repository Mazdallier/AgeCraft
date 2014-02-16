package org.agecraft.core.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import elcon.mods.core.tileentities.TileEntityStructure;

public class TileEntitySmelteryFurnace extends TileEntityStructure {

	public static final int MAX_TEMPERATURE = 1000;

	public byte color = -1;

	public byte size;
	public int temperature;
	public ItemStack[] ores;
	public ItemStack[] fuel;

	public TileEntitySmelteryFurnace() {
		super();
	}

	public TileEntitySmelteryFurnace(String structure, int blockID) {
		super(structure, blockID);
	}

	@Override
	public void markStructureBlocks() {
		super.markStructureBlocks();
		setSize((byte) (structurePattern.charAt(8) - '0'));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setSize(byte size) {
		this.size = size;
		if(size >= 3) {
			if(fuel == null || fuel.length != (size * size)) {
				fuel = new ItemStack[size * size];
			}
			if(ores == null || ores.length != ((int) Math.pow(size - 2, 3))) {
				ores = new ItemStack[(int) Math.pow(size - 2, 3)];
			}
		}
	}

	public boolean hasFuel() {
		if(fuel != null) {
			for(int i = 0; i < fuel.length; i++) {
				if(fuel[i] != null && fuel[i].stackSize > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isBurning() {
		return hasFuel() && getTemperature() > 0;
	}

	public int getTemperature() {
		return temperature;
	}

	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(100);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);

			dos.writeBoolean(hasStructure());
			dos.writeByte(color);

			dos.writeBoolean(isMaster());
			if(isMaster()) {
				dos.writeByte(size);
				dos.writeInt(temperature);
				dos.writeInt(ores == null ? -1 : ores.length);
				if(ores != null) {
					for(int i = 0; i < ores.length; i++) {
						Packet.writeItemStack(ores[i], dos);
					}
				}
				dos.writeInt(fuel == null ? -1 : fuel.length);
				if(fuel != null) {
					for(int i = 0; i < fuel.length; i++) {
						Packet.writeItemStack(fuel[i], dos);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "ACTile";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		return packet;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		color = nbt.getByte("Color");
		setSize(nbt.getByte("Size"));
		temperature = nbt.getInteger("Temperature");

		if(nbt.hasKey("Ores")) {
			NBTTagList oreList = nbt.getTagList("Ores");
			for(int i = 0; i < oreList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) oreList.tagAt(i);
				int slot = tag.getInteger("Slot");
				ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
				if(stack != null && slot >= 0 && slot < ores.length) {
					ores[slot] = stack;
				}
			}
		}
		if(nbt.hasKey("Fuel")) {
			NBTTagList fuelList = nbt.getTagList("Fuel");
			for(int i = 0; i < fuelList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) fuelList.tagAt(i);
				int slot = tag.getInteger("Slot");
				ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
				if(stack != null && slot >= 0 && slot < fuel.length) {
					fuel[slot] = stack;
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("Color", color);
		nbt.setByte("Size", size);
		nbt.setInteger("Temperature", temperature);

		if(ores != null) {
			NBTTagList oreList = new NBTTagList();
			for(int i = 0; i < ores.length; i++) {
				if(ores[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Slot", i);
					ores[i].writeToNBT(tag);
					oreList.appendTag(tag);
				}
			}
			nbt.setTag("Ores", oreList);
		}
		if(fuel != null) {
			NBTTagList fuelList = new NBTTagList();
			for(int i = 0; i < fuel.length; i++) {
				if(fuel[i] != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Slot", i);
					fuel[i].writeToNBT(tag);
					fuelList.appendTag(tag);
				}
			}
			nbt.setTag("Fuel", fuelList);
		}
	}
}
