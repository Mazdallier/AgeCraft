package org.agecraft.core.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.core.blocks.IBlockFalling;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFallingBlock extends Entity {

	private Block block;
	private int meta = 0;
	public int time;
	public boolean dropItem;
	public boolean broken;
	public boolean hurtEntities;
	public int fallHurtMax;
	public float fallHurtAmount;
	private NBTTagCompound tileEntityData = null;

	public EntityFallingBlock(World world) {
		super(world);
		this.dropItem = true;
		this.fallHurtMax = 40;
		this.fallHurtAmount = 2.0F;
	}

	public EntityFallingBlock(World world, double x, double y, double z, Block block) {
		this(world, x, y, z, block, 0);
	}

	public EntityFallingBlock(World world, double x, double y, double z, Block block, int meta) {
		super(world);
		this.dropItem = true;
		this.fallHurtMax = 40;
		this.fallHurtAmount = 2.0F;
		this.block = block;
		this.meta = meta;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = height / 2.0F;
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		ItemStack stack = new ItemStack(block, 1, meta);
		if(tileEntityData != null) {
			stack.setTagCompound(tileEntityData);
		}
		dataWatcher.updateObject(5, stack);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObjectByDataType(5, 5);
	}

	public void setNBT(NBTTagCompound nbt) {
		tileEntityData = nbt;
		ItemStack stack = dataWatcher.getWatchableObjectItemStack(5);
		stack.setTagCompound(nbt);
		dataWatcher.updateObject(5, stack);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void onUpdate() {
		if(block != null && block.getMaterial() == Material.air) {
			setDead();
		} else {
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			time++;
			motionY -= 0.03999999910593033D;
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.9800000190734863D;
			motionY *= 0.9800000190734863D;
			motionZ *= 0.9800000190734863D;
			if(!worldObj.isRemote) {
				int x = MathHelper.floor_double(posX);
				int y = MathHelper.floor_double(posY);
				int z = MathHelper.floor_double(posZ);
				if(time == 1) {
					if(worldObj.getBlock(x, y, z) != block) {
						setDead();
						return;
					}
					worldObj.setBlockToAir(x, y, z);
				}
				if(onGround) {
					motionX *= 0.699999988079071D;
					motionZ *= 0.699999988079071D;
					motionY *= -0.5D;
					if(worldObj.getBlock(x, y, z) != Blocks.piston_extension) {
						setDead();
						if(!broken && worldObj.canPlaceEntityOnSide(block, x, y, z, true, 1, (Entity) null, (ItemStack) null) && !BlockFalling.func_149831_e(worldObj, x, y - 1, z) && worldObj.setBlock(x, y, z, block, meta, 3)) {
							if(block instanceof BlockFalling) {
								((BlockFalling) block).func_149828_a(worldObj, x, y, z, meta);
							} else if(block instanceof IBlockFalling) {
								((IBlockFalling) block).onFallEnded(worldObj, x, y, z, meta);
							}
							if(tileEntityData != null && block instanceof ITileEntityProvider) {
								TileEntity tile = worldObj.getTileEntity(x, y, z);
								if(tile != null) {
									NBTTagCompound nbt = new NBTTagCompound();
									tile.writeToNBT(nbt);
									Iterator iterator = tileEntityData.func_150296_c().iterator();
									while(iterator.hasNext()) {
										String s = (String) iterator.next();
										NBTBase tag = tileEntityData.getTag(s);
										if(!s.equals("x") && !s.equals("y") && !s.equals("z")) {
											nbt.setTag(s, tag.copy());
										}
									}
									tile.readFromNBT(nbt);
									tile.markDirty();
								}
							}
						} else if(dropItem && !broken) {
							entityDropItem(new ItemStack(block, 1, block.damageDropped(meta)), 0.0F);
						}
					}
				} else if(time > 100 && !worldObj.isRemote && (y < 1 || y > 256) || time > 600) {
					if(dropItem) {
						entityDropItem(new ItemStack(block, 1, block.damageDropped(meta)), 0.0F);
					}
					setDead();
				}
			}
		}
	}

	@Override
	protected void fall(float fallDistance) {
		if(hurtEntities) {
			int damagePercentage = MathHelper.ceiling_float_int(fallDistance - 1.0F);
			if(damagePercentage > 0) {
				ArrayList entities = new ArrayList(worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox));
				boolean isVanillaAnvil = block == Blocks.anvil;
				DamageSource damageSource = isVanillaAnvil ? DamageSource.anvil : DamageSource.fallingBlock;
				if(block instanceof IBlockFalling) {
					damageSource = ((IBlockFalling) block).getFallDamageSource(this);
				}
				Iterator iterator = entities.iterator();
				while(iterator.hasNext()) {
					Entity entity = (Entity) iterator.next();
					entity.attackEntityFrom(damageSource, (float) Math.min(MathHelper.floor_float((float) damagePercentage * fallHurtAmount), fallHurtMax));
				}
				if(block instanceof IBlockFalling) {
					((IBlockFalling) block).onFallDamage(this, fallDistance, damagePercentage);
				}
				if(isVanillaAnvil && (double) rand.nextFloat() < 0.05000000074505806D + (double) damagePercentage * 0.05D) {
					int j = meta >> 2;
					int k = meta & 3;
					++j;
					if(j > 2) {
						broken = true;
					} else {
						meta = k | j << 2;
					}
				}
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("Tile", (byte) Block.getIdFromBlock(block));
		nbt.setInteger("TileID", Block.getIdFromBlock(block));
		nbt.setByte("Data", (byte) meta);
		nbt.setByte("Time", (byte) time);
		nbt.setBoolean("DropItem", dropItem);
		nbt.setBoolean("HurtEntities", hurtEntities);
		nbt.setFloat("FallHurtAmount", fallHurtAmount);
		nbt.setInteger("FallHurtMax", fallHurtMax);
		if(tileEntityData != null) {
			nbt.setTag("TileEntityData", tileEntityData);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("TileID", 99)) {
			block = Block.getBlockById(nbt.getInteger("TileID"));
		} else {
			block = Block.getBlockById(nbt.getByte("Tile") & 255);
		}
		meta = nbt.getByte("Data") & 255;
		time = nbt.getByte("Time") & 255;
		if(nbt.hasKey("HurtEntities", 99)) {
			hurtEntities = nbt.getBoolean("HurtEntities");
			fallHurtAmount = nbt.getFloat("FallHurtAmount");
			fallHurtMax = nbt.getInteger("FallHurtMax");
		} else if(block == Blocks.anvil) {
			hurtEntities = true;
		}
		if(nbt.hasKey("DropItem", 99)) {
			dropItem = nbt.getBoolean("DropItem");
		}
		if(nbt.hasKey("TileEntityData", 10)) {
			tileEntityData = nbt.getCompoundTag("TileEntityData");
		}
		if(block.getMaterial() == Material.air) {
			block = Blocks.sand;
		}
	}

	public void setHurtEntities(boolean hurtEntities) {
		this.hurtEntities = hurtEntities;
	}

	@Override
	public void addEntityCrashInfo(CrashReportCategory crash) {
		super.addEntityCrashInfo(crash);
		crash.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
		crash.addCrashSection("Immitating block data", Integer.valueOf(meta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire() {
		return false;
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public NBTTagCompound getNBT() {
		return tileEntityData;
	}
	
	@SideOnly(Side.CLIENT)
	public Block getBlockClient() {
		return dataWatcher.getWatchableObjectItemStack(5) == null ? Blocks.air : Block.getBlockFromItem(dataWatcher.getWatchableObjectItemStack(5).getItem());
	}
	
	@SideOnly(Side.CLIENT)
	public int getMetaClient() {
		return dataWatcher.getWatchableObjectItemStack(5) == null ? 0 : dataWatcher.getWatchableObjectItemStack(5).getItemDamage();
	}
	
	@SideOnly(Side.CLIENT)
	public NBTTagCompound getNBTClient() {
		return dataWatcher.getWatchableObjectItemStack(5) == null ? null : dataWatcher.getWatchableObjectItemStack(5).getTagCompound();
	}
}
