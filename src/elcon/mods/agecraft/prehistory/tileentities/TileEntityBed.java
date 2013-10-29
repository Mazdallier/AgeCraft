package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import elcon.mods.core.tileentities.TileEntityExtended;

public class TileEntityBed extends TileEntityExtended {

	public boolean isFoot;
	public byte direction;
	public int color;
	public boolean isOccupied;
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(202);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			dos.writeBoolean(isFoot);
			dos.writeByte(direction);
			dos.writeInt(color);
			dos.writeBoolean(isOccupied);
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
