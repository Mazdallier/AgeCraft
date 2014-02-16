package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.stone.BlockColoredStone;
import org.agecraft.core.blocks.stone.BlockColoredStoneBrick;
import org.agecraft.core.blocks.stone.BlockColoredStoneBrickPillar;
import org.agecraft.core.blocks.stone.BlockColoredStoneCracked;
import org.agecraft.core.blocks.stone.BlockColoredStoneMossy;
import org.agecraft.core.blocks.stone.BlockStone;
import org.agecraft.core.blocks.stone.BlockStoneBrick;
import org.agecraft.core.blocks.stone.BlockStoneBrickPillar;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.core.items.ItemBlockExtendedMetadata;
import elcon.mods.core.items.ItemBlockMetadata;
import elcon.mods.core.items.ItemBlockMetadataOverlay;

public class Stone extends ACComponent {

	public static Block stone;
	public static Block stoneBrick;
	public static Block stoneBrickPillar;
	
	public static Block coloredStone;
	public static Block coloredStoneCracked;
	public static Block coloredStoneMossy;
	public static Block coloredStoneBrick;
	public static Block coloredStoneBrickPillar;
	
	@Override
	public void preInit() {
		//init blocks
		stone = new BlockStone(2500).setUnlocalizedName("stone_stone");
		stoneBrick = new BlockStoneBrick(2501).setUnlocalizedName("stone_stoneBrick");
		stoneBrickPillar = new BlockStoneBrickPillar(2502).setUnlocalizedName("stone_stoneBrickPillar");
		
		coloredStone = new BlockColoredStone(2503).setUnlocalizedName("stone_coloredStone");
		coloredStoneCracked = new BlockColoredStoneCracked(2504).setUnlocalizedName("stone_coloredStoneCracked");
		coloredStoneMossy = new BlockColoredStoneMossy(2505).setUnlocalizedName("stone_coloredStoneMossy");
		coloredStoneBrick = new BlockColoredStoneBrick(2506).setUnlocalizedName("stone_coloredStoneBrick");
		coloredStoneBrickPillar = new BlockColoredStoneBrickPillar(2507).setUnlocalizedName("stone_coloredStoneBrickPillar");
		
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
