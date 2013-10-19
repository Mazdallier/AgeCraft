package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.crafting.BlockWorkbench;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;

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
	}
}
