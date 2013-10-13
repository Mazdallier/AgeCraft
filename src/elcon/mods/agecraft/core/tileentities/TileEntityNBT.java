package elcon.mods.agecraft.core.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class TileEntityNBT extends TileEntityExtended {

	public NBTTagCompound nbt;
	
	public TileEntityNBT() {
		nbt = new NBTTagCompound();
	}
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(getPacketID());
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			Iterator iterator = nbt.getTags().iterator();
	        while (iterator.hasNext()) {
	            NBTBase nbtbase = (NBTBase)iterator.next();
	            NBTBase.writeNamedTag(nbtbase, dos);
	        }
	        dos.writeByte(0);
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
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		nbt = nbtTagCompound.getCompoundTag("NBT");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setTag("NBT", nbt);
	}
	
	public int getPacketID() {
		return 90;
	}
}
