package elcon.mods.agecraft.core.items.tool;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSickle extends ItemTool {

	public ItemSickle(int id) {
		super(id);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
	    int blockID = world.getBlockId(x, y, z);
	    int halfHarvestLevel = (int) Math.floor(getToolHarvestLevel(stack) / 2) + 1;
	    System.out.println(halfHarvestLevel + " " + getToolHarvestLevel(stack));
	    System.out.println((x - halfHarvestLevel) + " to " + (x + getToolHarvestLevel(stack)));
		System.out.println((y - halfHarvestLevel) + " to " + (y + getToolHarvestLevel(stack)));
		System.out.println((z - halfHarvestLevel) + " to " + (z + getToolHarvestLevel(stack)));
		for(int xx = x - halfHarvestLevel; xx <= x + getToolHarvestLevel(stack); xx++) {
			for(int yy = y - halfHarvestLevel; yy <= y + getToolHarvestLevel(stack); yy++) {
				for(int zz = z - halfHarvestLevel; zz <= z + getToolHarvestLevel(stack); zz++) {
					int localBlockID = world.getBlockId(xx, yy, zz);
					Block localBlock = Block.blocksList[localBlockID];
					if(localBlock != null && isEffectiveAgainstBlock(stack, localBlock)) {						
						int localMetadata = world.getBlockMetadata(xx, yy, zz);
						if(!player.capabilities.isCreativeMode) {
							onBlockDestroyed(stack, world, localBlockID, xx, yy, zz, player);
						}
						localBlock.onBlockHarvested(world, xx, yy, zz, localMetadata, player);
						if(localBlock.removeBlockByPlayer(world, player, xx, yy, zz)) {
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
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLivingBase entity) {
		/*int halfHarvestLevel = (int) Math.floor(getToolHarvestLevel(stack) / 2);
		System.out.println((x - halfHarvestLevel) + " to " + (x + getToolHarvestLevel(stack)));
		System.out.println((y - halfHarvestLevel) + " to " + (y + getToolHarvestLevel(stack)));
		System.out.println((z - halfHarvestLevel) + " to " + (z + getToolHarvestLevel(stack)));
		for(int xx = x - halfHarvestLevel; xx <= x + getToolHarvestLevel(stack); xx++) {
			for(int yy = y - halfHarvestLevel; yy <= y + getToolHarvestLevel(stack); yy++) {
				for(int zz = z - halfHarvestLevel; zz <= z + getToolHarvestLevel(stack); zz++) {
					int localBlockID = world.getBlockId(xx, yy, zz);
					Block localBlock = Block.blocksList[localBlockID];
					if(localBlock != null && isEffectiveAgainstBlock(stack, localBlock)) {						
						if(entity instanceof EntityPlayer) { 
							EntityPlayer player = ((EntityPlayer) entity);
							int localMetadata = world.getBlockMetadata(xx, yy, zz);
							if(!player.capabilities.isCreativeMode) {
								onBlockDestroyed(stack, world, localBlockID, xx, yy, zz, player);
							}
							localBlock.onBlockHarvested(world, xx, yy, zz, localMetadata, player);
							if(localBlock.removeBlockByPlayer(world, player, xx, yy, zz)) {
								localBlock.onBlockDestroyedByPlayer(world, xx, yy, zz, localMetadata);
								if(!world.isRemote && !player.capabilities.isCreativeMode) {
									localBlock.harvestBlock(world, player, xx, yy, zz, localMetadata);
								}
							}
						} else {
							world.setBlockToAir(xx, yy, zz);
						}
					}
				}
			}
		}*/
		return super.onBlockDestroyed(stack, world, blockID, x, y, z, entity);
	}
}
