package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolCreativeEntry;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.agecraft.core.items.tool.ItemAxe;
import elcon.mods.agecraft.core.items.tool.ItemBattleAxe;
import elcon.mods.agecraft.core.items.tool.ItemHoe;
import elcon.mods.agecraft.core.items.tool.ItemPickaxe;
import elcon.mods.agecraft.core.items.tool.ItemShovel;
import elcon.mods.agecraft.core.items.tool.ItemSword;
import elcon.mods.agecraft.core.items.tool.ItemTool;

public class Tools extends ACComponent {

	public static ItemTool sword;
	public static ItemTool pickaxe;
	public static ItemTool axe;
	public static ItemTool shovel;
	public static ItemTool hoe;
	public static ItemTool battleAxe;
	
	@Override
	public void preInit() {
		sword = (ItemTool) new ItemSword(12520).setUnlocalizedName("tools_sword");
		pickaxe = (ItemTool) new ItemPickaxe(12521).setUnlocalizedName("tools_pickaxe");
		axe = (ItemTool) new ItemAxe(12522).setUnlocalizedName("tools_axe");
		shovel = (ItemTool) new ItemShovel(12523).setUnlocalizedName("tools_shovel");
		hoe = (ItemTool) new ItemHoe(12524).setUnlocalizedName("tools_hoe");
		battleAxe = (ItemTool) new ItemBattleAxe(12525).setUnlocalizedName("tools_battleAxe");
	}
	
	@Override
	public void init() {
		//register tools
		ToolRegistry.registerTool(new Tool(0, "sword", sword, 2, 1, true, true, true, new Block[] {
			Block.web
		}));
		ToolRegistry.registerTool(new Tool(1, "pickaxe", pickaxe, 1, 2, true, true, true, new Block[] {
			Block.stone, Metals.ore, Metals.block, Metals.fence, Metals.fenceGate, Metals.door, Metals.trapdoor, Metals.ladder
		}));
		ToolRegistry.registerTool(new Tool(2, "axe", axe, 1, 2, true, true, true, new Block[] {
			Trees.wood, Trees.log, Trees.woodWall, Trees.planks, Trees.fence, Trees.fenceGate, Trees.door, Trees.trapdoor, Trees.ladder
		}));
		ToolRegistry.registerTool(new Tool(3, "shovel", shovel, 1, 2, true, true, true, new Block[] {
			Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow
		}));
		ToolRegistry.registerTool(new Tool(4, "hoe", hoe, 2, 2, true, true, true, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(5, "battleAxe", battleAxe, 1, 1, true, true, true, ToolRegistry.tools[2].blocksEffectiveAgainst
		));
		
		//register tool materials		
		ToolRegistry.registerToolMaterial(new ToolMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), 39, 1.5F, 4, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), 39, 1.5F, 4, 0));
		
		//register tool rod materials
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(0, "woodOak", "tools.materials.woodOak", new ItemStack(Trees.stick, 1, 0), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(1, "woodBirch", "tools.materials.woodBirch", new ItemStack(Trees.stick, 1, 1), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(2, "woodSpruce", "tools.materials.woodSpruce", new ItemStack(Trees.stick, 1, 2), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(3, "woodJungle", "tools.materials.woodJungle", new ItemStack(Trees.stick, 1, 3), 20, 0.5F, 0));		
		
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(128, "copper", "metals.copper", new ItemStack(Metals.stick, 1, 0), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.stick, 1, 2), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(131, "silver", "metals.silver", new ItemStack(Metals.stick, 1, 3), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(132, "iron", "metals.iron", new ItemStack(Metals.stick, 1, 4), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(133, "gold", "metals.gold", new ItemStack(Metals.stick, 1, 5), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.stick, 1, 9), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(139, "steel", "metals.steel", new ItemStack(Metals.stick, 1, 11), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.stick, 1, 12), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.stick, 1, 13), 20, 0.5F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.stick, 1, 14), 20, 0.5F, 0));
		
		//register tool creative entries		
		//TODO: add enhancements and head less tools
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			if(ToolRegistry.tools[i] != null) {
				Tool tool = ToolRegistry.tools[i];
				if(tool.hasHead) {
					for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
						if(ToolRegistry.toolMaterials[j] != null) {
							if(tool.hasRod) {
								ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, j, 0));
							} else {
								ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, 0, 0));
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void postInit() {
		//register tool classes
		MinecraftForge.setToolClass(sword, "sword", 0);
		MinecraftForge.setToolClass(pickaxe, "pickaxe", 0);
		MinecraftForge.setToolClass(axe, "axe", 0);
		MinecraftForge.setToolClass(shovel, "shovel", 0);
		MinecraftForge.setToolClass(hoe, "hoe", 0);
		
		//register block harvest levels
		MinecraftForge.setBlockHarvestLevel(Block.stone, 0, "pickaxe", 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			Tool tool = ToolRegistry.tools[i];
			if(tool != null) {
				if(tool.hasHead) {
					for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
						ToolMaterial toolMaterial = ToolRegistry.toolMaterials[j];
						if(toolMaterial != null) {
							toolMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/" + tool.name + "/" + tool.name + ACUtil.firstUpperCase(toolMaterial.name));
						}
					}
				}
				if(tool.hasRod) {
					for(int j = 0; j < ToolRegistry.toolRodMaterials.length; j++) {
						ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[j];
						if(toolRodMaterial != null) {
							toolRodMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/sticks/" + tool.name + "/" + tool.name + ACUtil.firstUpperCase(toolRodMaterial.name));
						}
					}
				}
				if(tool.hasEnhancements) {
					//TODO: add tool enhancements
				}
			}
		}
	}
}
