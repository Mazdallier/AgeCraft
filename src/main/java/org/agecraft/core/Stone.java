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
import org.agecraft.core.blocks.stone.BlockStoneCracked;
import org.agecraft.core.blocks.stone.BlockStoneMossy;
import org.agecraft.core.registry.StoneTypeRegistry;
import org.agecraft.core.registry.StoneTypeRegistry.StoneType;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.elconqore.items.ItemBlockExtendedMetadata;
import elcon.mods.elconqore.items.ItemBlockMetadata;

public class Stone extends ACComponent {

	public static Block stone;
	public static Block stoneCracked;
	public static Block stoneMossy;
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
		stoneCracked = new BlockStoneCracked().setBlockName("AC_stone_stoneCracked");
		stoneMossy = new BlockStoneMossy().setBlockName("AC_stone_stoneMossy");
		stoneBrick = new BlockStoneBrick().setBlockName("AC_stone_stoneBrick");
		stoneBrickPillar = new BlockStoneBrickPillar().setBlockName("AC_stone_stoneBrickPillar");
		
		coloredStone = new BlockColoredStone().setBlockName("AC_stone_coloredStone");
		coloredStoneCracked = new BlockColoredStoneCracked().setBlockName("AC_stone_coloredStoneCracked");
		coloredStoneMossy = new BlockColoredStoneMossy().setBlockName("AC_stone_coloredStoneMossy");
		coloredStoneBrick = new BlockColoredStoneBrick().setBlockName("AC_stone_coloredStoneBrick");
		coloredStoneBrickPillar = new BlockColoredStoneBrickPillar().setBlockName("AC_stone_coloredStoneBrickPillar");
		
		//register blocks
		GameRegistry.registerBlock(stone, ItemBlockMetadata.class, "AC_stone_stone");
		GameRegistry.registerBlock(stoneCracked, ItemBlockMetadata.class, "AC_stone_stoneCracked");
		GameRegistry.registerBlock(stoneMossy, ItemBlockMetadata.class, "AC_stone_stoneMossy");
		GameRegistry.registerBlock(stoneBrick, ItemBlockExtendedMetadata.class, "AC_stone_stoneBrick");
		GameRegistry.registerBlock(stoneBrickPillar, ItemBlockExtendedMetadata.class, "AC_stone_stoneBrickPillar");
		
		GameRegistry.registerBlock(coloredStone, ItemBlockMetadata.class, "AC_stone_coloredStone");
		GameRegistry.registerBlock(coloredStoneCracked, ItemBlockMetadata.class, "AC_stone_coloredStoneCracked");
		GameRegistry.registerBlock(coloredStoneMossy, ItemBlockMetadata.class, "AC_stone_coloredStoneMossy");
		GameRegistry.registerBlock(coloredStoneBrick, ItemBlockExtendedMetadata.class, "AC_stone_coloredStoneBrick");
		GameRegistry.registerBlock(coloredStoneBrickPillar, ItemBlockExtendedMetadata.class, "AC_coloredStone_stoneBrickPillar");
		
		//register stone types
		StoneTypeRegistry.registerStoneType(new StoneType(0, "stone"));
		StoneTypeRegistry.registerStoneType(new StoneType(1, "granite"));
		StoneTypeRegistry.registerStoneType(new StoneType(2, "diorite"));
		StoneTypeRegistry.registerStoneType(new StoneType(3, "andesite"));
	}
}
