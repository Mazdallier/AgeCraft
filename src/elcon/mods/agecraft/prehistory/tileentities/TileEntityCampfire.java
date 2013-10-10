package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire;
import elcon.mods.agecraft.prehistory.recipes.RecipesCampfire.RecipeCampfire;

public class TileEntityCampfire extends TileEntity {

	public int tick = 0;
	
	public int burnTime = 0;
	public int cookTime = 0;
	public int cookTimeStart = 0;
	public boolean hasFire = false;
	
	public int[] logs = new int[TreeRegistry.trees.length];
	public int logCount = 0;
	public int logBurnTime = 0;
	public int currentLogIndex = -1;
	
	public RecipeCampfire recipe;
	public ItemStack spitStack;
	
	public byte frameDirection = -1;
	public byte frameStage = 0;
	public int frameType = 0;
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(200);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			dos.writeInt(burnTime);
			dos.writeBoolean(hasFire);
			
			dos.writeInt(logCount);
			dos.writeInt(currentLogIndex);
			
			dos.writeByte(frameDirection);
			dos.writeByte(frameStage);
			dos.writeInt(frameType);
			
			dos.writeBoolean(spitStack != null);
			if(spitStack != null) {
				dos.writeInt(spitStack.itemID);
				dos.writeInt(spitStack.getItemDamage());
				
				dos.writeBoolean(spitStack.hasTagCompound());
				if(spitStack.hasTagCompound()) {
					try {
						NBTBase.writeNamedTag(spitStack.stackTagCompound, dos);
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
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(isBurning()) {				
				//burnTime--;
				if(burnTime <= 0) {
					burnTime = 0;
					cookTime = 0;
					hasFire = false;
					dropSpitStack();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
				if(isCooking()) {
					cookTime--;
					if(cookTime == cookTimeStart - recipe.cookTime) {
						spitStack = recipe.cooked.copy();
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					} else if(cookTime <= 0) {
						cookTime = 0;
						spitStack = recipe.burned.copy();
						dropSpitStack();
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
				if(currentLogIndex != -1) {
					//logBurnTime--;
					if(logBurnTime <= 0) {
						logBurnTime = 0;
						logs[currentLogIndex]--;
						currentLogIndex = -1;
						countLogs();
						if(logCount > 0) {
							int best = 0;
							int bestIndex = -1;
							for(int i = 0; i < logs.length; i++) {
								if(logs[i] > best) {
									best = logs[i];
									bestIndex = i;
								}
							}
							currentLogIndex = bestIndex;
							logBurnTime = RecipesCampfire.getFuelValue(new ItemStack(Trees.log));
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}
				}
			}
		} else {
			tick = ++tick % 20;
			if(isBurning() && tick % 8 == 0) {
				double d = xCoord + 0.5F;
			    double d1 = yCoord + 0.37F;
			    double d2 = zCoord + 0.5F;
			    worldObj.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("flame", d + 0.1D, d1, d2, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("flame", d, d1, d2 + 0.1D, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("flame", d, d1, d2 - 0.1D, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("flame", d - 0.1D, d1, d2, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("smoke", d + 0.1D, d1, d2 + 0.1D, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("smoke", d - 0.1D, d1, d2 - 0.1D, 0.0D, 0.0D, 0.0D);
			    worldObj.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public boolean onBlockActivated(float rotationYaw, ItemStack stack) {
		if(stack != null) {
			if(RecipesCampfire.getFuelValue(stack) != 0) {
				if(stack.itemID == Trees.log.blockID) {
					logs[stack.getItemDamage()]++;
					countLogs();
					if(currentLogIndex == -1) {
						if(logCount > 0) {
							int best = 0;
							int bestIndex = -1;
							for(int i = 0; i < logs.length; i++) {
								if(logs[i] > best) {
									best = logs[i];
									bestIndex = i;
								}
							}
							currentLogIndex = bestIndex;
							logBurnTime = RecipesCampfire.getFuelValue(new ItemStack(Trees.log));
						}
					}
				}
				burnTime += RecipesCampfire.getFuelValue(stack);
				stack.stackSize--;
				if(stack.stackSize <= 0) {
					stack = null;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return true;
			} else if(RecipesCampfire.getRecipe(stack) != null) {
				if(hasFrame() && isBurning() && !isCooking()) {
					ItemStack s = stack.copy();
					s.stackSize = 1;
					setSpitStack(s, RecipesCampfire.getRecipe(stack));
					stack.stackSize--;
					if(stack.stackSize <= 0) {
						stack = null;
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}				
			} else if(stack.itemID == Trees.stick.itemID && TreeRegistry.trees[stack.getItemDamage()] != null) {
				if(frameStage < 3) {
					if(frameDirection == -1) {
						frameDirection = (byte) (MathHelper.floor_double((double)(rotationYaw * 4.0F / 360.0F) + 2.5D) & 1);
					}
					frameStage++;
					frameType = stack.getItemDamage();
					stack.stackSize--;
					if(stack.stackSize <= 0) {
						stack = null;
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}				
			} else if(stack.itemID == Item.flintAndSteel.itemID) {
				if(canBurn()) {
					hasFire = true;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return true;
			}
		}
		dropSpitStack();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return false;
	}
	
	public void setSpitStack(ItemStack s, RecipeCampfire r) {
		recipe = r;
		spitStack = s;
		cookTimeStart = recipe.cookTime + recipe.burnTime;
		cookTime = cookTimeStart;
	}

	public void dropSpitStack() {
		if(spitStack != null) {
			EntityItem entity = new EntityItem(worldObj, xCoord, yCoord, zCoord, spitStack.copy());
			worldObj.spawnEntityInWorld(entity);
		}
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
	
	public boolean hasFrame() {
		return frameStage >= 3;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		burnTime = nbt.getInteger("BurnTime");
		cookTime = nbt.getInteger("CookTime");
		cookTimeStart = nbt.getInteger("CookTimeStart");
	
		hasFire = nbt.getBoolean("HasFire");
		
		logs = nbt.getIntArray("Logs");
		countLogs();
		logBurnTime = nbt.getInteger("LogBurnTime");
		currentLogIndex = nbt.getInteger("CurrentLogIndex");
		
		if(nbt.hasKey("SpitStack")) {
			spitStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("SpitStack"));
			recipe = RecipeCampfire.readFromNBT(nbt.getCompoundTag("Recipe"));
		}
		
		frameDirection = nbt.getByte("FrameDirection");
		frameStage = nbt.getByte("FrameStage");
		frameType = nbt.getInteger("FrameType");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("BurnTime", burnTime);
		nbt.setInteger("CookTime", cookTime);
		nbt.setInteger("CookTimeStart", cookTimeStart);
		nbt.setBoolean("HasFire", hasFire);
		
		nbt.setIntArray("Logs", logs);
		nbt.setInteger("LogBurnTime", logBurnTime);
		nbt.setInteger("CurrentLogIndex", currentLogIndex);
		
		if(spitStack != null) {
			NBTTagCompound tag = new NBTTagCompound();
			spitStack.writeToNBT(tag);
			nbt.setTag("SpitStack", tag);
			
			tag = new NBTTagCompound();
			recipe.writeToNBT(tag);
			nbt.setTag("Recipe", tag);
		}
		
		nbt.setByte("FrameDirection", frameDirection);
		nbt.setByte("FrameStage", frameStage);
		nbt.setInteger("FrameType", frameType);
	}
}
