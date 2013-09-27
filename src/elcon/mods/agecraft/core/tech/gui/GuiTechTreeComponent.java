package elcon.mods.agecraft.core.tech.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.tech.TechTree;
import elcon.mods.core.lang.LanguageManager;

@SideOnly(Side.CLIENT)
public class GuiTechTreeComponent extends Gui {

	private static final ResourceLocation backgroundTexture = new ResourceLocation("textures/Ogui/achievement/achievement_background.png");

	public static GuiTechTreeComponent instance;
	
	private Minecraft mc;
	private int achievementWindowWidth;
	private int achievementWindowHeight;
	private long componentTime;
	private RenderItem itemRender;
	private boolean hasComponent;
	private String pageName;
	private String name;

	public GuiTechTreeComponent() {
		mc = Minecraft.getMinecraft();
		itemRender = new RenderItem();
		instance = this;
	}

	public void queueComponent(String pageName, String name) {
		this.pageName = pageName;
		this.name = name;
		hasComponent = true;
		componentTime = Minecraft.getSystemTime();
	}

	private void updateAchievementWindowScale() {
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		achievementWindowWidth = mc.displayWidth;
		achievementWindowHeight = mc.displayHeight;
		ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		achievementWindowWidth = scaledresolution.getScaledWidth();
		achievementWindowHeight = scaledresolution.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double) achievementWindowWidth, (double) achievementWindowHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	public void updateAchievementWindow() {
		if(hasComponent && componentTime != 0L) {
			double deltaTime = (double) (Minecraft.getSystemTime() - componentTime) / 3000.0D;
			if(deltaTime < 0.0D || deltaTime > 1.0D) {
				componentTime = 0L;
				hasComponent = false;
			} else {
				updateAchievementWindowScale();
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(false);
				
				double progress = deltaTime * 2.0D;
				if(progress > 1.0D) {
					progress = 2.0D - progress;
				}
				progress *= 4.0D;
				progress = 1.0D - progress;
				if(progress < 0.0D) {
					progress = 0.0D;
				}
				progress *= progress;
				progress *= progress;
				
				int i = achievementWindowWidth - 160;
				int j = 0 - (int) (progress * 36.0D);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				mc.getTextureManager().bindTexture(backgroundTexture);
				GL11.glDisable(GL11.GL_LIGHTING);
				drawTexturedModalRect(i, j, 96, 202, 160, 32);

				mc.fontRenderer.drawString(LanguageManager.getLocalization("techtree.popup"), i + 30, j + 7, -256);
				mc.fontRenderer.drawString(TechTree.getComponent(pageName, name).getName(), i + 30, j + 18, -1);

				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				if(TechTree.getComponent(pageName, name).stack != null) {
					itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), TechTree.getComponent(pageName, name).stack, i + 8, j + 8);
				} else {
					mc.renderEngine.bindTexture(ResourcesCore.guiTechTreeIcons);
					drawTexturedModalRect(i + 8, i + 8, TechTree.getComponent(pageName, name).iconIndex % 16 * 16, TechTree.getComponent(pageName, name).iconIndex / 16 * 16, 16, 16);
				}
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}
	}
}
