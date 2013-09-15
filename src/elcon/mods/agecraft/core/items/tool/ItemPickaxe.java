package elcon.mods.agecraft.core.items.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalDoor;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFence;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFenceGate;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalLadder;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalTrapdoor;
import elcon.mods.agecraft.core.blocks.metal.BlockOreStorage;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneLayered;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneOre;

public class ItemPickaxe extends ItemTool {

	public ItemPickaxe(int id) {
		super(id);
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, Block block, int meta) {
		if(block instanceof BlockStoneLayered) {
			return getToolHarvestLevel(stack) >= (6 - meta);
		} else if(block instanceof BlockStoneOre) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[meta].harvestLevel;
		} else if(block instanceof BlockOreStorage) {
			return getToolHarvestLevel(stack) >= MetalRegistry.metals[meta].harvestLevel;
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
