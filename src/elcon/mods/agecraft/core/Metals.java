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
import elcon.mods.agecraft.core.blocks.metal.BlockOreStorage;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneOre;
import elcon.mods.agecraft.core.items.ItemBlockExtendedMetadata;
import elcon.mods.agecraft.core.items.ItemGem;
import elcon.mods.agecraft.core.items.ItemIngot;

public class Metals extends ACComponent {

	public static Block ore;
	public static Block block;

	public static Item ingot;
	public static Item gem;

	@Override
	public void preInit() {
		//init blocks
		ore = new BlockStoneOre(2500).setUnlocalizedName("ore");
		block = new BlockOreStorage(2501).setUnlocalizedName("block");

		//register blocks
		GameRegistry.registerBlock(ore, ItemBlockExtendedMetadata.class, "AC_ore");
		GameRegistry.registerBlock(block, ItemBlockExtendedMetadata.class, "AC_block");

		//init items
		ingot = new ItemIngot(12500).setUnlocalizedName("ingot");
		gem = new ItemGem(12501).setUnlocalizedName("gem");
	}

	@Override
	public void init() {
		//init metals
		//TODO: change metal colors
		MetalRegistry.registerMetal(new Metal(0, "coal", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 0), 1, 1, true, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(1, "copper", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 1), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(2, "tin", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 2), 1, 1, false, true, true, true, 0xFFFFFF));
		MetalRegistry.registerMetal(new Metal(3, "bronze", OreType.METAL, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(ore, 1, 3), 1, 1, false, false, true, true, 0xFFFFFF));
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
