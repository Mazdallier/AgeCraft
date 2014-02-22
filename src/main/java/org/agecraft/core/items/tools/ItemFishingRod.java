package org.agecraft.core.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.registry.ToolEnhancementMaterialRegistry;
import org.agecraft.core.registry.ToolRegistry;
import org.agecraft.core.registry.ToolRegistry.Tool;
import org.agecraft.core.registry.ToolRodMaterialRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;

public class ItemFishingRod extends ItemTool {

	public IIcon[][] icons = new IIcon[256][2];
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		if(pass == 1) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				return icons[toolRodMaterial][0];
			}
		} else if(pass == 2) {
			int toolEnhancement = getToolEnhancementMaterial(stack);
			if(toolEnhancement != -1 && ToolEnhancementMaterialRegistry.instance.get(toolEnhancement) != null) {
				return ToolEnhancementMaterialRegistry.instance.get(toolEnhancement).icons[tool.id];
			}
		}
		return AgeCraftCoreClient.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for(int i = 0; i < ToolRodMaterialRegistry.instance.getAll().length; i++) {
			if(ToolRodMaterialRegistry.instance.get(i) != null) {
				for(int j = 0; j < 2; j++) {
					icons[i][j] = iconRegister.registerIcon("agecraft:tools/sticks/fishingRod/" + ToolRodMaterialRegistry.instance.get(i).name + "/fishingRod" + EQUtil.firstUpperCase(ToolRodMaterialRegistry.instance.get(i).name) + Integer.toString(j));
				}
			}
		}
	}
}
