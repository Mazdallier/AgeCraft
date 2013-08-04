package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.MetalRegistry.Metal;
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.agecraft.core.blocks.BlockStoneOre;

public class Metals extends ACComponent {	
	
	public static Block ore;
	public static Block block;
	
	public void preInit() {
		ore = new BlockStoneOre(2500).setUnlocalizedName("ore");
	}
	
	public void init() {
		MetalRegistry.registerMetal(new Metal(0, "copper", OreType.METAL, new ItemStack(ore, 1, 0), 1, 1, false, true, true));
	}
	
	public void postInit() {
		
	}
	
	public void clientProxy() {
		
	}
}
