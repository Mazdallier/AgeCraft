package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.items.ItemWoodStick;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire.RecipeCampfire;

public class TileEntityCampfire extends TileEntity {

	public int burnTime;
	public int cookTime;
	public boolean hasFire;
	
	public int[] logs = new int[TreeRegistry.trees.length];
	public int logCount;
	public int currentLogIndex;
	
	public RecipeCampfire recipe;
	public ItemStack spitStack;
	
	public byte frameDirection;
	public byte frameStage;
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(200);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);			
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
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(isBurning()) {
				burnTime--;
				if(burnTime <= 0) {
					burnTime = 0;
					cookTime = 0;
					hasFire = false;
					dropSpitStack();
				}
				if(isCooking()) {
					cookTime--;
					if(cookTime <= 0) {
						cookTime = 0;
						dropSpitStack();
					}
				}
			} else if(canBurn()) {
				
			}
		}
	}

	public boolean onBlockActivated(int side, ItemStack stack) {
		if(stack != null) {
			if(RecipesCampfire.getRecipe(stack) != null) {
				if(!isCooking()) {
					ItemStack s = stack.copy();
					s.stackSize = 1;
					setSpitStack(s, RecipesCampfire.getRecipe(stack));
					return true;
				}				
			} else if(stack.getItem() instanceof ItemWoodStick && TreeRegistry.trees[stack.getItemDamage()] != null) {
				//TODO: stuffs
				return true;
			}
		}
		dropSpitStack();
		return false;
	}
	
	public void setSpitStack(ItemStack s, RecipeCampfire r) {
		recipe = r;
		spitStack = s;
	}

	public void dropSpitStack() {
		EntityItem entity = new EntityItem(worldObj, xCoord, yCoord, zCoord, spitStack.copy());
		worldObj.spawnEntityInWorld(entity);
		recipe = null;
		spitStack = null;
	}
	
	public boolean isBurning() {
		return hasFire && canBurn();
	}

	public boolean canBurn() {
		return burnTime > 0;
	}
	
	public boolean hasRecipe() {
		return recipe != null && spitStack != null;
	}
		
	public boolean isCooking() {
		return isBurning() && hasRecipe() && cookTime > 0;
	}
	
	public void countLogs() {
		logCount = 0;
		for(int i = 0; i < logs.length; i++) {
			logCount += logs[i];
		}
	}
	
	public int getLogCount() {
		return Math.min(logCount, 8);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}
}
