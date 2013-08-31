package elcon.mods.agecraft.assets.resources;

import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class ResourcesCore {
	
	public static ResourceLocation guiTechTree = new ResourceLocation("agecraft", "textures/gui/tech_tree.png");
	public static ResourceLocation guiTechTreeIcons = new ResourceLocation("agecraft", "textures/gui/tech_tree_icons.png");
	
	public static ResourceLocation ageTeleporterBeam = new ResourceLocation("agecraft", "textures/misc/beam.png");
	public static ResourceLocation ageTeleporterChest = new ResourceLocation("agecraft", "textures/tile/teleporter_chest.png");
	
	public static Icon missingTexture;
	public static Icon emptyTexture;
	
	public static Icon[][][] doorWoodIcons = new Icon[4][2][2];
	public static Icon[] trapdoorWoodIcons = new Icon[2];
	
	public static Icon[][][] doorMetalIcons = new Icon[4][2][2];
	public static Icon[] trapdoorMetalIcons = new Icon[2];
}
