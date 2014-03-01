package org.agecraft.prehistory.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.fluids.FluidTankTile;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityPrehistoryPot extends TileEntityExtended implements IFluidHandler {
	
	public static class MessageTilePrehistoryPot extends EQMessageTile {
		
		public boolean hasLid;
		public FluidStack fluid;
		public ItemStack dust;
		
		public MessageTilePrehistoryPot() {
		}
		
		public MessageTilePrehistoryPot(int x, int y, int z, boolean hasLid, FluidStack fluid, ItemStack dust) {
			super(x, y, z);
			this.hasLid = hasLid;
			this.fluid = fluid;
			this.dust = dust;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeBoolean(hasLid);
			writeFluidStack(target, fluid);
			writeItemStack(target, dust);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			hasLid = source.readBoolean();
			fluid = readFluidStack(source);
			dust = readItemStack(source);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityPrehistoryPot tile = (TileEntityPrehistoryPot) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityPrehistoryPot();
				world.setTileEntity(x, y, z, tile);
			}
			tile.hasLid = hasLid;
			tile.fluid.setFluid(fluid);
			tile.dust = dust;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public boolean hasLid;
	public FluidTankTile fluid;
	public ItemStack dust;
	
	public TileEntityPrehistoryPot() {
		fluid = new FluidTankTile("pot", this, FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return PrehistoryAge.instance.packetHandler.getPacketToClient(new MessageTilePrehistoryPot(xCoord, yCoord, zCoord, hasLid, fluid.getFluid(), dust));
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
			nbt.setTag("Fluid", tag);
		}
		if(dust != null) {
			NBTTagCompound tag = new NBTTagCompound();
			dust.writeToNBT(tag);
			nbt.setTag("Dust", tag);
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
