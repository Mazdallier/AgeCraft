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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.agecraft.core.Farming;
import org.agecraft.core.dna.structure.DNAObject;
import org.agecraft.core.entity.DNAObjectAnimal;
import org.agecraft.core.entity.ai.EntityAIFollowParent;
import org.agecraft.core.entity.ai.EntityAIMate;
import org.agecraft.core.items.ItemBucket;

public class EntityCow extends EntityAnimal {

	public static DNAObjectAnimal dna = new DNAObjectAnimal(101, "cow", EntityCow.class);

	public EntityCow(World world) {
		super(world);
		setSize(0.9F, 1.3F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.wheat, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
	}

	@Override
	protected String getLivingSound() {
		return "mob.cow.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.cow.hurt";
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		playSound("mob.pig.step", 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public boolean interact(EntityPlayer player) {
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null && !player.capabilities.isCreativeMode) {
			if(stack.getItem() == Items.bucket) {
				if(stack.stackSize-- == 1) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.milk_bucket));
				} else if(!player.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
				}
			} else if(stack.getItem() instanceof ItemBucket) {
				ItemBucket bucket = (ItemBucket) stack.getItem();
				if(!bucket.hasFluid(stack) || bucket.getFluid(stack).getFluid() == Farming.milk) {
					bucket.fill(stack, new FluidStack(Farming.milk, FluidContainerRegistry.BUCKET_VOLUME), true);
				}
			}
			return true;
		} else {
			return super.interact(player);
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.wheat;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public EntityAnimal createChild() {
		return new EntityCow(worldObj);
	}

	@Override
	public DNAObject getDNAObject() {
		return dna;
	}
}
