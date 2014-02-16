package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.crafting.BlockSmelteryFurnace;
import org.agecraft.core.blocks.crafting.BlockWorkbench;
import org.agecraft.core.blocks.crafting.MBStructureSmeltery;
import org.agecraft.core.items.ItemBlockSmelteryFurnace;
import org.agecraft.core.recipes.RecipesWorkbench;
import org.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import org.agecraft.core.tileentities.TileEntityWorkbench;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.core.items.ItemBlockExtendedMetadata;
import elcon.mods.core.structure.MBStructureRegistry;

public class Crafting extends ACComponent {
	
	public static Block workbench;
	public static Block smelteryFurnace;
	public static Block smelteryHatch;
	public static Block smelteryDrain;
	public static Block smelteryFaucet;
	public static Block smelteryCastingTable;
	public static Block smelteryTank;
	
	public static MBStructureSmeltery structureSmeltery;
	
	@Override
	public void preInit() {
		//init blocks
		workbench = new BlockWorkbench(2550).setUnlocalizedName("crafting_workbench");
		smelteryFurnace = new BlockSmelteryFurnace(2551).setUnlocalizedName("crafting_smelteryFurnace");
		
		//register blocks
		GameRegistry.registerBlock(workbench, ItemBlockExtendedMetadata.class, "AC_crafting_workbench");
		GameRegistry.registerBlock(smelteryFurnace, ItemBlockSmelteryFurnace.class, "AC_crafting_smelteryFurnace");
		
		//register block flammability
		Block.setBurnProperties(workbench.blockID, 5, 20);
		
		//register tileentities
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "TileWorkbench");
		GameRegistry.registerTileEntity(TileEntitySmelteryFurnace.class, "TileSmelteryFurnace");
	}
	
	@Override
	public void init() {
		//init multiblock structure
		structureSmeltery = new MBStructureSmeltery("smeltery");
		
		//register multiblock structures
		MBStructureRegistry.registerStructure(structureSmeltery);
	}
	
	@Override
	public void postInit() {
		//add recipes
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				GameRegistry.addRecipe(new ItemStack(workbench.blockID, 1, i), "##", "##", '#', new ItemStack(Trees.planks, 1, i));
			}
		}
		RecipesWorkbench.addRecipes();
	}
}
