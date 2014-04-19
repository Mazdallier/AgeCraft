package org.agecraft.core.entity.animals;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.agecraft.core.dna.structure.DNAObject;
import org.agecraft.core.entity.DNAObjectAnimal;
import org.agecraft.core.entity.ai.EntityAIFollowParent;
import org.agecraft.core.entity.ai.EntityAIMate;

public class EntityChicken extends EntityAnimal {

	public static DNAObjectAnimal dna = new DNAObjectAnimal(102, "chicken", EntityChicken.class);

	public float value;
	public float destPos;
	public float oldDestPos;
	public float oldValue;
	public float decrValue = 1.0F;
	public int timeUntilNextEgg;

	public EntityChicken(World world) {
		super(world);
		setSize(0.3F, 0.7F);
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		oldValue = value;
		oldDestPos = destPos;
		destPos = (float) ((double) destPos + (double) (onGround ? -1 : 4) * 0.3D);
		if(destPos < 0.0F) {
			destPos = 0.0F;
		}
		if(destPos > 1.0F) {
			destPos = 1.0F;
		}
		if(!onGround && decrValue < 1.0F) {
			decrValue = 1.0F;
		}
		decrValue = (float) ((double) decrValue * 0.9D);
		if(!onGround && motionY < 0.0D) {
			motionY *= 0.6D;
		}
		value += decrValue * 2.0F;
		if(!isChild() && !worldObj.isRemote && --timeUntilNextEgg <= 0) {
			playSound("mob.chicken.plop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			dropItem(Items.egg, 1);
			timeUntilNextEgg = rand.nextInt(6000) + 6000;
		}
	}

	@Override
	protected void fall(float damage) {

	}

	@Override
	protected String getLivingSound() {
		return "mob.chicken.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.chicken.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.chicken.hurt";
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		playSound("mob.chicken.step", 0.15F, 1.0F);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.wheat_seeds;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public EntityAnimal createChild() {
		return new EntityChicken(worldObj);
	}

	@Override
	public DNAObject getDNAObject() {
		return dna;
	}
}
