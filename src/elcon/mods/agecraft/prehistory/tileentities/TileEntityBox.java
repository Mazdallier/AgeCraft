package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import elcon.mods.core.tileentities.TileEntityExtended;

public class TileEntityBox extends TileEntityExtended {

	public int woodType;
	public boolean hasLid;

	public ItemStack stacks[] = new ItemStack[4];

	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(204);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);

			dos.writeInt(woodType);
			dos.writeBoolean(hasLid);

			for(int i = 0; i < stacks.length; i++) {
				Packet.writeItemStack(stacks[i], dos);
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

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		woodType = nbt.getInteger("WoodType");
		hasLid = nbt.getBoolean("HasLid");
		NBTTagList list = nbt.getTagList("Stacks");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
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
