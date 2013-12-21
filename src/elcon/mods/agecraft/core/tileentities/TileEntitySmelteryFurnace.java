package elcon.mods.agecraft.core.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import elcon.mods.core.tileentities.TileEntityStructure;

public class TileEntitySmelteryFurnace extends TileEntityStructure {

	public static final int MAX_TEMPERATURE = 100;
	
	public byte color = -1;
	public int temperature;
	public ItemStack[] fuel;
	public ItemStack[] ores;
	
	public TileEntitySmelteryFurnace() {
		super();
	}
	
	public TileEntitySmelteryFurnace(String structure, int blockID) {
		super(structure, blockID);
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("Color", color);
	}
}
