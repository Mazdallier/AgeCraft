package org.agecraft.core.entity.ai;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;

import org.agecraft.core.entity.animals.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {

	private EntityAnimal child;
	private EntityAnimal parent;
	private double moveSpeed;
	private int field2;

	public EntityAIFollowParent(EntityAnimal child, double moveSpeed) {
		this.child = child;
		this.moveSpeed = moveSpeed;
	}

	@Override
	public boolean shouldExecute() {
		if(child.getGrowingAge() < 0) {
			List list = child.worldObj.getEntitiesWithinAABB(child.getClass(), child.boundingBox.expand(8.0D, 4.0D, 8.0D));
			EntityAnimal animal = null;
			double distance = Double.MAX_VALUE;
			Iterator iterator = list.iterator();
			while(iterator.hasNext()) {
				EntityAnimal entity = (EntityAnimal) iterator.next();
				if(entity.getGrowingAge() >= 0) {
					double d1 = child.getDistanceSqToEntity(entity);
					if(d1 <= distance) {
						distance = d1;
						animal = entity;
					}
				}
			}
			if(animal == null) {
				return false;
			} else if(distance < 9.0D) {
				return false;
			} else {
				parent = animal;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean continueExecuting() {
		if(parent.isEntityAlive()) {
			double distance = child.getDistanceSqToEntity(parent);
			return distance >= 9.0D && distance <= 256.0D;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		field2 = 0;
	}

	@Override
	public void resetTask() {
		parent = null;
	}

	@Override
	public void updateTask() {
		field2--;
		if(field2 <= 0) {
			field2 = 10;
			child.getNavigator().tryMoveToEntityLiving(parent, moveSpeed);
		}
	}
}
