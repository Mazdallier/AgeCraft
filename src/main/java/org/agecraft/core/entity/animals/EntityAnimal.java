package org.agecraft.core.entity.animals;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.agecraft.core.dna.DNA;
import org.agecraft.core.entity.EntityDNA;
import org.agecraft.core.entity.EntityDrop;
import org.agecraft.core.registry.AnimalRegistry;
import org.agecraft.core.registry.AnimalRegistry.Animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityAnimal extends EntityDNA implements IAnimals {

	private float oldWidth = -1.0F;
	private float oldHeight;
	private int inLove;
	private int breeding;
	private EntityPlayer breeder;

	public EntityAnimal(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(12, new Integer(0));
	}

	@Override
	protected void updateAITick() {
		if(getGrowingAge() != 0) {
			inLove = 0;
		}
		super.updateAITick();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(worldObj.isRemote) {
			setScale(isChild() ? 0.5F : 1.0F);
		} else {
			int age = getGrowingAge();
			if(age < 0) {
				age++;
				setGrowingAge(age);
			} else if(age > 0) {
				age--;
				setGrowingAge(age);
			}
		}
		if(getGrowingAge() != 0) {
			inLove = 0;
		}
		if(inLove > 0) {
			inLove--;
			String particle = "heart";
			if(inLove % 10 == 0) {
				double x = rand.nextGaussian() * 0.02D;
				double y = rand.nextGaussian() * 0.02D;
				double z = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle(particle, posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width, posY + 0.5D + (double) (rand.nextFloat() * height), posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width, x, y, z);
			}
		} else {
			breeding = 0;
		}
	}

	@Override
	protected void attackEntity(Entity entity, float damage) {
		if(entity instanceof EntityPlayer) {
			if(damage < 3.0F) {
				double deltaX = entity.posX - posX;
				double deltaZ = entity.posZ - posZ;
				rotationYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
				hasAttacked = true;
			}
			EntityPlayer player = (EntityPlayer) entity;
			if(player.getCurrentEquippedItem() == null || !isBreedingItem(player.getCurrentEquippedItem())) {
				entityToAttack = null;
			}
		} else if(entity instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal) entity;
			if(this.getGrowingAge() > 0 && animal.getGrowingAge() < 0) {
				if((double) damage < 2.5D) {
					hasAttacked = true;
				}
			} else if(inLove > 0 && animal.inLove > 0) {
				if(animal.entityToAttack == null) {
					animal.entityToAttack = this;
				}
				if(animal.entityToAttack == this && (double) damage < 3.5D) {
					animal.inLove++;
					inLove++;
					breeding++;
					if(breeding % 4 == 0) {
						worldObj.spawnParticle("heart", posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width, posY + 0.5D + (double) (rand.nextFloat() * height), posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width, 0.0D, 0.0D, 0.0D);
					}
					if(breeding == 60) {
						breed((EntityAnimal) entity);
					}
				} else {
					breeding = 0;
				}
			} else {
				breeding = 0;
				entityToAttack = null;
			}
		}
	}

	public void breed(EntityAnimal animal) {
		EntityAnimal entity = createChild(animal);
		if(entity != null) {
			if(breeder == null && animal.getBreeder() != null) {
				breeder = animal.getBreeder();
			}
			if(breeder != null) {
				breeder.triggerAchievement(StatList.field_151186_x);
			}

			this.setGrowingAge(6000);
			animal.setGrowingAge(6000);
			inLove = 0;
			breeding = 0;
			entityToAttack = null;
			animal.entityToAttack = null;
			animal.breeding = 0;
			animal.inLove = 0;
			entity.setGrowingAge(-24000);
			entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			for(int i = 0; i < 7; ++i) {
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				double d2 = this.rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle("heart", posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width, posY + 0.5D + (double) (rand.nextFloat() * height), posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width, d0, d1, d2);
			}
			worldObj.spawnEntityInWorld(entity);
		}
	}

	public EntityAnimal createChild(EntityAnimal parent) {
		EntityAnimal child = createChild();
		child.setDNA(DNA.reproduce(getDNA(), parent.getDNA()));
		return child;
	}

	public abstract EntityAnimal createChild();

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float damage) {
		if(isEntityInvulnerable()) {
			return false;
		} else {
			fleeingTick = 60;
			if(!isAIEnabled()) {
				IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
				if(attribute.getModifier(field_110179_h) == null) {
					attribute.applyModifier(field_110181_i);
				}
			}
			entityToAttack = null;
			inLove = 0;
			return super.attackEntityFrom(damageSource, damage);
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int x = MathHelper.floor_double(posX);
		int y = MathHelper.floor_double(boundingBox.minY);
		int z = MathHelper.floor_double(posZ);
		return worldObj.getBlock(x, y - 1, z).getMaterial() == Material.grass && worldObj.getFullBlockLightValue(x, y, z) > 8 && super.getCanSpawnHere();
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return worldObj.getBlock(x, y - 1, z).getMaterial() == Material.grass ? 10.0F : worldObj.getLightBrightness(x, y, z) - 0.5F;
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(fleeingTick > 0) {
			return null;
		} else {
			float range = 8.0F;
			List list;
			EntityAnimal animal;
			if(inLove > 0) {
				list = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand((double) range, (double) range, (double) range));
				for(int i = 0; i < list.size(); ++i) {
					animal = (EntityAnimal) list.get(i);
					if(animal != this && animal.inLove > 0) {
						return animal;
					}
				}
			} else if(getGrowingAge() == 0) {
				list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand((double) range, (double) range, (double) range));
				for(int i = 0; i < list.size(); ++i) {
					EntityPlayer player = (EntityPlayer) list.get(i);
					if(player.getCurrentEquippedItem() != null && isBreedingItem(player.getCurrentEquippedItem())) {
						return player;
					}
				}
			} else if(getGrowingAge() > 0) {
				list = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand((double) range, (double) range, (double) range));
				for(int i = 0; i < list.size(); ++i) {
					animal = (EntityAnimal) list.get(i);
					if(animal != this && animal.getGrowingAge() < 0) {
						return animal;
					}
				}
			}
			return null;
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null && isBreedingItem(stack) && getGrowingAge() == 0 && inLove <= 0) {
			if(!player.capabilities.isCreativeMode) {
				stack.stackSize--;
				if(stack.stackSize <= 0) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
				}
			}
			setBreeding(player);
			return true;
		} else {
			return super.interact(player);
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte health) {
		if(health == 18) {
			for(int i = 0; i < 7; ++i) {
				double x = rand.nextGaussian() * 0.02D;
				double y = rand.nextGaussian() * 0.02D;
				double z = rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle("heart", posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width, posY + 0.5D + (double) (rand.nextFloat() * height), posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width, x, y, z);
			}
		} else {
			super.handleHealthUpdate(health);
		}
	}

	@Override
	protected void dropFewItems(boolean isPlayerKill, int looting) {
		ArrayList<EntityDrop> drops = AnimalRegistry.get(getClass()).drops;
		for(EntityDrop drop : drops) {
			if(rand.nextFloat() < drop.chance) {
				if(drop.shouldBurn && !isBurning()) {
					continue;
				}
				if(drop.shouldNotBurn && isBurning()) {
					continue;
				}
				int amount = drop.min + rand.nextInt((drop.max - drop.min) + 1);
				if(drop.canLoot && looting > 0) {
					amount += rand.nextInt(looting + 1);
				}
				for(int i = 0; i < amount; i++) {
					entityDropItem(drop.stack.copy(), 0.0F);
				}
			}
		}
	}

	public boolean canMateWith(EntityAnimal animal) {
		return animal == this ? false : (animal.getClass() != getClass() ? false : isInLove() && animal.isInLove() && isMale() != animal.isMale());
	}

	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.wheat;
	}

	public void setBreeding(EntityPlayer player) {
		inLove = 600;
		breeder = player;
		entityToAttack = null;
		worldObj.setEntityState(this, (byte) 18);
	}

	public int getGrowingAge() {
		return dataWatcher.getWatchableObjectInt(12);
	}

	public void addGrowth(int growth) {
		int age = getGrowingAge();
		age += growth * 20;
		if(age > 0) {
			age = 0;
		}
		setGrowingAge(age);
	}

	public void setGrowingAge(int age) {
		dataWatcher.updateObject(12, age);
		setScale(isChild() ? 0.5F : 1.0F);
	}

	@Override
	public boolean isChild() {
		return getGrowingAge() < 0;
	}

	@Override
	public void setSize(float width, float height) {
		boolean flag = oldWidth > 0.0F;
		oldWidth = width;
		oldHeight = height;
		if(!flag) {
			setScale(1.0F);
		}
	}

	public void setScale(float scale) {
		super.setSize(oldWidth * scale, oldHeight * scale);
	}

	public EntityPlayer getBreeder() {
		return breeder;
	}

	public boolean isInLove() {
		return inLove > 0;
	}

	public void resetInLove() {
		inLove = 0;
	}

	@Override
	public int getTalkInterval() {
		return 120;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		return 1 + worldObj.rand.nextInt(3);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setGrowingAge(nbt.getInteger("Age"));
		inLove = nbt.getInteger("InLove");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Age", getGrowingAge());
		nbt.setInteger("InLove", inLove);
	}
	
	public Animal getAnimal() {
		return AnimalRegistry.get(getClass());
	}
	
	public boolean isMale() {
		return getDNA().getGene(0, 0).getActive() == 0;
	}
	
	public boolean isFemale() {
		return getDNA().getGene(0, 0).getActive() == 1;
	}
}
