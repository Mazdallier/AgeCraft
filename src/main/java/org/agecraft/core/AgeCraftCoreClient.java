package org.agecraft.core;

import java.util.HashMap;

import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.registry.ArmorMaterialRegistry;
import org.agecraft.core.registry.ArmorMaterialRegistry.ArmorMaterial;
import org.agecraft.core.registry.ArmorTypeRegistry;
import org.agecraft.core.registry.ArmorTypeRegistry.ArmorType;
import org.agecraft.core.registry.DustRegistry;
import org.agecraft.core.registry.DustRegistry.Dust;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.MetalRegistry.Metal;
import org.agecraft.core.registry.MetalRegistry.OreType;
import org.agecraft.core.registry.ToolEnhancementMaterialRegistry;
import org.agecraft.core.registry.ToolEnhancementMaterialRegistry.ToolEnhancementMaterial;
import org.agecraft.core.registry.ToolMaterialRegistry;
import org.agecraft.core.registry.ToolMaterialRegistry.ToolMaterial;
import org.agecraft.core.registry.ToolRegistry;
import org.agecraft.core.registry.ToolRegistry.Tool;
import org.agecraft.core.registry.ToolRodMaterialRegistry;
import org.agecraft.core.registry.ToolRodMaterialRegistry.ToolRodMaterial;
import org.agecraft.core.registry.TreeRegistry;
import org.agecraft.core.registry.TreeRegistry.Tree;

import elcon.mods.elconqore.EQUtil;

public class AgeCraftCoreClient extends ACComponentClient {

	public static AgeCraftCoreClient instance;

	public static ResourceLocation guiIcons = new ResourceLocation("agecraft", "textures/gui/icons.png");
	public static ResourceLocation guiInventory = new ResourceLocation("agecraft", "textures/gui/inventory.png");
	public static ResourceLocation guiTrade = new ResourceLocation("agecraft", "textures/gui/trade.png");
	public static ResourceLocation guiChat = new ResourceLocation("agecraft", "textures/gui/chat.png");

	public static ResourceLocation guiClothingSelector = new ResourceLocation("agecraft", "textures/gui/clothing.png");
	public static ResourceLocation guiClothingSelectorSideBar = new ResourceLocation("agecraft", "textures/gui/clothing_sidebar.png");
	public static ResourceLocation guiClothingIcons = new ResourceLocation("agecraft", "textures/gui/clothing_icons.png");

	public static ResourceLocation guiTechTree = new ResourceLocation("agecraft", "textures/gui/tech_tree.png");
	public static ResourceLocation guiTechTreeIcons = new ResourceLocation("agecraft", "textures/gui/tech_tree_icons.png");

	public static ResourceLocation guiWorkbench = new ResourceLocation("agecraft", "textures/gui/crafting/workbench.png");
	public static ResourceLocation guiSmeltery = new ResourceLocation("agecraft", "textures/gui/crafting/smeltery.png");

	public static ResourceLocation ageTeleporterBeam = new ResourceLocation("agecraft", "textures/misc/beam.png");
	public static ResourceLocation ageTeleporterChest = new ResourceLocation("agecraft", "textures/tile/teleporter_chest.png");

	public static ResourceLocation gear = new ResourceLocation("agecraft", "textures/misc/gear.png");

	public static ResourceLocation[] arrowHeads = new ResourceLocation[ToolMaterialRegistry.instance.getAll().length];
	public static ResourceLocation[] arrowRods = new ResourceLocation[ToolRodMaterialRegistry.instance.getAll().length];
	public static ResourceLocation[] boltHeads = new ResourceLocation[ToolMaterialRegistry.instance.getAll().length];
	public static ResourceLocation[] boltRods = new ResourceLocation[ToolRodMaterialRegistry.instance.getAll().length];

	public static IIcon missingTexture;
	public static IIcon emptyTexture;
	public static IIcon iconLock;

