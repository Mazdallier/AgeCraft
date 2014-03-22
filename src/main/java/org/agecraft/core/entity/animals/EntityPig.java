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
import org.agecraft.core.items.farming.ItemFood;

public class EntityPig extends EntityAnimal {

	public static DNAObjectAnimal pigDNA = new DNAObjectAnimal(100, "pig", EntityPig.class);
	
	public EntityPig(World world) {
		super(world);
		setSize(0.9F, 0.9F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		//tasks.addTask(2, aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
		tasks.addTask(3, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
        tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
        tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}
	
	@Override
	protected String getLivingSound() {
		return "mob.pig.say";
	}
	
	@Override
	protected String getHurtSound() {
		return "mob.pig.say";
	}
	
	@Override
	protected String getDeathSound() {
		return "mob.pig.death";
	}
	
	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		playSound("mob.pig.step", 0.15F, 1.0F);
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return ItemFood.isFood(stack, "carrot");
	}
	
	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public EntityAnimal createChild() {
		return new EntityPig(worldObj);
	}

	@Override
	public DNAObject getDNAObject() {
		return pigDNA;
	}
}
