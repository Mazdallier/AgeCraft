package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.crafting.BlockWorkbench;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;
import elcon.mods.agecraft.core.recipes.RecipesWorkbench;
import elcon.mods.agecraft.core.tileentities.TileEntityWorkbench;

public class Crafting extends ACComponent {
	
	public static Block workbench;
	
	@Override
	public void preInit() {
		//init blocks
		workbench = new BlockWorkbench(2540).setUnlocalizedName("crafting_workbench");
		
		//register blocks
		GameRegistry.registerBlock(workbench, ItemBlockExtendedMetadata.class, "AC_crafting_workbench");
		
		//register block flammability
		Block.setBurnProperties(workbench.blockID, 5, 20);
		
		//register tileentities
		GameRegistry.registerTileEntity(TileEntityWorkbench.class, "TileWorkbench");
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
