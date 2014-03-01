package org.agecraft.prehistory.tileentities;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.core.Trees;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.prehistory.PrehistoryAge;
import org.agecraft.prehistory.recipes.RecipesCampfire;
import org.agecraft.prehistory.recipes.RecipesCampfire.RecipeCampfire;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtilClient;
import elcon.mods.elconqore.network.EQMessageTile;
import elcon.mods.elconqore.tileentities.TileEntityExtended;

public class TileEntityPrehistoryCampfire extends TileEntityExtended {

	public static class MessageTilePrehistoryCampfire extends EQMessageTile {

		public int burnTime = 0;
		public boolean hasFire = false;
		public int logCount = 0;
		public int currentLogIndex = -1;
		
		public ItemStack spitStack;	
		
		public byte frameDirection = -1;
		public byte frameStage = 0;
		public int[] frameTypes = new int[3];
		
		public MessageTilePrehistoryCampfire() {
		}
		
		public MessageTilePrehistoryCampfire(int x, int y, int z, int burnTime, boolean hasFire, int logCount, int currentLogIndex, ItemStack spitStack, byte frameDirection, byte frameStage, int[] frameTypes) {
			super(x, y, z);
			this.burnTime = burnTime;
			this.hasFire = hasFire;
			this.logCount = logCount;
			this.currentLogIndex = currentLogIndex;
			
			this.spitStack = spitStack;
			
			this.frameDirection = frameDirection;
			this.frameStage = frameStage;
			this.frameTypes = frameTypes;
		}
		
		@Override
		public void encodeTo(ByteBuf target) {
			super.encodeTo(target);
			target.writeInt(burnTime);
			target.writeBoolean(hasFire);
			target.writeShort(logCount);
			target.writeShort(currentLogIndex);
		
			writeItemStack(target, spitStack);
			
			target.writeByte(frameDirection);
			target.writeByte(frameStage);
			target.writeShort(frameTypes[0]);
			target.writeShort(frameTypes[1]);
			target.writeShort(frameTypes[2]);
		}
		
		@Override
		public void decodeFrom(ByteBuf source) {
			super.decodeFrom(source);
			burnTime = source.readInt();
			hasFire = source.readBoolean();
			logCount = source.readShort();
			currentLogIndex = source.readShort();
			
			spitStack = readItemStack(source);
			
			frameDirection = source.readByte();
			frameStage = source.readByte();
			frameTypes = new int[3];
			frameTypes[0] = source.readShort();
			frameTypes[1] = source.readShort();
			frameTypes[2] = source.readShort();
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void handle() {
			World world = EQUtilClient.getWorld();
			TileEntityPrehistoryCampfire tile = (TileEntityPrehistoryCampfire) world.getTileEntity(x, y, z);
			if(tile == null) {
				tile = new TileEntityPrehistoryCampfire();
				world.setTileEntity(x, y, z, tile);
			}
			tile.burnTime = burnTime;
			tile.hasFire = hasFire;
			tile.logCount = logCount;
			tile.currentLogIndex = currentLogIndex;
			
			tile.spitStack = spitStack;
			
			tile.frameDirection = frameDirection;
			tile.frameStage = frameStage;
			tile.frameTypes = frameTypes;
			world.markBlockForUpdate(x, y, z);
		}
	}
	
	public static final int MAX_LOGS = 8;
	
	public int tick = 0;
	
	public int burnTime = 0;
	public int cookTime = 0;
	public int cookTimeStart = 0;
	public boolean hasFire = false;
	
	public int[] logs = new int[TreeRegistry.instance.getAll().length];
	public int logCount = 0;
	public int logBurnTime = 0;
	public int currentLogIndex = -1;
	
	public RecipeCampfire recipe;
	public ItemStack spitStack;
	
	public byte frameDirection = -1;
	public byte frameStage = 0;
	public int[] frameTypes = new int[3];
	
	@Override
	public Packet getDescriptionPacket() {		
		return PrehistoryAge.instance.packetHandler.getPacketToClient(new MessageTilePrehistoryCampfire(xCoord, yCoord, zCoord, burnTime, hasFire, logCount, currentLogIndex, spitStack, frameDirection, frameStage, frameTypes));
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
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					if(frameStage == 0) {	
						worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					}
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
					logBurnTime--;
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
	
	public boolean addLogs(ItemStack stack) {
		if(RecipesCampfire.getFuelValue(stack) != 0) {
			if(Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(Trees.log)) {
				if(logCount >= MAX_LOGS) {
					return true;
				}
				logs[stack.getItemDamage()] += stack.stackSize;
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
			burnTime += RecipesCampfire.getFuelValue(stack) * stack.stackSize;
			stack = null;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}

	public boolean onBlockActivated(float rotationYaw, boolean isCreativeMode, ItemStack stack) {
		if(stack != null) {
			if(RecipesCampfire.getFuelValue(stack) != 0) {
				if(Item.getIdFromItem(stack.getItem()) == Block.getIdFromBlock(Trees.log)) {
					if(logCount >= MAX_LOGS) {
						return true;
					}
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
				if(!isCreativeMode) {
					stack.stackSize--;
					if(stack.stackSize <= 0) {
						stack = null;
					}
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return true;
			} else if(RecipesCampfire.getRecipe(stack) != null) {
				if(hasFrame() && isBurning() && !isCooking()) {
					ItemStack s = stack.copy();
					s.stackSize = 1;
					setSpitStack(s, RecipesCampfire.getRecipe(stack));
					if(!isCreativeMode) {
						stack.stackSize--;
						if(stack.stackSize <= 0) {
							stack = null;
						}
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}				
			} else if(stack.getItem() == Trees.stick && TreeRegistry.instance.get(stack.getItemDamage()) != null) {
				if(frameStage < 3) {
					if(frameDirection == -1) {
						frameDirection = (byte) (MathHelper.floor_double((double)(rotationYaw * 4.0F / 360.0F) + 2.5D) & 1);
					}
					frameTypes[frameStage] = stack.getItemDamage();
					frameStage++;
					if(!isCreativeMode) {
						stack.stackSize--;
						if(stack.stackSize <= 0) {
							stack = null;
						}
					}
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}				
			} else if(stack.getItem() == Items.flint_and_steel) {
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
		frameTypes = nbt.getIntArray("FrameType");
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
		nbt.setIntArray("FrameType", frameTypes);
	}
}