	public static IIcon[][][] doorWoodIcons = new IIcon[4][2][2];
	public static IIcon[] trapdoorWoodIcons = new IIcon[2];

	public static IIcon[][][] doorMetalIcons = new IIcon[4][2][2];
	public static IIcon[] trapdoorMetalIcons = new IIcon[2];

	private static final int FLUID_CONTAINER_COUNT = 1;
	public static HashMap<String, IIcon[]> fluidContainerIcons = new HashMap<String, IIcon[]>();

	public AgeCraftCoreClient(ACComponent component) {
		super(component);
		instance = this;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		String[] doorTypes = new String[]{"Standard", "Solid", "Double", "Full"};
		for(int i = 0; i < doorTypes.length; i++) {
			doorWoodIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Lower");
			doorWoodIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Upper");
			doorWoodIcons[i][0][1] = new IconFlipped(doorWoodIcons[i][0][0], true, false);
			doorWoodIcons[i][1][1] = new IconFlipped(doorWoodIcons[i][1][0], true, false);
		}
		trapdoorWoodIcons[0] = iconRegister.registerIcon("agecraft:door/wood/trapdoorStandard");
		trapdoorWoodIcons[1] = iconRegister.registerIcon("agecraft:door/wood/trapdoorSolid");

		for(int i = 0; i < doorTypes.length; i++) {
			doorMetalIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Lower");
			doorMetalIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Upper");
			doorMetalIcons[i][0][1] = new IconFlipped(doorMetalIcons[i][0][0], true, false);
			doorMetalIcons[i][1][1] = new IconFlipped(doorMetalIcons[i][1][0], true, false);
		}
		trapdoorMetalIcons[0] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalStandard");
		trapdoorMetalIcons[1] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalSolid");

		// trees
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			Tree tree = TreeRegistry.instance.get(i);
			if(tree != null) {
				tree.wood = iconRegister.registerIcon("agecraft:wood/wood" + EQUtil.firstUpperCase(tree.name));
				tree.woodTop = iconRegister.registerIcon("agecraft:wood/woodTop" + EQUtil.firstUpperCase(tree.name));
				tree.logTop = iconRegister.registerIcon("agecraft:wood/logTop" + EQUtil.firstUpperCase(tree.name));
				tree.planks = iconRegister.registerIcon("agecraft:wood/planks" + EQUtil.firstUpperCase(tree.name));
				tree.leaves = iconRegister.registerIcon("agecraft:leaves/leaves" + EQUtil.firstUpperCase(tree.name));
				tree.leavesFast = iconRegister.registerIcon("agecraft:leaves/leavesFast" + EQUtil.firstUpperCase(tree.name));
				tree.smallSapling = iconRegister.registerIcon("agecraft:sapling/smallSapling" + EQUtil.firstUpperCase(tree.name));
				tree.sapling = iconRegister.registerIcon("agecraft:sapling/sapling" + EQUtil.firstUpperCase(tree.name));
			}
		}

