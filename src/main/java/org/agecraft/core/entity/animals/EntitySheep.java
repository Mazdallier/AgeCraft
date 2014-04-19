package org.agecraft.core.entity.animals;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import org.agecraft.core.dna.DNA;
import org.agecraft.core.dna.structure.DNAObject;
import org.agecraft.core.entity.DNAObjectAnimal;
import org.agecraft.core.entity.ai.EntityAIFollowParent;
import org.agecraft.core.entity.ai.EntityAIMate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySheep extends EntityAnimal implements IShearable {

	public static final float[][] colors = new float[][]{{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};

	public static DNAObjectAnimal dna = new DNAObjectAnimal(103, "sheep", EntitySheep.class);

	private final InventoryCrafting inventory = new InventoryCrafting(new Container() {

		public boolean canInteractWith(EntityPlayer player) {
			return false;
		}
	}, 2, 1);

	public int sheepTimer;
	public EntityAIEatGrass taskEatGrass = new EntityAIEatGrass(this);

	public EntitySheep(World world) {
		super(world);
		setSize(0.9F, 1.3F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.wheat, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		tasks.addTask(5, taskEatGrass);
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		inventory.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
		inventory.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
	}

	@Override
	protected void updateAITasks() {
		sheepTimer = taskEatGrass.func_151499_f();
		super.updateAITasks();
	}

	@Override
	public void onLivingUpdate() {
		if(worldObj.isRemote) {
			sheepTimer = Math.max(0, sheepTimer - 1);
		}
		super.onLivingUpdate();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte health) {
		if(health == 10) {
			sheepTimer = 40;
		} else {
			super.handleHealthUpdate(health);
		}
	}

	@SideOnly(Side.CLIENT)
	public float func_70894_j(float f) {
		return sheepTimer <= 0 ? 0.0F : (sheepTimer >= 4 && sheepTimer <= 36 ? 1.0F : (sheepTimer < 4 ? ((float) sheepTimer - f) / 4.0F : -((float) (sheepTimer - 40) - f) / 4.0F));
	}

	@SideOnly(Side.CLIENT)
	public float func_70890_k(float f) {
		if(sheepTimer > 4 && sheepTimer <= 36) {
			float val = ((float) (sheepTimer - 4) - f) / 32.0F;
			return ((float) Math.PI / 5F) + ((float) Math.PI * 7F / 100F) * MathHelper.sin(val * 28.7F);
		} else {
			return sheepTimer > 0 ? ((float) Math.PI / 5F) : rotationPitch / (180F / (float) Math.PI);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Sheared", getSheared());
		nbt.setByte("Color", (byte) getFleeceColor());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSheared(nbt.getBoolean("Sheared"));
		setFleeceColor(nbt.getByte("Color"));
	}

	public int getFleeceColor() {
		return dataWatcher.getWatchableObjectByte(16) & 15;
	}

	public void setFleeceColor(int color) {
		byte value = dataWatcher.getWatchableObjectByte(16);
		dataWatcher.updateObject(16, Byte.valueOf((byte) (value & 240 | color & 15)));
	}

	public boolean getSheared() {
		return (dataWatcher.getWatchableObjectByte(16) & 16) != 0;
	}

	public void setSheared(boolean sheared) {
		byte value = dataWatcher.getWatchableObjectByte(16);
		if(sheared) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (value | 16)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (value & -17)));
		}
	}

	public static int getRandomFleeceColor(Random random) {
		int i = random.nextInt(100);
		return i < 5 ? 15 : (i < 10 ? 7 : (i < 15 ? 8 : (i < 18 ? 12 : (random.nextInt(500) == 0 ? 6 : 0))));
	}

	@Override
	public void eatGrassBonus() {
		setSheared(false);
		if(isChild()) {
			addGrowth(60);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setFleeceColor(getRandomFleeceColor(worldObj.rand));
		return data;
	}

	public int getChildFleeceColor(EntityAnimal parent1, EntityAnimal parent2) {
		int i = getInvertedFleeceColor(parent1);
		int j = getInvertedFleeceColor(parent2);
		inventory.getStackInSlot(0).setItemDamage(i);
		inventory.getStackInSlot(1).setItemDamage(j);
		ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(inventory, ((EntitySheep) parent1).worldObj);
		int k;
		if(itemstack != null && itemstack.getItem() == Items.dye) {
			k = itemstack.getItemDamage();
		} else {
			k = worldObj.rand.nextBoolean() ? i : j;
		}
		return k;
	}

	private int getInvertedFleeceColor(EntityAnimal animal) {
		return 15 - ((EntitySheep) animal).getFleeceColor();
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return !getSheared() && !isChild();
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		setSheared(true);
		int i = 1 + rand.nextInt(3);
		for(int j = 0; j < i; j++) {
			ret.add(new ItemStack(Blocks.wool, 1, getFleeceColor()));
		}
		playSound("mob.sheep.shear", 1.0F, 1.0F);
		return ret;
	}

	@Override
	protected String getLivingSound() {
		return "mob.sheep.say";
	}

	@Override
	protected String getHurtSound() {
		return "mob.sheep.say";
	}

	@Override
	protected String getDeathSound() {
		return "mob.sheep.say";
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		playSound("mob.sheep.step", 0.15F, 1.0F);
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
		return new EntitySheep(worldObj);
	}
	
	@Override
	public EntityAnimal createChild(EntityAnimal parent) {
		EntitySheep child = (EntitySheep) createChild();
		child.setDNA(DNA.reproduce(getDNA(), parent.getDNA()));
        child.setFleeceColor(15 - getChildFleeceColor(this, parent));
		return child;
	}

	@Override
	public DNAObject getDNAObject() {
		return dna;
	}
}
