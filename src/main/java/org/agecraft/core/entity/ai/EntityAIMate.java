package org.agecraft.core.entity.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;

import org.agecraft.core.entity.animals.EntityAnimal;

public class EntityAIMate extends EntityAIBase {

	private EntityAnimal animal;
	private EntityAnimal target;
	private double moveSpeed;
	private int spawnBabyDelay;

	public EntityAIMate(EntityAnimal animal, double moveSpeed) {
		this.animal = animal;
		this.moveSpeed = moveSpeed;
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		if(animal.isInLove()) {
			target = getNearbyMate();
			return target != null;
		}
		return false;
	}

	@Override
	public boolean continueExecuting() {
		return target.isEntityAlive() && target.isInLove() && spawnBabyDelay < 60;
	}

	@Override
	public void resetTask() {
		target = null;
		spawnBabyDelay = 0;
	}

	@Override
	public void updateTask() {
		animal.getLookHelper().setLookPositionWithEntity(target, 10.0F, animal.getVerticalFaceSpeed());
		animal.getNavigator().tryMoveToEntityLiving(target, moveSpeed);
		spawnBabyDelay++;
		if(spawnBabyDelay >= 60 && animal.getDistanceSqToEntity(target) < 9.0F) {
			spawnBaby();
		}
	}

	public EntityAnimal getNearbyMate() {
		List list = animal.worldObj.getEntitiesWithinAABB(animal.getClass(), animal.boundingBox.expand(8.0D, 8.0D, 8.0D));
		double distance = Double.MAX_VALUE;
		EntityAnimal mate = null;
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {
			EntityAnimal entity = (EntityAnimal) iterator.next();
			if(animal.canMateWith(entity) && animal.getDistanceSqToEntity(entity) < distance) {
				mate = entity;
				distance = animal.getDistanceSqToEntity(entity);
			}
		}
		return mate;
	}

	public void spawnBaby() {
		EntityAnimal baby = animal.createChild(target);

		if(baby != null) {
			EntityPlayer player = animal.getBreeder();
			if(player == null && target.getBreeder() != null) {
				player = target.getBreeder();
			}
			if(player != null) {
				player.triggerAchievement(StatList.field_151186_x);
			}
			animal.setGrowingAge(6000);
			target.setGrowingAge(6000);
			animal.resetInLove();
			target.resetInLove();
			baby.setGrowingAge(-24000);
			baby.setLocationAndAngles(animal.posX, animal.posY, animal.posZ, 0.0F, 0.0F);
			animal.worldObj.spawnEntityInWorld(baby);
			Random random = animal.getRNG();

			for(int i = 0; i < 7; ++i) {
				double x = random.nextGaussian() * 0.02D;
				double y = random.nextGaussian() * 0.02D;
				double z = random.nextGaussian() * 0.02D;
				animal.worldObj.spawnParticle("heart", animal.posX + (double) (random.nextFloat() * animal.width * 2.0F) - (double) animal.width, animal.posY + 0.5D + (double) (random.nextFloat() * animal.height), animal.posZ + (double) (random.nextFloat() * animal.width * 2.0F) - (double) animal.width, x, y, z);
			}
			if(animal.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
				animal.worldObj.spawnEntityInWorld(new EntityXPOrb(animal.worldObj, animal.posX, animal.posY, animal.posZ, random.nextInt(7) + 1));
			}
		}
	}
}
