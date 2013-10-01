package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolCreativeEntry;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.agecraft.core.items.tool.ItemArrow;
import elcon.mods.agecraft.core.items.tool.ItemAxe;
import elcon.mods.agecraft.core.items.tool.ItemBattleAxe;
import elcon.mods.agecraft.core.items.tool.ItemBow;
import elcon.mods.agecraft.core.items.tool.ItemChisel;
import elcon.mods.agecraft.core.items.tool.ItemCrossbow;
import elcon.mods.agecraft.core.items.tool.ItemDagger;
import elcon.mods.agecraft.core.items.tool.ItemFishingRod;
import elcon.mods.agecraft.core.items.tool.ItemHammer;
import elcon.mods.agecraft.core.items.tool.ItemHandSaw;
import elcon.mods.agecraft.core.items.tool.ItemHoe;
import elcon.mods.agecraft.core.items.tool.ItemKnife;
import elcon.mods.agecraft.core.items.tool.ItemMace;
import elcon.mods.agecraft.core.items.tool.ItemPickaxe;
import elcon.mods.agecraft.core.items.tool.ItemSaw;
import elcon.mods.agecraft.core.items.tool.ItemShovel;
import elcon.mods.agecraft.core.items.tool.ItemSickle;
import elcon.mods.agecraft.core.items.tool.ItemSpear;
import elcon.mods.agecraft.core.items.tool.ItemSword;
import elcon.mods.agecraft.core.items.tool.ItemTool;
import elcon.mods.agecraft.core.items.tool.ItemWarhammer;

public class Tools extends ACComponent {

	public static ItemTool sword;
	public static ItemTool pickaxe;
	public static ItemTool axe;
	public static ItemTool shovel;
	public static ItemTool hoe;
	public static ItemTool battleaxe;
	public static ItemTool warhammer;
	public static ItemTool mace;
	public static ItemTool dagger;
	public static ItemTool knife;
	public static ItemTool spear;
	public static ItemTool sickle;
	public static ItemTool hammer;
	public static ItemTool chisel;
	public static ItemTool handSaw;
	public static ItemTool saw;
	public static ItemTool bow;
	public static ItemTool crossbow;
	public static ItemTool arrow;
	public static ItemTool fishingRod;

	@Override
	public void preInit() {
		sword = (ItemTool) new ItemSword(12520).setUnlocalizedName("tools_sword");
		pickaxe = (ItemTool) new ItemPickaxe(12521).setUnlocalizedName("tools_pickaxe");
		axe = (ItemTool) new ItemAxe(12522).setUnlocalizedName("tools_axe");
		shovel = (ItemTool) new ItemShovel(12523).setUnlocalizedName("tools_shovel");
		hoe = (ItemTool) new ItemHoe(12524).setUnlocalizedName("tools_hoe");
		battleaxe = (ItemTool) new ItemBattleAxe(12525).setUnlocalizedName("tools_battleAxe");
		warhammer = (ItemTool) new ItemWarhammer(12526).setUnlocalizedName("tools_warhammer");
		mace = (ItemTool) new ItemMace(12527).setUnlocalizedName("tools_mace");
		dagger = (ItemTool) new ItemDagger(12528).setUnlocalizedName("tools_dagger");
		knife = (ItemTool) new ItemKnife(12529).setUnlocalizedName("tools_knife");
		spear = (ItemTool) new ItemSpear(12530).setUnlocalizedName("tools_spear");
		sickle = (ItemTool) new ItemSickle(12531).setUnlocalizedName("tools_sickle");
		hammer = (ItemTool) new ItemHammer(12532).setUnlocalizedName("tools_hammer");
		chisel = (ItemTool) new ItemChisel(12533).setUnlocalizedName("tools_chisel");
		handSaw = (ItemTool) new ItemHandSaw(12534).setUnlocalizedName("tools_handSaw");
		saw = (ItemTool) new ItemSaw(12535).setUnlocalizedName("tools_saw");
		bow = (ItemTool) new ItemBow(12536).setUnlocalizedName("tools_bow");
		crossbow = (ItemTool) new ItemCrossbow(12537).setUnlocalizedName("tools_crossbow");
		arrow = (ItemTool) new ItemArrow(12538).setUnlocalizedName("tools_arrow");
		fishingRod = (ItemTool) new ItemFishingRod(12539).setUnlocalizedName("tools_fishingRod");
	}

