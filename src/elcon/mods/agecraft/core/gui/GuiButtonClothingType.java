package elcon.mods.agecraft.core.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import elcon.mods.agecraft.assets.resources.ResourcesCore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiButtonClothingType extends GuiToggleButton {

	public int clothingType;
	
	public GuiButtonClothingType(int id, int x, int y, int textureX, int textureY, int width, int height, ResourceLocation location, String text, int clothingType) {
		super(id, x, y, textureX, textureY, width, height, location, text, true);
		this.clothingType = clothingType;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(drawButton) {
			mc.getTextureManager().bindTexture(location);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(xPosition, yPosition, textureX, textureY + (toggled ? height : 0), width, height);
			mouseDragged(mc, mouseX, mouseY);
			field_82253_i = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			if(displayString != null && displayString.length() > 0) {
				int color = 0x404040;
				if(field_82253_i) {
					color = 0xFFFFFF;
				}
				mc.fontRenderer.drawString(displayString, xPosition + 18, yPosition + (height - 8) / 2, color);
			}
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(ResourcesCore.guiClothingIcons);
			drawTexturedModalRect(xPosition, yPosition, 0, 16 * clothingType, 16, 16);
		}
	}
}
