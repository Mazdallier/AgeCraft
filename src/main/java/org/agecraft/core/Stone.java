package org.agecraft.core;

import net.minecraft.block.Block;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.stone.BlockColoredStone;
import org.agecraft.core.blocks.stone.BlockColoredStoneBrick;
import org.agecraft.core.blocks.stone.BlockColoredStoneBrickPillar;
import org.agecraft.core.blocks.stone.BlockColoredStoneCracked;
import org.agecraft.core.blocks.stone.BlockColoredStoneMossy;
import org.agecraft.core.blocks.stone.BlockStone;
import org.agecraft.core.blocks.stone.BlockStoneBrick;
import org.agecraft.core.blocks.stone.BlockStoneBrickPillar;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.elconqore.items.ItemBlockExtendedMetadata;
import elcon.mods.elconqore.items.ItemBlockMetadata;

public class Stone extends ACComponent {

	//TODO: change stone to support more types
	public static Block stone;
	public static Block stoneBrick;
	public static Block stoneBrickPillar;
	
	public static Block coloredStone;
	public static Block coloredStoneCracked;
	public static Block coloredStoneMossy;
	public static Block coloredStoneBrick;
	public static Block coloredStoneBrickPillar;
	
	public Stone() {
		super("stone", false);
	}
	
	@Override
	public void preInit() {
		//init blocks
		stone = new BlockStone().setBlockName("AC_stone_stone");
		stoneBrick = new BlockStoneBrick().setBlockName("AC_stone_stoneBrick");
		stoneBrickPillar = new BlockStoneBrickPillar().setBlockName("AC_stone_stoneBrickPillar");
		
		coloredStone = new BlockColoredStone(2503).setBlockName("AC_stone_coloredStone");
		coloredStoneCracked = new BlockColoredStoneCracked().setBlockName("AC_stone_coloredStoneCracked");
		coloredStoneMossy = new BlockColoredStoneMossy().setBlockName("AC_stone_coloredStoneMossy");
		coloredStoneBrick = new BlockColoredStoneBrick().setBlockName("AC_stone_coloredStoneBrick");
		coloredStoneBrickPillar = new BlockColoredStoneBrickPillar().setBlockName("AC_stone_coloredStoneBrickPillar");
		
		//register blocks
		GameRegistry.registerBlock(stone, ItemBlockMetadata.class, "AC_stone_stone");
		GameRegistry.registerBlock(stoneBrick, ItemBlockMetadata.class, "AC_stone_stoneBrick");
		GameRegistry.registerBlock(stoneBrickPillar, ItemBlockMetadata.class, "AC_stone_stoneBrickPillar");
		
		GameRegistry.registerBlock(coloredStone, ItemBlockMetadata.class, "AC_stone_coloredStone");
		GameRegistry.registerBlock(coloredStoneCracked, ItemBlockMetadata.class, "AC_stone_coloredStoneCracked");
		GameRegistry.registerBlock(coloredStoneMossy, ItemBlockMetadataOverlay.class, "AC_stone_coloredStoneMossy");
		GameRegistry.registerBlock(coloredStoneBrick, ItemBlockExtendedMetadata.class, "AC_stone_coloredStoneBrick");
		GameRegistry.registerBlock(coloredStoneBrickPillar, ItemBlockExtendedMetadata.class, "AC_coloredStone_stoneBrickPillar");
	}
}
