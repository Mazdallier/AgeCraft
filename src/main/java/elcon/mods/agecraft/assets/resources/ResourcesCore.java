package elcon.mods.agecraft.assets.resources;

import java.util.HashMap;

import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.ArmorRegistry;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorMaterial;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.agecraft.core.DustRegistry;
import elcon.mods.agecraft.core.DustRegistry.Dust;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.MetalRegistry.Metal;
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolEnhancementMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.core.ECUtil;

@SideOnly(Side.CLIENT)
public class ResourcesCore extends Resources {
	
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
	
	public static ResourceLocation[] arrowHeads = new ResourceLocation[ToolRegistry.toolMaterials.length];
	public static ResourceLocation[] arrowRods = new ResourceLocation[ToolRegistry.toolRodMaterials.length];
	public static ResourceLocation[] boltHeads = new ResourceLocation[ToolRegistry.toolMaterials.length];
	public static ResourceLocation[] boltRods = new ResourceLocation[ToolRegistry.toolRodMaterials.length];
	
	public static Icon missingTexture;
	public static Icon emptyTexture;
	public static Icon iconLock;
	
	public static Icon[][][] doorWoodIcons = new Icon[4][2][2];
	public static Icon[] trapdoorWoodIcons = new Icon[2];
	
	public static Icon[][][] doorMetalIcons = new Icon[4][2][2];
	public static Icon[] trapdoorMetalIcons = new Icon[2];
	
	private static final int FLUID_CONTAINER_COUNT = 1;
	public static HashMap<String, Icon[]> fluidContainerIcons = new HashMap<String, Icon[]>();
	
