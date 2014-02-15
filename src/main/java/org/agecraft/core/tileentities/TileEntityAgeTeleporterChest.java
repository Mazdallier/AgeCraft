package org.agecraft.core.tileentities;

import java.util.Iterator;
import java.util.List;

import org.agecraft.core.AgeCraftCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAgeTeleporterChest extends TileEntity implements IInventory {
	
	public ItemStack[] chestContents = new ItemStack[36];

	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;
	private int ticksSinceSync;

	public int getSizeInventory() {
		return 27;
	}

	public ItemStack getStackInSlot(int par1) {
		return chestContents[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if(chestContents[par1] != null) {
			ItemStack var3;

			if(chestContents[par1].stackSize <= par2) {
				var3 = chestContents[par1];
				chestContents[par1] = null;
				onInventoryChanged();
				return var3;
			} else {
				var3 = chestContents[par1].splitStack(par2);

				if(chestContents[par1].stackSize == 0) {
					chestContents[par1] = null;
				}

				onInventoryChanged();
				return var3;
			}
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if(chestContents[par1] != null) {
			ItemStack var2 = chestContents[par1];
			chestContents[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		chestContents[par1] = par2ItemStack;

		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit()) {
			par2ItemStack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged();
	}

	public String getInvName() {
		return "container.chest";
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		chestContents = new ItemStack[getSizeInventory()];

		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if(var5 >= 0 && var5 < chestContents.length) {
				chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for(int var3 = 0; var3 < chestContents.length; ++var3) {
			if(chestContents[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				chestContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
	}

	public void updateEntity() {
		super.updateEntity();
		++ticksSinceSync;
		float var1;

		if(!worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numUsingPlayers = 0;
			var1 = 5.0F;
			List var2 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double) ((float) xCoord - var1), (double) ((float) yCoord - var1), (double) ((float) zCoord - var1), (double) ((float) (xCoord + 1) + var1), (double) ((float) (yCoord + 1) + var1), (double) ((float) (zCoord + 1) + var1)));
			Iterator var3 = var2.iterator();

			while(var3.hasNext()) {
				EntityPlayer var4 = (EntityPlayer) var3.next();

				if(var4.openContainer instanceof ContainerChest) {
					IInventory var5 = ((ContainerChest) var4.openContainer).getLowerChestInventory();

					if(var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest) var5).isPartOfLargeChest(this)) {
						++numUsingPlayers;
					}
				}
			}
		}

		prevLidAngle = lidAngle;
		var1 = 0.1F;
		double var11;

		if(numUsingPlayers > 0 && lidAngle == 0.0F) {
			double var8 = (double) xCoord + 0.5D;
			var11 = (double) zCoord + 0.5D;

			worldObj.playSoundEffect(var8, (double) yCoord + 0.5D, var11, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if(numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
			float var9 = lidAngle;

			if(numUsingPlayers > 0) {
				lidAngle += var1;
			} else {
				lidAngle -= var1;
			}

			if(lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}

			float var10 = 0.5F;

			if(lidAngle < var10 && var9 >= var10) {
				var11 = (double) xCoord + 0.5D;
				double var6 = (double) zCoord + 0.5D;

				worldObj.playSoundEffect(var11, (double) yCoord + 0.5D, var6, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if(lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	public boolean receiveClientEvent(int par1, int par2) {
		if(par1 == 1) {
			numUsingPlayers = par2;
			return true;
		}
		return false;
	}

	public void openChest() {
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, AgeCraftCore.ageTeleporterChest.blockID, 1, numUsingPlayers);
	}

	public void closeChest() {
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, AgeCraftCore.ageTeleporterChest.blockID, 1, numUsingPlayers);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
}
