package org.agecraft.core.items.tools;

import org.agecraft.core.MetalRegistry;
import org.agecraft.core.ToolRegistry;
import org.agecraft.core.blocks.metal.BlockMetalBlock;
import org.agecraft.core.blocks.metal.BlockMetalDoor;
import org.agecraft.core.blocks.metal.BlockMetalFence;
import org.agecraft.core.blocks.metal.BlockMetalFenceGate;
import org.agecraft.core.blocks.metal.BlockMetalLadder;
import org.agecraft.core.blocks.metal.BlockMetalPillar;
import org.agecraft.core.blocks.metal.BlockMetalTrapdoor;
import org.agecraft.core.blocks.metal.BlockStoneLayered;
import org.agecraft.core.blocks.metal.BlockStoneOre;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemPickaxe extends ItemTool {

	public ItemPickaxe(int id) {
		super(id);
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, Block block, int meta) {
		if(block instanceof BlockStoneLayered) {
			return getToolHarvestLevel(stack) >= (5 - meta);
		} else if(block instanceof BlockStoneOre) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[meta].harvestLevel;
		} else if(block instanceof BlockMetalBlock) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 3)) / 4].harvestLevel;
		} else if(block instanceof BlockMetalPillar) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 3)) / 4].harvestLevel;
		} else if(block instanceof BlockMetalFence) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[meta].harvestLevel;
		} else if(block instanceof BlockMetalFenceGate) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 7)) / 8].harvestLevel;
		} else if(block instanceof BlockMetalDoor) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 127)) / 128].harvestLevel;
		} else if(block instanceof BlockMetalTrapdoor) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 31)) / 32].harvestLevel;
		} else if(block instanceof BlockMetalLadder) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[(meta - (meta & 7)) / 8].harvestLevel;
		} else {
			Block[] blocksEffectiveAgainst = ToolRegistry.tools[getToolType(stack)].blocksEffectiveAgainst;
			for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
				if(blocksEffectiveAgainst[i].blockID == block.blockID) {
					return true;
				}
			}
		}
		return canHarvestBlock(block, stack);
	}
}
