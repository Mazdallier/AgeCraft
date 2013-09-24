package elcon.mods.agecraft.core.tech.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.registry.LanguageRegistry;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tech.TechTreeComponent;

@Deprecated
public class GuiTechTreePopup extends Gui {

	private static final ResourceLocation achievementBackground = new ResourceLocation("textures/gui/achievement/achievement_background.png");
	
	private Minecraft theGame;

	private int achievementWindowWidth;
	private int achievementWindowHeight;

	private String achievementGetLocalText;
	private String achievementStatName;

	private TechTreeComponent theComponent;
	private long popupTime;

	private RenderItem itemRender;
	private boolean haveTech;

	public GuiTechTreePopup(Minecraft par1Minecraft) {
		theGame = par1Minecraft;
		itemRender = new RenderItem();
	}

	public void queueTakenTechTreeComponent(TechTreeComponent tech) {
		String s = LanguageRegistry.instance().getStringLocalization("agecraft.techtree.popup");
		String st = s != "" ? s : LanguageRegistry.instance().getStringLocalization("agecraft.techtree.popup", "en_US");
		achievementGetLocalText = st;
		achievementStatName = tech.getName();
		popupTime = Minecraft.getSystemTime();
		theComponent = tech;
		haveTech = false;
		GuiTechTree.lastX = tech.displayColumn;
		GuiTechTree.lastY = tech.displayRow;
	}

	/*private void queueTechTreeComponentInformation(TechTreeComponent tech) {
		achievementGetLocalText = tech.getName();
		achievementStatName = tech.getDescription();
		popupTime = Minecraft.getSystemTime() - 2500L;
		theComponent = tech;
		haveTech = true;
	}*/

	private void updateWindowScale() {
		GL11.glViewport(0, 0, theGame.displayWidth, theGame.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		achievementWindowWidth = theGame.displayWidth;
		achievementWindowHeight = theGame.displayHeight;
		ScaledResolution var1 = new ScaledResolution(theGame.gameSettings, theGame.displayWidth, theGame.displayHeight);
		achievementWindowWidth = var1.getScaledWidth();
		achievementWindowHeight = var1.getScaledHeight();
		GL11.glClear(256);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double) achievementWindowWidth, (double) achievementWindowHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	public void updateTechTreeWindow() {
		if (theComponent != null && popupTime != 0L) {
			double var1 = (double) (Minecraft.getSystemTime() - popupTime) / 3000.0D;

			if (!haveTech && (var1 < 0.0D || var1 > 1.0D)) {
				popupTime = 0L;
			} else {
				updateWindowScale();
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(false);
				double var3 = var1 * 2.0D;

				if (var3 > 1.0D) {
					var3 = 2.0D - var3;
				}

				var3 *= 4.0D;
				var3 = 1.0D - var3;

				if (var3 < 0.0D) {
					var3 = 0.0D;
				}

				var3 *= var3;
				var3 *= var3;
				int var5 = achievementWindowWidth - 160;
				int var6 = 0 - (int) (var3 * 36.0D);
				int textureID = theGame.renderEngine.getTexture(achievementBackground).getGlTextureId();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
				GL11.glDisable(GL11.GL_LIGHTING);
				drawTexturedModalRect(var5, var6, 96, 202, 160, 32);

				if (haveTech) {
					theGame.fontRenderer.drawSplitString(achievementStatName, var5 + 30, var6 + 7, 120, -1);
				} else {
					theGame.fontRenderer.drawString(achievementGetLocalText, var5 + 30, var6 + 7, -256);
					theGame.fontRenderer.drawString(achievementStatName, var5 + 30, var6 + 18, -1);
				}

				if (theComponent.itemStack != null) {
					RenderHelper.enableGUIStandardItemLighting();
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					GL11.glEnable(GL11.GL_COLOR_MATERIAL);
					GL11.glEnable(GL11.GL_LIGHTING);
					itemRender.renderItemAndEffectIntoGUI(theGame.fontRenderer, theGame.renderEngine, theComponent.itemStack, var5 + 8, var6 + 8);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
				} else {
					RenderHelper.enableGUIStandardItemLighting();
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					GL11.glEnable(GL11.GL_COLOR_MATERIAL);
					GL11.glEnable(GL11.GL_LIGHTING);
					textureID = theGame.renderEngine.getTexture(ResourcesCore.guiTechTreeIcons).getGlTextureId();
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
					drawTexturedModalRect(var5 + 8, var6 + 8, theComponent.iconIndex % 16 * 16, theComponent.iconIndex / 16 * 16, 16, 16);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
				}
			}
		}
	}
}