	@Override
	public void init() {
		// register tools
		ToolRegistry.registerTool(new Tool(0, "sword", sword, 2, 1, true, true, true, new Block[]{
			Block.web
		}));
		ToolRegistry.registerTool(new Tool(1, "pickaxe", pickaxe, 1, 2, true, true, true, new Block[]{
			Block.stone, Block.cobblestone, Metals.ore, Metals.block, Metals.fence, Metals.fenceGate, Metals.door, Metals.trapdoor, Metals.ladder
		}));
		ToolRegistry.registerTool(new Tool(2, "axe", axe, 1, 2, true, true, true, new Block[]{
			Trees.wood, Trees.log, Trees.woodWall, Trees.planks, Trees.fence, Trees.fenceGate, Trees.door, Trees.trapdoor, Trees.ladder
		}));
		ToolRegistry.registerTool(new Tool(3, "shovel", shovel, 1, 2, true, true, true, new Block[]{
			Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow
		}));
		ToolRegistry.registerTool(new Tool(4, "hoe", hoe, 2, 2, true, true, true, new Block[]{
		}));
		ToolRegistry.registerTool(new Tool(5, "battleaxe", battleaxe, 1, 1, true, true, true, ToolRegistry.tools[2].blocksEffectiveAgainst));
		ToolRegistry.registerTool(new Tool(6, "warhammer", warhammer, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(7, "mace", mace, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(8, "dagger", dagger, 2, 1, true, true, true, new Block[]{	
			Block.web
		}));
		ToolRegistry.registerTool(new Tool(9, "knife", knife, 2, 1, true, true, true, new Block[]{	
			Block.web
		}));
		ToolRegistry.registerTool(new Tool(10, "spear", spear, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(11, "sickle", sickle, 1, 2, true, true, true, new Block[]{
			Block.web, Block.tallGrass, Block.deadBush, Block.vine, Trees.leaves
		}));
		ToolRegistry.registerTool(new Tool(12, "hammer", hammer, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(13, "chisel", chisel, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(14, "handSaw", handSaw, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(15, "saw", saw, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(16, "bow", bow, 2, 1, false, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(17, "crossbow", crossbow, 2, 1, false, true, true, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(18, "arrow", arrow, 0, 0, false, true, true, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(19, "fishingRod", fishingRod, 2, 2, false, true, true, new Block[]{	
		}));

		// register tool materials
		ToolRegistry.registerToolMaterial(new ToolMaterial(127, "stone", "tools.materials.stone", new ItemStack(Block.cobblestone, 1, 0), 138, 1.5F, 2, 1));
		ToolRegistry.registerToolMaterial(new ToolMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), 144, 2.25F, 2, 3));
		ToolRegistry.registerToolMaterial(new ToolMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), 166, 3.0F, 3, 4));
		ToolRegistry.registerToolMaterial(new ToolMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), 156, 3.75F, 4, 2));
		ToolRegistry.registerToolMaterial(new ToolMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), 189, 4.5F, 5, 5));
		ToolRegistry.registerToolMaterial(new ToolMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), 24, 10.5F, 1, 0));
		ToolRegistry.registerToolMaterial(new ToolMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), 266, 5.25F, 6, 6));
		ToolRegistry.registerToolMaterial(new ToolMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), 492, 6.0F, 7, 7));
		ToolRegistry.registerToolMaterial(new ToolMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), 701, 6.75F, 8, 8));
		ToolRegistry.registerToolMaterial(new ToolMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), 1084, 7.5F, 9, 9));
		ToolRegistry.registerToolMaterial(new ToolMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), 1411, 8.25F, 10, 10));

		// register tool rod materials
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(0, "woodOak", "tools.materials.woodOak", new ItemStack(Trees.stick, 1, 0), 27, 0.25F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(1, "woodBirch", "tools.materials.woodBirch", new ItemStack(Trees.stick, 1, 1), 27, 0.25F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(2, "woodSpruce", "tools.materials.woodSpruce", new ItemStack(Trees.stick, 1, 2), 27, 0.25F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(3, "woodJungle", "tools.materials.woodJungle", new ItemStack(Trees.stick, 1, 3), 27, 0.25F, 0));

		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(128, "copper", "metals.copper", new ItemStack(Metals.stick, 1, 0), 92, 0.75F, 1));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.stick, 1, 2), 111, 1.0F, 2));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(131, "silver", "metals.silver", new ItemStack(Metals.stick, 1, 3), 104, 1.25F, 2));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(132, "iron", "metals.iron", new ItemStack(Metals.stick, 1, 4), 125, 1.5F, 3));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(133, "gold", "metals.gold", new ItemStack(Metals.stick, 1, 5), 16, 3.0F, 0));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.stick, 1, 9), 177, 1.75F, 3));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(139, "steel", "metals.steel", new ItemStack(Metals.stick, 1, 11), 328, 2.0F, 4));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.stick, 1, 12), 467, 2.25F, 4));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.stick, 1, 13), 723, 2.5F, 5));
		ToolRegistry.registerToolRodMaterial(new ToolRodMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.stick, 1, 14), 941, 2.75F, 5));

		// register tool creative entries
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			if(ToolRegistry.tools[i] != null) {
				Tool tool = ToolRegistry.tools[i];
				if(tool.hasHead) {
					for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
						if(ToolRegistry.toolMaterials[j] != null) {
							if(tool.hasRod) {
								if(j == 127) {
									ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, 0, -1));
								} else {
									ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, j, -1));
								}
							} else {
								ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, -1, -1));
							}
						}
					}
				} else {
					for(int j = 0; j < ToolRegistry.toolRodMaterials.length; j++) {
						if(ToolRegistry.toolRodMaterials[j] != null) {
							ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, -1, j, -1));
						}
					}
				}
			}
		}
	}

	@Override
	public void postInit() {
		
	}
}
