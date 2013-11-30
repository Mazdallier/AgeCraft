package elcon.mods.agecraft.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import elcon.mods.core.gui.GuiToggleButton;

public class GuiButtonClothingColor extends GuiToggleButton {

	public GuiButtonClothingColor(int id, int x, int y, int textureX, int textureY, int width, int height, ResourceLocation location, String text) {
		super(id, x, y, textureX, textureY, width, height, location, text, true);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(drawButton) {
			mc.getTextureManager().bindTexture(location);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			field_82253_i = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, textureX, textureY, width, height);
			if(toggled) {
				drawTexturedModalRect(xPosition, yPosition, 132, 166, 8, 8);
			}
			mouseDragged(mc, mouseX, mouseY);
			if(displayString != null && displayString.length() > 0) {
				int color = 0xE0E0E0;
				if(!enabled) {
					color = -0x5F5F60;
				} else if(field_82253_i) {
					color = 0xFFFFA0;
				}
				drawCenteredString(mc.fontRenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, color);
			}
		}
	}
}
