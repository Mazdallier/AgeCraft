package org.agecraft.prehistory.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
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
import org.agecraft.prehistory.recipes.RecipesBarrel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.fluids.FluidTankTile;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityPrehistoryBarrel extends TileEntityExtended implements IFluidHandler {

	public static class MessageTilePrehistoryBarrel extends EQMessageTile {

		public int woodType;
		public int stickType;
		public boolean hasLid;
		public FluidStack fluid;
		public ItemStack stack;
		
		public MessageTilePrehistoryBarrel() {
		}
		
		public MessageTilePrehistoryBarrel(int x, int y, int z, int woodType, int stickType, boolean hasLid, FluidStack fluid, ItemStack stack) {
			super(x, y, z);
			this.woodType = woodType;
			this.stickType = stickType;
			this.hasLid = hasLid;
			
			this.fluid = fluid;
			this.stack = stack;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeShort(woodType);
			target.writeShort(stickType);
			target.writeBoolean(hasLid);
			writeFluidStack(target, fluid);		
			writeItemStack(target, stack);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			woodType = source.readShort();
			stickType = source.readShort();
			hasLid = source.readBoolean();
			fluid = readFluidStack(source);
			stack = readItemStack(source);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityPrehistoryBarrel tile = (TileEntityPrehistoryBarrel) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityPrehistoryBarrel();
				world.setTileEntity(x, y, z, tile);
			}
			tile.woodType = woodType;
			tile.stickType = stickType;
			tile.hasLid = hasLid;
			tile.fluid.setFluid(fluid);
			tile.stack = stack;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public int woodType;
	public int stickType = -1;
	public boolean hasLid;
	
	public FluidTankTile fluid;
	public ItemStack stack;
	
	public int ticksLeft = -1;
	
	public TileEntityPrehistoryBarrel() {
		fluid = new FluidTankTile("barrel", this, FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return PrehistoryAge.instance.packetHandler.getPacketToClient(new MessageTilePrehistoryBarrel(xCoord, yCoord, zCoord, woodType, stickType, hasLid, fluid.getFluid(), stack));
	}
	
	public boolean hasFluid() {
		return fluid != null && !fluid.isEmpty();		
	}
	
	public boolean hasStack() {
		return stack != null;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(hasStack() && (!hasFluid() || stickType == -1)) {
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, stack.copy()));
			}
			if(hasLid) {
				if(ticksLeft > 0) {
					ticksLeft--;
				} else if(ticksLeft == 0) { 
					ticksLeft = -1;
					if(RecipesBarrel.getRecipe(stack) != null) {
						stack = RecipesBarrel.getRecipe(stack).output.copy();
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		woodType = nbt.getInteger("WoodType");
		stickType = nbt.getInteger("StickType");
		hasLid = nbt.getBoolean("HasLid");
		if(nbt.hasKey("Fluid")) {
			fluid.readFromNBT(nbt.getCompoundTag("Fluid"));
		}
		if(nbt.hasKey("Stack")) {
			stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Stack"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("WoodType", woodType);
		nbt.setInteger("StickType", stickType);
		nbt.setBoolean("HasLid", hasLid);
		if(fluid != null) {
			NBTTagCompound tag = new NBTTagCompound();
			fluid.writeToNBT(tag);
			nbt.setTag("Fluid", tag);
		}
		if(stack != null) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.writeToNBT(tag);
			nbt.setTag("Stack", tag);
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
