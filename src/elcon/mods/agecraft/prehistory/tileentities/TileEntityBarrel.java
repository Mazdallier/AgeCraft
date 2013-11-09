package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.item.EntityItem;
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
import elcon.mods.agecraft.core.fluids.FluidTankTile;
import elcon.mods.agecraft.prehistory.recipes.RecipesBarrel;
import elcon.mods.core.tileentities.TileEntityExtended;

public class TileEntityBarrel extends TileEntityExtended implements IFluidHandler {

	public int woodType;
	public int stickType = -1;
	public boolean hasLid;
	
	public FluidTankTile fluid;
	public ItemStack stack;
	
	public int ticksLeft = -1;
	
	public TileEntityBarrel() {
		fluid = new FluidTankTile("barrel", this, FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(203);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			dos.writeInt(woodType);
			dos.writeInt(stickType);			
			dos.writeBoolean(hasLid);
			
			dos.writeBoolean(fluid.getFluid() != null && fluid.getFluid().getFluid() != null);
			if(fluid.getFluid() != null && fluid.getFluid().getFluid() != null) {
				dos.writeInt(fluid.getFluid().getFluid().getID());
				dos.writeInt(fluid.getFluid().amount);
			}
			
			dos.writeBoolean(stack != null);
			if(stack != null) {
				dos.writeInt(stack.itemID);
				dos.writeInt(stack.stackSize);
				dos.writeInt(stack.getItemDamage());
				
				dos.writeBoolean(stack.hasTagCompound());
				if(stack.hasTagCompound()) {
					try {
						NBTBase.writeNamedTag(stack.stackTagCompound, dos);
					} catch(Exception e) {
						e.printStackTrace();
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
			nbt.setCompoundTag("Fluid", tag);
		}
		if(stack != null) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.writeToNBT(tag);
			nbt.setCompoundTag("Stack", tag);
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