	@Override
	public void registerBlockIcons(IconRegister iconRegister) {
		String[] doorTypes = new String[]{"Standard", "Solid", "Double", "Full"};
		for(int i = 0; i < doorTypes.length; i++) {
			ResourcesCore.doorWoodIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Lower");
			ResourcesCore.doorWoodIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Upper");
			ResourcesCore.doorWoodIcons[i][0][1] = new IconFlipped(ResourcesCore.doorWoodIcons[i][0][0], true, false);
			ResourcesCore.doorWoodIcons[i][1][1] = new IconFlipped(ResourcesCore.doorWoodIcons[i][1][0], true, false);
		}
		ResourcesCore.trapdoorWoodIcons[0] = iconRegister.registerIcon("agecraft:door/wood/trapdoorStandard");
		ResourcesCore.trapdoorWoodIcons[1] = iconRegister.registerIcon("agecraft:door/wood/trapdoorSolid");
		
		for(int i = 0; i < doorTypes.length; i++) {
			ResourcesCore.doorMetalIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Lower");
			ResourcesCore.doorMetalIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Upper");
			ResourcesCore.doorMetalIcons[i][0][1] = new IconFlipped(ResourcesCore.doorMetalIcons[i][0][0], true, false);
			ResourcesCore.doorMetalIcons[i][1][1] = new IconFlipped(ResourcesCore.doorMetalIcons[i][1][0], true, false);
		}
		ResourcesCore.trapdoorMetalIcons[0] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalStandard");
		ResourcesCore.trapdoorMetalIcons[1] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalSolid");
		
		//trees
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.wood = iconRegister.registerIcon("agecraft:wood/wood" + ECUtil.firstUpperCase(tree.name));
				tree.woodTop = iconRegister.registerIcon("agecraft:wood/woodTop" + ECUtil.firstUpperCase(tree.name));
				tree.logTop = iconRegister.registerIcon("agecraft:wood/logTop" + ECUtil.firstUpperCase(tree.name));
				tree.planks = iconRegister.registerIcon("agecraft:wood/planks" + ECUtil.firstUpperCase(tree.name));
				tree.leaves = iconRegister.registerIcon("agecraft:leaves/leaves" + ECUtil.firstUpperCase(tree.name));
				tree.leavesFast = iconRegister.registerIcon("agecraft:leaves/leavesFast" + ECUtil.firstUpperCase(tree.name));
				tree.smallSapling = iconRegister.registerIcon("agecraft:sapling/smallSapling" + ECUtil.firstUpperCase(tree.name));
				tree.sapling = iconRegister.registerIcon("agecraft:sapling/sapling" + ECUtil.firstUpperCase(tree.name));
			}
		}
		
		//metals
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/metals/ore" + ECUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.blocks[0] = iconRegister.registerIcon("agecraft:metals/blocks/metals/block" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[1] = iconRegister.registerIcon("agecraft:metals/blocks/metals/bricks" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[2] = iconRegister.registerIcon("agecraft:metals/blocks/metals/bricksSmall" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[3] = iconRegister.registerIcon("agecraft:metals/blocks/metals/blockCircle" + ECUtil.firstUpperCase(metal.name));
						metal.blockPillar = iconRegister.registerIcon("agecraft:metals/blocks/metals/pillar" + ECUtil.firstUpperCase(metal.name));
						metal.blockPillarTop = iconRegister.registerIcon("agecraft:metals/blocks/metals/pillarTop" + ECUtil.firstUpperCase(metal.name));
					}
					metal.fluid.setIcons(iconRegister.registerIcon("agecraft:metals/fluids/metals/fluid" + ECUtil.firstUpperCase(metal.name)));
				} else if(metal.type == OreType.GEM) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/gems/ore" + ECUtil.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.blocks[0] = iconRegister.registerIcon("agecraft:metals/blocks/gems/block" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[1] = iconRegister.registerIcon("agecraft:metals/blocks/gems/bricks" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[2] = iconRegister.registerIcon("agecraft:metals/blocks/gems/bricksSmall" + ECUtil.firstUpperCase(metal.name));
						metal.blocks[3] = iconRegister.registerIcon("agecraft:metals/blocks/gems/blockCircle" + ECUtil.firstUpperCase(metal.name));
						metal.blockPillar = iconRegister.registerIcon("agecraft:metals/blocks/gems/pillar" + ECUtil.firstUpperCase(metal.name));
						metal.blockPillarTop = iconRegister.registerIcon("agecraft:metals/blocks/gems/pillarTop" + ECUtil.firstUpperCase(metal.name));
					}
					metal.fluid.setIcons(iconRegister.registerIcon("agecraft:metals/fluids/gems/fluid" + ECUtil.firstUpperCase(metal.name)));
				}
			}
		}
		
		//dusts
		for(int i = 0; i < DustRegistry.dusts.length; i++) {
			Dust dust = DustRegistry.dusts[i];
			if(dust != null) {
				dust.icon = iconRegister.registerIcon("agecraft:dusts/dust" + ECUtil.firstUpperCase(dust.name));
			}
		}
	}
	
	@Override
	public void registerItemIcons(IconRegister iconRegister) {
		missingTexture = iconRegister.registerIcon("agecraft:missingTexture");
		emptyTexture = iconRegister.registerIcon("agecraft:emptyTexture");
		iconLock = iconRegister.registerIcon("agecraft:lock");
		
		//trees
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.log = iconRegister.registerIcon("agecraft:wood/logs/log" + ECUtil.firstUpperCase(tree.name));
				tree.stick = iconRegister.registerIcon("agecraft:wood/sticks/stick" + ECUtil.firstUpperCase(tree.name));
				tree.dust = iconRegister.registerIcon("agecraft:wood/dusts/dust" + ECUtil.firstUpperCase(tree.name));
				tree.bucket = iconRegister.registerIcon("agecraft:wood/buckets/bucket" + ECUtil.firstUpperCase(tree.name));
			}
		}
		
		//metals
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/ingots/ingot" + ECUtil.firstUpperCase(metal.name));
						metal.stick = iconRegister.registerIcon("agecraft:metals/sticks/stick" + ECUtil.firstUpperCase(metal.name));
						metal.nugget = iconRegister.registerIcon("agecraft:metals/nuggets/nugget" + ECUtil.firstUpperCase(metal.name));
						if(metal.hasDust) {
							metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/metals/dust" + ECUtil.firstUpperCase(metal.name));
						}
						if(ToolRegistry.toolMaterials[128 + metal.id] != null) {
							metal.bucket = iconRegister.registerIcon("agecraft:metals/buckets/bucket" + ECUtil.firstUpperCase(metal.name));
						}
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/gems/gem" + ECUtil.firstUpperCase(metal.name));	
					}
					if(metal.hasDust) {
						metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/gems/dust" + ECUtil.firstUpperCase(metal.name));
					}
				}
			}
		}
		
		//tools
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			Tool tool = ToolRegistry.tools[i];
			if(tool != null) {
				tool.outline = iconRegister.registerIcon("agecraft:tools/outline/" + tool.name);
				if(tool.id != 17 && tool.id != 18 && tool.id != 19 && tool.id != 20 && tool.id != 21) {
					if(tool.hasHead) {
						for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
							ToolMaterial toolMaterial = ToolRegistry.toolMaterials[j];
							if(toolMaterial != null) {
								toolMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/" + tool.name + "/" + tool.name + ECUtil.firstUpperCase(toolMaterial.name));
							}
						}
					}
					if(tool.hasRod) {
						for(int j = 0; j < ToolRegistry.toolRodMaterials.length; j++) {
							ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[j];
							if(toolRodMaterial != null) {
								toolRodMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/sticks/" + tool.name + "/" + tool.name + ECUtil.firstUpperCase(toolRodMaterial.name));
							}
						}
					}
				}
				if(tool.hasEnhancements) {
					for(int j = 0; j < ToolRegistry.toolEnhancementMaterials.length; j++) {
						ToolEnhancementMaterial toolEnhancementMaterial = ToolRegistry.toolEnhancementMaterials[j];
						if(toolEnhancementMaterial != null) {
							toolEnhancementMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/enhancements/" + tool.name + "/" + tool.name + ECUtil.firstUpperCase(toolEnhancementMaterial.name));
						}
					}
				}
			}
		}
		
		//armor
		for(int i = 0; i < ArmorRegistry.armorTypes.length; i++) {
			ArmorType armorType = ArmorRegistry.armorTypes[i];
			if(armorType != null) {
				armorType.backgroundIcon = iconRegister.registerIcon("agecraft:armor/slots/slot" + ECUtil.firstUpperCase(armorType.name));
				for(int j = 0; j < ArmorRegistry.armorMaterials.length; j++) {
					ArmorMaterial armorMaterial = ArmorRegistry.armorMaterials[j];
					if(armorMaterial != null) {
						armorMaterial.icons[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + ECUtil.firstUpperCase(armorMaterial.name));
						if(armorMaterial.hasOverlay) {
							armorMaterial.iconsOverlay[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + ECUtil.firstUpperCase(armorMaterial.name) + "Overlay");
						}
					}
				}
			}
		}
		
		//fluids
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			if(fluid != null) {
				registerContainerIcon(fluid.getName(), 0, iconRegister.registerIcon("agecraft:fluids/bucket/" + fluid.getName()));
			}
		}
	}
	
	@Override
	public void postInit() {
		//arrow
		for(int i = 0; i < ToolRegistry.toolMaterials.length; i++) {
			ToolMaterial toolMaterial = ToolRegistry.toolMaterials[i];
			if(toolMaterial != null) {
				arrowHeads[i] = new ResourceLocation("agecraft", "textures/entity/arrow/arrowHead" + ECUtil.firstUpperCase(toolMaterial.name) + ".png");
				boltHeads[i] = new ResourceLocation("agecraft", "textures/entity/bolt/boltHead" + ECUtil.firstUpperCase(toolMaterial.name) + ".png");
			}
		}
		for(int i = 0; i < ToolRegistry.toolRodMaterials.length; i++) {
			ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[i];
			if(toolRodMaterial != null) {
				arrowRods[i] = new ResourceLocation("agecraft", "textures/entity/arrow/arrowRod" + ECUtil.firstUpperCase(toolRodMaterial.name) + ".png");
				boltRods[i] = new ResourceLocation("agecraft", "textures/entity/bolt/boltRod" + ECUtil.firstUpperCase(toolRodMaterial.name) + ".png");
			}
		}
	}

	public void registerContainerIcon(String name, int type, Icon icon) {
		if(!fluidContainerIcons.containsKey(name)) {
			fluidContainerIcons.put(name, new Icon[FLUID_CONTAINER_COUNT]);
		}
		if(type < FLUID_CONTAINER_COUNT) {
			fluidContainerIcons.get(name)[type] = icon;
		}
	}
}
