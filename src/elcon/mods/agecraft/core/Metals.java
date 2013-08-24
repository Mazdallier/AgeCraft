package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.MetalRegistry.Metal;
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalDoor;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFence;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFenceGate;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalLadder;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalTrapdoor;
import elcon.mods.agecraft.core.blocks.metal.BlockOreStorage;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneOre;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;
import elcon.mods.agecraft.core.items.ItemGem;
import elcon.mods.agecraft.core.items.ItemIngot;
import elcon.mods.agecraft.core.items.ItemMetalDoor;

public class Metals extends ACComponent {

	public static Block ore;
	public static Block block;
	public static Block fence;
	public static Block fenceGate;
	public static Block door;
	public static Block trapdoor;
	public static Block ladder;

	public static Item ingot;
	public static Item gem;

	@Override
	public void preInit() {
		//init blocks
		ore = new BlockStoneOre(2500).setUnlocalizedName("metal_ore");
		block = new BlockOreStorage(2501).setUnlocalizedName("metal_block");
		fence = new BlockMetalFence(2502).setUnlocalizedName("metal_fence");
		fenceGate = new BlockMetalFenceGate(2503).setUnlocalizedName("metal_fenceGate");
		door = new BlockMetalDoor(2504).setUnlocalizedName("metal_door");
		trapdoor = new BlockMetalTrapdoor(2505).setUnlocalizedName("metal_trapdoor");
		ladder = new BlockMetalLadder(2506).setUnlocalizedName("metal_ladder");

		//register blocks
		GameRegistry.registerBlock(ore, ItemBlockExtendedMetadata.class, "AC_metal_ore");
		GameRegistry.registerBlock(block, ItemBlockExtendedMetadata.class, "AC_metal_block");
		GameRegistry.registerBlock(fence, ItemBlockExtendedMetadata.class, "AC_metal_fence");
		GameRegistry.registerBlock(fenceGate, ItemBlockExtendedMetadata.class, "AC_metal_fenceGate");
		GameRegistry.registerBlock(door, ItemMetalDoor.class, "AC_metal_door");
		GameRegistry.registerBlock(trapdoor, ItemBlockExtendedMetadata.class, "AC_metal_trapdoor");
		GameRegistry.registerBlock(ladder, ItemBlockExtendedMetadata.class, "AC_metal_ladder");

		//init items
		ingot = new ItemIngot(12500).setUnlocalizedName("ingot");
		gem = new ItemGem(12501).setUnlocalizedName("gem");
	}

	@Override
	public void init() {
		//init metals
		//TODO: change metal colors
		MetalRegistry.registerMetal(new Metal(0, "copper", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 0), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(1, "tin", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 1), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(2, "bronze", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 2), 1, 1, false, false, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(3, "silver", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 3), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(4, "iron", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 4), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(5, "gold", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 5), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(6, "zinc", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 6), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(7, "nickel", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 7), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(8, "aluminium", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 8), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(9, "platinum", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 9), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(10, "lead", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 10), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(11, "cobalt", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 11), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(12, "mithril", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 12), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(13, "adamantite", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 13, 1), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(14, "tungsten", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 14), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(15, "uranium", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 15), 1, 1, false, true, true, true, 0xFFFFFF));
		
		MetalRegistry.registerMetal(new Metal(32, "coal", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 32), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(33, "amethyst", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 33), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(34, "berylRed", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 34), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(35, "berylYellow", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 35), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(36, "berylBlue", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 36), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(37, "berylGreen", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 37), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(38, "diamond", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 38), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(39, "emerald", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 39), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(40, "jade", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 40), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(41, "lapisLazuli", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 41), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(42, "onyx", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 42), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(43, "opal", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 43), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(44, "quartz", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 44), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(45, "ruby", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 45), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(46, "sapphire", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 46), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(47, "tigerEye", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 47), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(48, "topaz", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 48), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(49, "turquoise", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 49), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(50, "redstone", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 50), 1, 1, true, true, true, true, 0xFFFFFF));
	}

	@Override
	public void postInit() {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister iconRegister) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/metals/ore" + ACUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.block = iconRegister.registerIcon("agecraft:metals/blocks/metals/block" + ACUtil.firstUpperCase(metal.name));
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/gems/ore" + ACUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.block = iconRegister.registerIcon("agecraft:metals/blocks/gems/block" + ACUtil.firstUpperCase(metal.name));
					}
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/metals/ingot" + ACUtil.firstUpperCase(metal.name));
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/gems/gem" + ACUtil.firstUpperCase(metal.name));
					}
				}
			}
		}
	}

	@Override
	public void clientProxy() {

	}
}
