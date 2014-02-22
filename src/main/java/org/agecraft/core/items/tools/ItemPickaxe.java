package org.agecraft.core.items.tools;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import org.agecraft.core.blocks.metal.BlockMetalBlock;
import org.agecraft.core.blocks.metal.BlockMetalDoor;
import org.agecraft.core.blocks.metal.BlockMetalFence;
import org.agecraft.core.blocks.metal.BlockMetalFenceGate;
import org.agecraft.core.blocks.metal.BlockMetalLadder;
import org.agecraft.core.blocks.metal.BlockMetalPillar;
import org.agecraft.core.blocks.metal.BlockMetalTrapdoor;
import org.agecraft.core.blocks.metal.BlockStoneLayered;
import org.agecraft.core.blocks.metal.BlockStoneOre;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.ToolRegistry;

public class ItemPickaxe extends ItemTool {

	@Override
	public boolean canHarvestBlock(ItemStack stack, Block block, int meta) {
		if(block instanceof BlockStoneLayered) {
			return getToolHarvestLevel(stack) >= (5 - meta);
		} else if(block instanceof BlockStoneOre) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get(meta).harvestLevel;
		} else if(block instanceof BlockMetalBlock) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 3)) / 4).harvestLevel;
		} else if(block instanceof BlockMetalPillar) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 3)) / 4).harvestLevel;
		} else if(block instanceof BlockMetalFence) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get(meta).harvestLevel;
		} else if(block instanceof BlockMetalFenceGate) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 7)) / 8).harvestLevel;
		} else if(block instanceof BlockMetalDoor) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 127)) / 128).harvestLevel;
		} else if(block instanceof BlockMetalTrapdoor) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 31)) / 32).harvestLevel;
		} else if(block instanceof BlockMetalLadder) {
			return getToolHarvestLevel(stack) >= MetalRegistry.instance.get((meta - (meta & 7)) / 8).harvestLevel;
		} else {
			Block[] blocksEffectiveAgainst = ToolRegistry.instance.get(getToolType(stack)).blocksEffectiveAgainst;
			for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
				if(Block.getIdFromBlock(blocksEffectiveAgainst[i]) == Block.getIdFromBlock(block)) {
					return true;
				}
			}
		}
		return canHarvestBlock(block, stack);
	}
}
