package elcon.mods.agecraft.prehistory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.assets.resources.ResourcesPrehistory;

public class GuiSharpener extends GuiContainer {
	
	public GuiSharpener(InventorySharpener inv) {
		super(new ContainerSharpener(Minecraft.getMinecraft().thePlayer, inv));
		allowUserInput = false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.func_110577_a(ResourcesPrehistory.guiSharpener);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
