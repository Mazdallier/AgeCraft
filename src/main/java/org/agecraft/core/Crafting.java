package org.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.crafting.BlockWorkbench;
import org.agecraft.core.recipes.RecipesWorkbench;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.core.tileentities.TileEntityWorkbench;

import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.elconqore.items.ItemBlockExtendedMetadata;

public class Crafting extends ACComponent {
	
	public static Block workbench;
	
	public Crafting() {
		super("crafting", false);
	}
	
	@Override
	public void preInit() {
		//init blocks
		workbench = new BlockWorkbench().setBlockName("AC_crafting_workbench");
		
		//register blocks
		GameRegistry.registerBlock(workbench, ItemBlockExtendedMetadata.class, "AC_crafting_workbench");
		
		//register block flammability
		Blocks.fire.setFireInfo(workbench, 5, 20);
		
		//register tileentities
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "AC_TileWorkbench");
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		//add recipes
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				GameRegistry.addRecipe(new ItemStack(workbench, 1, i), "##", "##", '#', new ItemStack(Trees.planks, 1, i));
			}
		}
		RecipesWorkbench.addRecipes();
	}
}
