package org.agecraft.core.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.registry.ToolMaterialRegistry;
import org.agecraft.core.registry.ToolRodMaterialRegistry;
import org.agecraft.core.registry.ToolRodMaterialRegistry.ToolRodMaterial;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;

public class ItemArrow extends ItemTool {

	public IIcon[][] iconsHead = new IIcon[256][4];
	public IIcon[][] iconsRod = new IIcon[256][4];

	public ItemArrow() {
		setMaxStackSize(64);
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if(pass == 0) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && ToolRodMaterialRegistry.instance.get(toolRodMaterial) != null) {
				return iconsRod[toolRodMaterial][0];
			}
		} else if(pass == 1) {
			int toolMaterial = getToolMaterial(stack);
			if(toolMaterial != -1 && ToolMaterialRegistry.instance.get(toolMaterial) != null) {
				return iconsHead[toolMaterial][0];
			}
		}
		return AgeCraftCoreClient.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for(int i = 0; i < ToolMaterialRegistry.instance.getAll().length; i++) {
			org.agecraft.core.registry.ToolMaterialRegistry.ToolMaterial toolMaterial = ToolMaterialRegistry.instance.get(i);
			if(toolMaterial != null) {
				iconsHead[i][0] = iconRegister.registerIcon("agecraft:tools/arrow/" + toolMaterial.name + "/arrow" + EQUtil.firstUpperCase(toolMaterial.name));
				for(int j = 0; j < 3; j++) {
					iconsHead[i][j + 1] = iconRegister.registerIcon("agecraft:tools/arrow/" + toolMaterial.name + "/arrow" + EQUtil.firstUpperCase(toolMaterial.name) + Integer.toString(j));
				}
			}
		}
		for(int i = 0; i < ToolRodMaterialRegistry.instance.getAll().length; i++) {
			ToolRodMaterial toolRodMaterial = ToolRodMaterialRegistry.instance.get(i);
			if(toolRodMaterial != null) {
				iconsRod[i][0] = iconRegister.registerIcon("agecraft:tools/sticks/arrow/" + toolRodMaterial.name + "/arrow" + EQUtil.firstUpperCase(toolRodMaterial.name));
				for(int j = 0; j < 3; j++) {
					iconsRod[i][j + 1] = iconRegister.registerIcon("agecraft:tools/sticks/arrow/" + toolRodMaterial.name + "/arrow" + EQUtil.firstUpperCase(toolRodMaterial.name) + Integer.toString(j));
				}
			}
		}
	}
}