		// metals
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			Metal metal = MetalRegistry.instance.get(i);
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/metals/ore" + EQUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.blocks[0] = iconRegister.registerIcon("agecraft:metals/blocks/metals/block" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[1] = iconRegister.registerIcon("agecraft:metals/blocks/metals/bricks" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[2] = iconRegister.registerIcon("agecraft:metals/blocks/metals/bricksSmall" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[3] = iconRegister.registerIcon("agecraft:metals/blocks/metals/blockCircle" + EQUtil.firstUpperCase(metal.name));
						metal.blockPillar = iconRegister.registerIcon("agecraft:metals/blocks/metals/pillar" + EQUtil.firstUpperCase(metal.name));
						metal.blockPillarTop = iconRegister.registerIcon("agecraft:metals/blocks/metals/pillarTop" + EQUtil.firstUpperCase(metal.name));
					}
					metal.fluid.setIcons(iconRegister.registerIcon("agecraft:metals/fluids/metals/fluid" + EQUtil.firstUpperCase(metal.name)));
				} else if(metal.type == OreType.GEM) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/gems/ore" + EQUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.blocks[0] = iconRegister.registerIcon("agecraft:metals/blocks/gems/block" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[1] = iconRegister.registerIcon("agecraft:metals/blocks/gems/bricks" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[2] = iconRegister.registerIcon("agecraft:metals/blocks/gems/bricksSmall" + EQUtil.firstUpperCase(metal.name));
						metal.blocks[3] = iconRegister.registerIcon("agecraft:metals/blocks/gems/blockCircle" + EQUtil.firstUpperCase(metal.name));
						metal.blockPillar = iconRegister.registerIcon("agecraft:metals/blocks/gems/pillar" + EQUtil.firstUpperCase(metal.name));
						metal.blockPillarTop = iconRegister.registerIcon("agecraft:metals/blocks/gems/pillarTop" + EQUtil.firstUpperCase(metal.name));
					}
					metal.fluid.setIcons(iconRegister.registerIcon("agecraft:metals/fluids/gems/fluid" + EQUtil.firstUpperCase(metal.name)));
				}
			}
		}

		// dusts
		for(int i = 0; i < DustRegistry.instance.getAll().length; i++) {
			Dust dust = DustRegistry.instance.get(i);
			if(dust != null) {
				dust.icon = iconRegister.registerIcon("agecraft:dusts/dust" + EQUtil.firstUpperCase(dust.name));
			}
		}
	}

	@Override
	public void registerItemIcons(IIconRegister iconRegister) {
		missingTexture = iconRegister.registerIcon("agecraft:missingTexture");
		emptyTexture = iconRegister.registerIcon("agecraft:emptyTexture");
		iconLock = iconRegister.registerIcon("agecraft:lock");
		
		// trees
		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			Tree tree = TreeRegistry.instance.get(i);
			if(tree != null) {
				tree.log = iconRegister.registerIcon("agecraft:wood/logs/log" + EQUtil.firstUpperCase(tree.name));
				tree.stick = iconRegister.registerIcon("agecraft:wood/sticks/stick" + EQUtil.firstUpperCase(tree.name));
				tree.dust = iconRegister.registerIcon("agecraft:wood/dusts/dust" + EQUtil.firstUpperCase(tree.name));
				tree.bucket = iconRegister.registerIcon("agecraft:wood/buckets/bucket" + EQUtil.firstUpperCase(tree.name));
			}
		}

		// metals
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			Metal metal = MetalRegistry.instance.get(i);
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/ingots/ingot" + EQUtil.firstUpperCase(metal.name));
						metal.stick = iconRegister.registerIcon("agecraft:metals/sticks/stick" + EQUtil.firstUpperCase(metal.name));
						metal.nugget = iconRegister.registerIcon("agecraft:metals/nuggets/nugget" + EQUtil.firstUpperCase(metal.name));
						if(metal.hasDust) {
							metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/metals/dust" + EQUtil.firstUpperCase(metal.name));
						}
						if(ToolMaterialRegistry.instance.get(128 + metal.id) != null) {
							metal.bucket = iconRegister.registerIcon("agecraft:metals/buckets/bucket" + EQUtil.firstUpperCase(metal.name));
						}
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/gems/gem" + EQUtil.firstUpperCase(metal.name));
					}
					if(metal.hasDust) {
						metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/gems/dust" + EQUtil.firstUpperCase(metal.name));
					}
				}
			}
		}

		// tools
		for(int i = 0; i < ToolRegistry.instance.getAll().length; i++) {
			Tool tool = ToolRegistry.instance.get(i);
			if(tool != null) {
				tool.outline = iconRegister.registerIcon("agecraft:tools/outline/" + tool.name);
				if(tool.id != 17 && tool.id != 18 && tool.id != 19 && tool.id != 20 && tool.id != 21) {
					if(tool.hasHead) {
						for(int j = 0; j < ToolMaterialRegistry.instance.getAll().length; j++) {
							ToolMaterial toolMaterial = ToolMaterialRegistry.instance.get(j);
							if(toolMaterial != null) {
								toolMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/" + tool.name + "/" + tool.name + EQUtil.firstUpperCase(toolMaterial.name));
							}
						}
					}
					if(tool.hasRod) {
						for(int j = 0; j < ToolRodMaterialRegistry.instance.getAll().length; j++) {
							ToolRodMaterial toolRodMaterial = ToolRodMaterialRegistry.instance.get(j);
							if(toolRodMaterial != null) {
								toolRodMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/sticks/" + tool.name + "/" + tool.name + EQUtil.firstUpperCase(toolRodMaterial.name));
							}
						}
					}
				}
				if(tool.hasEnhancements) {
					for(int j = 0; j < ToolEnhancementMaterialRegistry.instance.getAll().length; j++) {
						ToolEnhancementMaterial toolEnhancementMaterial = ToolEnhancementMaterialRegistry.instance.get(j);
						if(toolEnhancementMaterial != null) {
							toolEnhancementMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/enhancements/" + tool.name + "/" + tool.name + EQUtil.firstUpperCase(toolEnhancementMaterial.name));
						}
					}
				}
			}
		}

		// armor
		for(int i = 0; i < ArmorTypeRegistry.instance.getAll().length; i++) {
			ArmorType armorType = ArmorTypeRegistry.instance.get(i);
			if(armorType != null) {
				armorType.backgroundIcon = iconRegister.registerIcon("agecraft:armor/slots/slot" + EQUtil.firstUpperCase(armorType.name));
				for(int j = 0; j < ArmorMaterialRegistry.instance.getAll().length; j++) {
					ArmorMaterial armorMaterial = ArmorMaterialRegistry.instance.get(j);
					if(armorMaterial != null) {
						armorMaterial.icons[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + EQUtil.firstUpperCase(armorMaterial.name));
						if(armorMaterial.hasOverlay) {
							armorMaterial.iconsOverlay[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + EQUtil.firstUpperCase(armorMaterial.name) + "Overlay");
						}
					}
				}
			}
		}

		// fluids
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			if(fluid != null) {
				registerContainerIcon(fluid.getName(), 0, iconRegister.registerIcon("agecraft:fluids/bucket/" + fluid.getName()));
			}
		}
	}

	@Override
	public void postInit() {
		// arrow
		for(int i = 0; i < ToolMaterialRegistry.instance.getAll().length; i++) {
			ToolMaterial toolMaterial = ToolMaterialRegistry.instance.get(i);
			if(toolMaterial != null) {
				arrowHeads[i] = new ResourceLocation("agecraft", "textures/entity/arrow/arrowHead" + EQUtil.firstUpperCase(toolMaterial.name) + ".png");
				boltHeads[i] = new ResourceLocation("agecraft", "textures/entity/bolt/boltHead" + EQUtil.firstUpperCase(toolMaterial.name) + ".png");
			}
		}
		for(int i = 0; i < ToolRodMaterialRegistry.instance.getAll().length; i++) {
			ToolRodMaterial toolRodMaterial = ToolRodMaterialRegistry.instance.get(i);
			if(toolRodMaterial != null) {
				arrowRods[i] = new ResourceLocation("agecraft", "textures/entity/arrow/arrowRod" + EQUtil.firstUpperCase(toolRodMaterial.name) + ".png");
				boltRods[i] = new ResourceLocation("agecraft", "textures/entity/bolt/boltRod" + EQUtil.firstUpperCase(toolRodMaterial.name) + ".png");
			}
		}
	}

	public void registerContainerIcon(String name, int type, IIcon icon) {
		if(!fluidContainerIcons.containsKey(name)) {
			fluidContainerIcons.put(name, new IIcon[FLUID_CONTAINER_COUNT]);
		}
		if(type < FLUID_CONTAINER_COUNT) {
			fluidContainerIcons.get(name)[type] = icon;
		}
	}
}
