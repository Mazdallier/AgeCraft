package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.blocks.stone.BlockStone;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneBrick;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneBrickPillar;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneCracked;
import elcon.mods.agecraft.core.blocks.stone.BlockStoneMossy;
import elcon.mods.core.items.ItemBlockExtendedMetadata;
import elcon.mods.core.items.ItemBlockMetadata;

public class Stone extends ACComponent {

	public static Block stone;
	public static Block stoneCracked;
	public static Block stoneMossy;
	public static Block stoneBrick;
	public static Block stoneBrickPillar;
	
	@Override
	public void preInit() {
		//init blocks
		stone = new BlockStone(2500).setUnlocalizedName("stone_stone");
		stoneCracked = new BlockStoneCracked(2501).setUnlocalizedName("stone_stoneCracked");
		stoneMossy = new BlockStoneMossy(2502).setUnlocalizedName("stone_stoneMossy");
		stoneBrick = new BlockStoneBrick(2503).setUnlocalizedName("stone_stoneBrick");
		stoneBrickPillar = new BlockStoneBrickPillar(2504).setUnlocalizedName("stone_stoneBrickPillar");
		
		//register blocks
		GameRegistry.registerBlock(stone, ItemBlockMetadata.class, "AC_stone_stone");
		GameRegistry.registerBlock(stoneCracked, ItemBlockMetadata.class, "AC_stone_stoneCracked");
		GameRegistry.registerBlock(stoneMossy, ItemBlockMetadata.class, "AC_stone_stoneMossy");
		GameRegistry.registerBlock(stoneBrick, ItemBlockExtendedMetadata.class, "AC_stone_stoneBrick");
		GameRegistry.registerBlock(stoneBrickPillar, ItemBlockExtendedMetadata.class, "AC_stone_stoneBrickPillar");
	}
}
