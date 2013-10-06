package elcon.mods.agecraft.core.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.core.ElConCore;

public class ItemCrossbow extends ItemTool {

	public Icon[][] icons = new Icon[256][4];
	
	public ItemCrossbow(int id) {
		super(id);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(pass == 1) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				if(Minecraft.getMinecraft().thePlayer.getItemInUse() != null && ItemStack.areItemStacksEqual(stack, Minecraft.getMinecraft().thePlayer.getItemInUse())) {
					int duration = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
					if(duration >= 18) {
						return icons[toolRodMaterial][3];
					} else if(duration > 13) {
						return icons[toolRodMaterial][2];
					} else if(duration > 0) {
						return icons[toolRodMaterial][1];
					}
				}
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
				for(int j = 0; j < 4; j++) {
					icons[i][j] = iconRegister.registerIcon("agecraft:tools/sticks/crossbow/" + ToolRegistry.toolRodMaterials[i].name + "/crossbow" + ElConCore.firstUpperCase(ToolRegistry.toolRodMaterials[i].name) + Integer.toString(j));
				}
			}
		}
	}
}
