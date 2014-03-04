package org.agecraft.core.blocks;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.agecraft.core.entity.EntityFallingBlock;

public interface IBlockFalling {

	public void fall(World world, int x, int y, int z);

	public void addFallData(World world, int x, int y, int z, EntityFallingBlock entity);

	public boolean canFall(World world, int x, int y, int z);

	public DamageSource getFallDamageSource(EntityFallingBlock entity);

	public void onFallDamage(EntityFallingBlock entity, float fallDistance, int damagePercentage);
	
	public void onFallEnded(World world, int x, int y, int z, int meta);
}
