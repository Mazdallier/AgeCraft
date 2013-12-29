package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import elcon.mods.core.fluid.FluidTankTile;
import elcon.mods.core.tileentities.TileEntityExtended;

public class TileEntityPot extends TileEntityExtended implements IFluidHandler {
	
	public boolean hasLid;
	public FluidTankTile fluid;
	public ItemStack dust;
	
	public TileEntityPot() {
		fluid = new FluidTankTile("pot", this, FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(201);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			dos.writeBoolean(hasLid);
			
			dos.writeBoolean(fluid.getFluid() != null && fluid.getFluid().getFluid() != null);
			if(fluid.getFluid() != null && fluid.getFluid().getFluid() != null) {
				dos.writeInt(fluid.getFluid().getFluid().getID());
				dos.writeInt(fluid.getFluid().amount);
				dos.writeBoolean(fluid.getFluid().tag != null);
				if(fluid.getFluid().tag != null) {
					NBTBase.writeNamedTag(fluid.getFluid().tag, dos);
				}
			}
			
			Packet.writeItemStack(dust, dos);
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
	
	public boolean isEmpty() {
		return !hasFluid() && !hasDust();
	}
	
	public boolean hasFluid() {
		return fluid != null && !fluid.isEmpty();
	}
	
	public boolean hasDust() {
		return dust != null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hasLid = nbt.getBoolean("HasLid");
		if(nbt.hasKey("Fluid")) {
			fluid.readFromNBT(nbt.getCompoundTag("Fluid"));
		}
		if(nbt.hasKey("Dust")) {
			dust = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Dust"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("HasLid", hasLid);
		if(fluid != null) {
			NBTTagCompound tag = new NBTTagCompound();
			fluid.writeToNBT(tag);
			nbt.setCompoundTag("Fluid", tag);
		}
		if(dust != null) {
			NBTTagCompound tag = new NBTTagCompound();
			dust.writeToNBT(tag);
			nbt.setCompoundTag("Dust", tag);
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource == null) {
			return 0;
		}
		return fluid.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource == null) {
			return null;
		}
		if(!resource.isFluidEqual(fluid.getFluid())) {
			return null;
		}
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return fluid.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{fluid.getInfo()};
	}
}
