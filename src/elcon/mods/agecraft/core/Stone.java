package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.stone.BlockStone;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneBrick;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneCracked;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneMossy;

public class Stone extends ACComponent {

	public static Block stone;
	public static Block stoneCracked;
	public static Block stoneMossy;
	public static Block stoneBrick;
	
	@Override
	public void preInit() {
		//init blocks
		stone = new BlockStone(2500).setUnlocalizedName("stone_stone");
		stoneCracked = new BlockStoneCracked(2501).setUnlocalizedName("stone_stoneCracked");
		stoneMossy = new BlockStoneMossy(2502).setUnlocalizedName("stone_stoneMossy");
		stoneBrick = new BlockStoneBrick(2503).setUnlocalizedName("stone_stoneBrick");
		
		//register blocks
		GameRegistry.registerBlock(stone, "AC_stone_stone");
		GameRegistry.registerBlock(stoneCracked, "AC_stone_stoneCracked");
		GameRegistry.registerBlock(stoneMossy, "AC_stone_stoneMossy");
		GameRegistry.registerBlock(stoneBrick, "AC_stone_stoneBrick");
	}
}
