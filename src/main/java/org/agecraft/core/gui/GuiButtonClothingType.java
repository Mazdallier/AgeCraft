package org.agecraft.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.clothing.ClothingRegistry;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.gui.GuiToggleButton;

@SideOnly(Side.CLIENT)
public class GuiButtonClothingType extends GuiToggleButton {

	public String clothingType;
	public int clothingTypeIndex;
	
	public GuiButtonClothingType(int id, int x, int y, int textureX, int textureY, int width, int height, ResourceLocation location, String text, String clothingType, int clothingTypeIndex) {
		super(id, x, y, textureX, textureY, width, height, location, text, true);
		this.clothingType = clothingType;
		this.clothingTypeIndex = clothingTypeIndex;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(visible) {
			mc.getTextureManager().bindTexture(location);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(xPosition, yPosition, textureX, textureY + (toggled ? height : 0), width, height);
			mouseDragged(mc, mouseX, mouseY);
			field_146123_n = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			if(displayString != null && displayString.length() > 0) {
				int color = 0x404040;
				if(field_146123_n) {
					color = 0xFFFFFF;
				}
				mc.fontRenderer.drawString(displayString, xPosition + 18, yPosition + (height - 8) / 2, color);
			}
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(AgeCraftCoreClient.guiClothingIcons);
			drawTexturedModalRect(xPosition, yPosition, 0, 16 * ClothingRegistry.types.get(clothingType).index, 16, 16);
		}
	}
}
