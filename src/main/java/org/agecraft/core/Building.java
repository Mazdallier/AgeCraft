package org.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.agecraft.ACComponent;
import org.agecraft.ACCreativeTabs;
import org.agecraft.core.blocks.building.BlockBrick;
import org.agecraft.core.blocks.building.BlockClay;
import org.agecraft.core.blocks.building.BlockDirt;
import org.agecraft.core.blocks.building.BlockGlass;
import org.agecraft.core.blocks.building.BlockGrass;
import org.agecraft.core.blocks.building.BlockHardenedClay;
import org.agecraft.core.blocks.building.BlockSand;
import org.agecraft.core.blocks.building.BlockStainedGlass;
import org.agecraft.core.blocks.building.BlockStainedHardenedClay;
import org.agecraft.core.items.ItemDummy;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.elconqore.items.ItemBlockMetadata;
import elcon.mods.elconqore.items.ItemBlockName;

public class Building extends ACComponent {

	public static Block grass;
	public static Block dirt;
	public static Block sand;
	public static Block clay;
	public static Block hardenedClay;
	public static Block stainedHardenedClay;
	public static Block brickBlock;
	public static Block glass;
	public static Block stainedGlass;
	
	public static Item clayBall;
	public static Item brick;
	
	public Building() {
		super("building", false);
	}

	@Override
	public void preInit() {
		//init blocks
		grass = new BlockGrass().setBlockName("AC_building_grass");
		dirt = new BlockDirt().setBlockName("AC_building_grass");
		sand = new BlockSand().setBlockName("AC_building_sand");
		clay = new BlockClay().setBlockName("AC_building_clay");
		hardenedClay = new BlockHardenedClay().setBlockName("AC_building_hardenedClay");
		stainedHardenedClay = new BlockStainedHardenedClay().setBlockName("AC_building_stainedHardenedClay");
		brickBlock = new BlockBrick().setBlockName("AC_building_brickBlock");
		glass = new BlockGlass().setBlockName("AC_building_glass");
		stainedGlass = new BlockStainedGlass().setBlockName("AC_building_stainedGlass");
		
		//register blocks
		GameRegistry.registerBlock(grass, ItemBlockName.class, "AC_building_grass");
		GameRegistry.registerBlock(dirt, ItemBlockMetadata.class, "AC_building_dirt");
		GameRegistry.registerBlock(sand, ItemBlockMetadata.class, "AC_building_sand");
		GameRegistry.registerBlock(clay, ItemBlockName.class, "AC_building_clay");
		GameRegistry.registerBlock(hardenedClay, ItemBlockName.class, "AC_building_hardenedClay");
		GameRegistry.registerBlock(stainedHardenedClay, ItemBlockMetadata.class, "AC_building_stainedHardenedClay");
		GameRegistry.registerBlock(brickBlock, ItemBlockName.class, "AC_building_brickBlock");
		GameRegistry.registerBlock(glass, ItemBlockMetadata.class, "AC_building_glass");
		GameRegistry.registerBlock(stainedGlass, ItemBlockMetadata.class, "AC_building_stainedGlass");
		
		//set block harvest levels
		hardenedClay.setHarvestLevel("pickaxe", 0);
		stainedHardenedClay.setHarvestLevel("pickaxe", 0);
		brickBlock.setHarvestLevel("pickaxe", 0);
		
		//init items
		clayBall = new ItemDummy("building.clayBall").setCreativeTab(ACCreativeTabs.building).setTextureName("agecraft:building/clay").setUnlocalizedName("AC_building_clayBall");
		brick = new ItemDummy("building.brick").setCreativeTab(ACCreativeTabs.building).setTextureName("agecraft:building/brick").setUnlocalizedName("AC_building_brick");
	
		//register items
		GameRegistry.registerItem(clayBall, "AC_building_clayBall");
		GameRegistry.registerItem(brick, "AC_building_brick");
	}
}
