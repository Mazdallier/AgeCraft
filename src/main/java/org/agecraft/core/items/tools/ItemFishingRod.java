package org.agecraft.core.items.tools;

import javax.swing.Icon;

import net.minecraft.item.ItemStack;

import org.agecraft.core.registry.ToolRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishingRod extends ItemTool {

	public Icon[][] icons = new Icon[256][2];
	
	public ItemFishingRod(int id) {
		super(id);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(pass == 1) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				return icons[toolRodMaterial][0];
			}
		} else if(pass == 2) {
			int toolEnhancement = getToolEnhancementMaterial(stack);
			if(toolEnhancement != -1 && ToolRegistry.toolEnhancementMaterials[toolEnhancement] != null) {
				return ToolRegistry.toolEnhancementMaterials[toolEnhancement].icons[tool.id];
			}
		}
		return ResourcesCore.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < ToolRegistry.toolRodMaterials.length; i++) {
			if(ToolRegistry.toolRodMaterials[i] != null) {
				for(int j = 0; j < 2; j++) {
					icons[i][j] = iconRegister.registerIcon("agecraft:tools/sticks/fishingRod/" + ToolRegistry.toolRodMaterials[i].name + "/fishingRod" + ECUtil.firstUpperCase(ToolRegistry.toolRodMaterials[i].name) + Integer.toString(j));
				}
			}
		}
	}
}
