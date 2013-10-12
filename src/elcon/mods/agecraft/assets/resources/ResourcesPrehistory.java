package elcon.mods.agecraft.assets.resources;

import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.core.ElConCore;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.ResourceLocation;

public class ResourcesPrehistory extends Resources {
	
	public static ResourceLocation guiSharpener = new ResourceLocation("agecraft", "textures/gui/sharpener.png");

	public static ResourceLocation[] campfire = new ResourceLocation[TreeRegistry.trees.length];
	
	@Override
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				campfire[i] = new ResourceLocation("agecraft", "textures/tile/prehistory/campfire" + ElConCore.firstUpperCase(TreeRegistry.trees[i].name) + ".png");
			}
		}
	}
}
