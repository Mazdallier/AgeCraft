package elcon.mods.agecraft.core.items.tools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.core.ECUtil;

public class ItemArrow extends ItemTool {

	public Icon[][] iconsHead = new Icon[256][4];
	public Icon[][] iconsRod = new Icon[256][4];
	
	public ItemArrow(int id) {
		super(id);
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(pass == 0) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && ToolRegistry.toolRodMaterials[toolRodMaterial] != null) {
				return iconsRod[toolRodMaterial][0];
			}
		} else if(pass == 1) {
			int toolMaterial = getToolMaterial(stack);
			if(toolMaterial != -1 && ToolRegistry.toolMaterials[toolMaterial] != null) {
				return iconsHead[toolMaterial][0];
			}
		}
		return ResourcesCore.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < ToolRegistry.toolMaterials.length; i++) {
			ToolMaterial toolMaterial = ToolRegistry.toolMaterials[i];
			if(toolMaterial != null) {
				iconsHead[i][0] = iconRegister.registerIcon("agecraft:tools/arrow/" + toolMaterial.name + "/arrow" + ECUtil.firstUpperCase(toolMaterial.name));
				for(int j = 0; j < 3; j++) {
					iconsHead[i][j + 1] = iconRegister.registerIcon("agecraft:tools/arrow/" + toolMaterial.name + "/arrow" + ECUtil.firstUpperCase(toolMaterial.name) + Integer.toString(j));
				}
			}
		}
		for(int i = 0; i < ToolRegistry.toolRodMaterials.length; i++) {
			ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[i];
			if(toolRodMaterial != null) {
				iconsRod[i][0] = iconRegister.registerIcon("agecraft:tools/sticks/arrow/" + toolRodMaterial.name + "/arrow" + ECUtil.firstUpperCase(toolRodMaterial.name));
				for(int j = 0; j < 3; j++) {
					iconsRod[i][j + 1] = iconRegister.registerIcon("agecraft:tools/sticks/arrow/" + toolRodMaterial.name + "/arrow" + ECUtil.firstUpperCase(toolRodMaterial.name) + Integer.toString(j));
				}
			}
		}
	}
}
