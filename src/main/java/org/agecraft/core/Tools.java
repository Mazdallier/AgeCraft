package org.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import org.agecraft.ACComponent;
import org.agecraft.AgeCraft;
import org.agecraft.core.entity.EntityArrow;
import org.agecraft.core.entity.EntityBolt;
import org.agecraft.core.items.tools.ItemArrow;
import org.agecraft.core.items.tools.ItemAxe;
import org.agecraft.core.items.tools.ItemBattleAxe;
import org.agecraft.core.items.tools.ItemBolt;
import org.agecraft.core.items.tools.ItemBow;
import org.agecraft.core.items.tools.ItemChisel;
import org.agecraft.core.items.tools.ItemCrossbow;
import org.agecraft.core.items.tools.ItemDagger;
import org.agecraft.core.items.tools.ItemFishingRod;
import org.agecraft.core.items.tools.ItemHammer;
import org.agecraft.core.items.tools.ItemHandSaw;
import org.agecraft.core.items.tools.ItemHoe;
import org.agecraft.core.items.tools.ItemKnife;
import org.agecraft.core.items.tools.ItemMace;
import org.agecraft.core.items.tools.ItemPickaxe;
import org.agecraft.core.items.tools.ItemPitchfork;
import org.agecraft.core.items.tools.ItemSaw;
import org.agecraft.core.items.tools.ItemShovel;
import org.agecraft.core.items.tools.ItemSickle;
import org.agecraft.core.items.tools.ItemSpear;
import org.agecraft.core.items.tools.ItemSword;
import org.agecraft.core.items.tools.ItemTool;
import org.agecraft.core.items.tools.ItemWarhammer;
import org.agecraft.core.registry.ToolMaterialRegistry;
import org.agecraft.core.registry.ToolMaterialRegistry.ToolMaterial;
import org.agecraft.core.registry.ToolRegistry;
import org.agecraft.core.registry.ToolRegistry.Tool;
import org.agecraft.core.registry.ToolRegistry.ToolCreativeEntry;
import org.agecraft.core.registry.ToolRodMaterialRegistry;
import org.agecraft.core.registry.ToolRodMaterialRegistry.ToolRodMaterial;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
	public static ItemTool pitchfork;
	public static ItemTool hammer;
	public static ItemTool chisel;
	public static ItemTool handsaw;
	public static ItemTool saw;
	public static ItemTool bow;
	public static ItemTool crossbow;
	public static ItemTool arrow;
	public static ItemTool bolt;
	public static ItemTool fishingRod;

	public Tools() {
		super("tools", false);
	}
	
	@Override
	public void preInit() {
		//init items
		sword = (ItemTool) new ItemSword().setUnlocalizedName("AC_tools_sword");
		pickaxe = (ItemTool) new ItemPickaxe().setUnlocalizedName("AC_tools_pickaxe");
		axe = (ItemTool) new ItemAxe().setUnlocalizedName("AC_tools_axe");
		shovel = (ItemTool) new ItemShovel().setUnlocalizedName("AC_tools_shovel");
		hoe = (ItemTool) new ItemHoe().setUnlocalizedName("AC_tools_hoe");
		battleaxe = (ItemTool) new ItemBattleAxe().setUnlocalizedName("AC_tools_battleAxe");
		warhammer = (ItemTool) new ItemWarhammer().setUnlocalizedName("AC_tools_warhammer");
		mace = (ItemTool) new ItemMace().setUnlocalizedName("AC_tools_mace");
		dagger = (ItemTool) new ItemDagger().setUnlocalizedName("AC_tools_dagger");
		knife = (ItemTool) new ItemKnife().setUnlocalizedName("AC_tools_knife");
		spear = (ItemTool) new ItemSpear().setUnlocalizedName("AC_tools_spear");
		sickle = (ItemTool) new ItemSickle().setUnlocalizedName("AC_tools_sickle");
		pitchfork = (ItemTool) new ItemPitchfork().setUnlocalizedName("AC_tools_pitchfork");
		hammer = (ItemTool) new ItemHammer().setUnlocalizedName("AC_tools_hammer");
		chisel = (ItemTool) new ItemChisel().setUnlocalizedName("AC_tools_chisel");
		handsaw = (ItemTool) new ItemHandSaw().setUnlocalizedName("AC_tools_handsaw");
		saw = (ItemTool) new ItemSaw().setUnlocalizedName("AC_tools_saw");
		bow = (ItemTool) new ItemBow().setUnlocalizedName("AC_tools_bow");
		crossbow = (ItemTool) new ItemCrossbow().setUnlocalizedName("AC_tools_crossbow");
		arrow = (ItemTool) new ItemArrow().setUnlocalizedName("AC_tools_arrow");
		bolt = (ItemTool) new ItemBolt().setUnlocalizedName("AC_tools_bolt");
		fishingRod = (ItemTool) new ItemFishingRod().setUnlocalizedName("AC_tools_fishingRod");
		
		//register items
		GameRegistry.registerItem(sword, "AC_tools_sword");
		GameRegistry.registerItem(pickaxe, "AC_tools_pickaxe");
		GameRegistry.registerItem(axe, "AC_tools_axe");
		GameRegistry.registerItem(shovel, "AC_tools_shovel");
		GameRegistry.registerItem(hoe, "AC_tools_hoe");
		GameRegistry.registerItem(battleaxe, "AC_tools_battleaxe");
		GameRegistry.registerItem(warhammer, "AC_tools_warhammer");
		GameRegistry.registerItem(mace, "AC_tools_mace");
		GameRegistry.registerItem(dagger, "AC_tools_dagger");
		GameRegistry.registerItem(knife, "AC_tools_knife");
		GameRegistry.registerItem(spear, "AC_tools_spear");
		GameRegistry.registerItem(sickle, "AC_tools_sickle");
		GameRegistry.registerItem(pitchfork, "AC_tools_pitchfork");
		GameRegistry.registerItem(hammer, "AC_tools_hammer");
		GameRegistry.registerItem(chisel, "AC_tools_chisel");
		GameRegistry.registerItem(handsaw, "AC_tools_handsaw");
		GameRegistry.registerItem(saw, "AC_tools_saw");
		GameRegistry.registerItem(bow, "AC_tools_bow");
		GameRegistry.registerItem(crossbow, "AC_tools_crossbow");
		GameRegistry.registerItem(arrow, "AC_tools_arrow");
		GameRegistry.registerItem(bolt, "AC_tools_bolt");
		GameRegistry.registerItem(fishingRod, "AC_tools_fishingRod");
		
		//register entities
		EntityRegistry.registerModEntity(EntityArrow.class, "AC_Arrow", EntityRegistry.findGlobalUniqueEntityId(), AgeCraft.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityBolt.class, "AC_Bolt", EntityRegistry.findGlobalUniqueEntityId(), AgeCraft.instance, 64, 1, true);
		
		// register tools
		ToolRegistry.registerTool(new Tool(0, "sword", sword, 2, 1, true, true, true, new Block[]{
			Blocks.web
		}));
		ToolRegistry.registerTool(new Tool(1, "pickaxe", pickaxe, 1, 2, true, true, true, new Block[]{
			Stone.stone, Stone.stoneCracked, Stone.stoneMossy, Stone.stoneBrick, Stone.stoneBrickPillar, Stone.coloredStone, Stone.coloredStoneCracked, Stone.coloredStoneMossy, Stone.coloredStoneBrick, Stone.coloredStoneBrickPillar, 
			Metals.ore, Metals.block, Metals.fence, Metals.fenceGate, Metals.door, Metals.trapdoor, Metals.ladder, Crafting.anvil
		}));
		ToolRegistry.registerTool(new Tool(2, "axe", axe, 1, 2, true, true, true, new Block[]{
			Trees.wood, Trees.log, Trees.woodWall, Trees.planks, Trees.fence, Trees.fenceGate, Trees.door, Trees.trapdoor, Trees.ladder
		}));
		ToolRegistry.registerTool(new Tool(3, "shovel", shovel, 1, 2, true, true, true, new Block[]{
			Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow, Blocks.snow_layer
		}));
		ToolRegistry.registerTool(new Tool(4, "hoe", hoe, 2, 2, true, true, true, new Block[]{
		}));
		ToolRegistry.registerTool(new Tool(5, "battleaxe", battleaxe, 1, 1, true, true, true, ToolRegistry.instance.get(2).blocksEffectiveAgainst));
		ToolRegistry.registerTool(new Tool(6, "warhammer", warhammer, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(7, "mace", mace, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(8, "dagger", dagger, 2, 1, true, true, true, new Block[]{	
			Blocks.web
		}));
		ToolRegistry.registerTool(new Tool(9, "knife", knife, 2, 1, true, true, true, new Block[]{	
			Blocks.web
		}));
		ToolRegistry.registerTool(new Tool(10, "spear", spear, 2, 1, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(11, "sickle", sickle, 1, 2, true, true, true, new Block[]{
			Blocks.web, Blocks.tallgrass, Blocks.deadbush, Blocks.vine, Trees.leaves, Trees.leavesDNA, Trees.saplingDNA
		}));
		ToolRegistry.registerTool(new Tool(12, "pitchfork", pitchfork, 1, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(13, "hammer", hammer, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(14, "chisel", chisel, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(15, "handsaw", handsaw, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(16, "saw", saw, 2, 2, true, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(17, "bow", bow, 2, 1, false, true, true, new Block[]{	
		}));
		ToolRegistry.registerTool(new Tool(18, "crossbow", crossbow, 2, 1, false, true, true, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(19, "arrow", arrow, 0, 0, true, true, false, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(20, "bolt", bolt, 0, 0, true, true, false, new Block[] {
		}));
		ToolRegistry.registerTool(new Tool(21, "fishingRod", fishingRod, 2, 2, false, true, true, new Block[]{	
		}));

		// register tool materials
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(127, "stone", "tools.materials.stone", new ItemStack(Stone.stone, 1, 1), 138, 1.5F, 2, 1));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), 144, 2.25F, 2, 3));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), 166, 3.0F, 3, 4));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), 156, 3.75F, 4, 2));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), 189, 4.5F, 5, 5));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), 24, 10.5F, 1, 0));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), 266, 5.25F, 6, 6));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), 492, 6.0F, 7, 7));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), 701, 6.75F, 8, 8));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), 1084, 7.5F, 9, 9));
		ToolMaterialRegistry.registerToolMaterial(new ToolMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), 1411, 8.25F, 10, 10));

		// register tool rod materials
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(0, "woodAcacia", "tools.materials.woodAcacia", new ItemStack(Trees.stick, 1, 0), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(1, "woodAlder", "tools.materials.woodAlder", new ItemStack(Trees.stick, 1, 1), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(2, "woodBeech", "tools.materials.woodBeech", new ItemStack(Trees.stick, 1, 2), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(3, "woodBirch", "tools.materials.woodBirch", new ItemStack(Trees.stick, 1, 3), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(4, "woodBuckeye", "tools.materials.woodBuckeye", new ItemStack(Trees.stick, 1, 4), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(5, "woodCherryBlossom", "tools.materials.woodCherryBlossom", new ItemStack(Trees.stick, 1, 5), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(6, "woodConiferous", "tools.materials.woodConiferous", new ItemStack(Trees.stick, 1, 6), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(7, "woodDarkOak", "tools.materials.woodDarkOak", new ItemStack(Trees.stick, 1, 7), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(8, "woodDogwood", "tools.materials.woodDogwood", new ItemStack(Trees.stick, 1, 8), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(9, "woodElm", "tools.materials.woodElm", new ItemStack(Trees.stick, 1, 9), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(10, "woodEucalyptus", "tools.materials.woodEucalyptus", new ItemStack(Trees.stick, 1, 10), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(11, "woodFir", "tools.materials.woodFir", new ItemStack(Trees.stick, 1, 11), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(12, "woodFilbert", "tools.materials.woodFilbert", new ItemStack(Trees.stick, 1, 12), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(13, "woodJungle", "tools.materials.woodJungle", new ItemStack(Trees.stick, 1, 13), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(14, "woodMaple", "tools.materials.woodMaple", new ItemStack(Trees.stick, 1, 14), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(15, "woodOak", "tools.materials.woodOak", new ItemStack(Trees.stick, 1, 16), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(16, "woodPalm", "tools.materials.woodPalm", new ItemStack(Trees.stick, 1, 16), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(17, "woodPine", "tools.materials.woodPine", new ItemStack(Trees.stick, 1, 17), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(18, "woodPoplar", "tools.materials.woodPoplar", new ItemStack(Trees.stick, 1, 18), 27, 0.25F, 0));		
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(19, "woodSpruce", "tools.materials.woodSpruce", new ItemStack(Trees.stick, 1, 19), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(20, "woodTupelo", "tools.materials.woodTupelo", new ItemStack(Trees.stick, 1, 20), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(21, "woodWillow", "tools.materials.woodWillow", new ItemStack(Trees.stick, 1, 21), 27, 0.25F, 0));		
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(22, "woodYew", "tools.materials.woodYew", new ItemStack(Trees.stick, 1, 22), 27, 0.25F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(23, "woodYellow", "tools.materials.woodYellow", new ItemStack(Trees.stick, 1, 23), 27, 0.25F, 0));

		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(128, "copper", "metals.copper", new ItemStack(Metals.stick, 1, 0), 92, 0.75F, 1));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.stick, 1, 2), 111, 1.0F, 2));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(131, "silver", "metals.silver", new ItemStack(Metals.stick, 1, 3), 104, 1.25F, 2));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(132, "iron", "metals.iron", new ItemStack(Metals.stick, 1, 4), 125, 1.5F, 3));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(133, "gold", "metals.gold", new ItemStack(Metals.stick, 1, 5), 16, 3.0F, 0));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.stick, 1, 9), 177, 1.75F, 3));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(139, "steel", "metals.steel", new ItemStack(Metals.stick, 1, 11), 328, 2.0F, 4));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.stick, 1, 12), 467, 2.25F, 4));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.stick, 1, 13), 723, 2.5F, 5));
		ToolRodMaterialRegistry.registerToolRodMaterial(new ToolRodMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.stick, 1, 14), 941, 2.75F, 5));

		// register tool creative entries
		for(int i = 0; i < ToolRegistry.instance.getAll().length; i++) {
			if(ToolRegistry.instance.get(i) != null) {
				Tool tool = ToolRegistry.instance.get(i);
				if(tool.hasHead) {
					for(int j = 0; j < ToolMaterialRegistry.instance.getAll().length; j++) {
						if(ToolMaterialRegistry.instance.get(j) != null) {
							if(tool.hasRod) {
								if(j == 127) {
									ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, 15, -1));
								} else {
									ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, j, -1));
								}
							} else {
								ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, j, -1, -1));
							}
						}
					}
				} else {
					for(int j = 0; j < ToolRodMaterialRegistry.instance.getAll().length; j++) {
						if(ToolRodMaterialRegistry.instance.get(j) != null) {
							ToolRegistry.registerToolCreativeEntry(new ToolCreativeEntry(i, -1, j, -1));
						}
					}
				}
			}
		}
	}
}
