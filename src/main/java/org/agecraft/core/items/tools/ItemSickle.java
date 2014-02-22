package org.agecraft.core.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSickle extends ItemTool {

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
	    int harvestLevel = getToolHarvestLevel(stack);
	    int radius = 1;
	    if(harvestLevel >= 8) {
	    	radius = 3;
	    } else if(harvestLevel >= 4) {
	    	radius = 2;
	    }	   
		for(int xx = x - radius; xx <= x + radius; xx++) {
			for(int yy = y - radius; yy <= y + radius; yy++) {
				for(int zz = z - radius; zz <= z + radius; zz++) {
					Block localBlock = world.getBlock(xx, yy, zz);
					if(localBlock != null && isEffectiveAgainstBlock(stack, localBlock)) {						
						int localMetadata = world.getBlockMetadata(xx, yy, zz);
						 if(world.isRemote) {
							world.playAuxSFX(2001, xx, yy, zz, Block.getIdFromBlock(localBlock) + (localMetadata << 12));
						}
						if(!player.capabilities.isCreativeMode) {
							onBlockDestroyed(stack, world, localBlock, xx, yy, zz, player);
						}
						localBlock.onBlockHarvested(world, xx, yy, zz, localMetadata, player);
						if(localBlock.removedByPlayer(world, player, xx, yy, zz)) {
							localBlock.onBlockDestroyedByPlayer(world, xx, yy, zz, localMetadata);
							if(!world.isRemote && !player.capabilities.isCreativeMode) {
								localBlock.harvestBlock(world, player, xx, yy, zz, localMetadata);
							}
						}
					}
				}
			}
		}		
		return super.onBlockStartBreak(stack, x, y, z, player);
	}
}
