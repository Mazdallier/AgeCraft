package elcon.mods.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import elcon.mods.agecraft.assets.resources.ResourcesCore;

@SideOnly(Side.CLIENT)
public class GuiInventory extends GuiContainer {

	private float xSize_lo;
	private float ySize_lo;

	public GuiInventory(EntityPlayer player) {
		super(player.inventoryContainer);
		allowUserInput = true;
		player.addStat(AchievementList.openInventory, 1);
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		if(mc.playerController.isInCreativeMode()) {
			//mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
			//return;
		}
		super.initGui();
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		if(mc.playerController.isInCreativeMode()) {
			//mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTickTime) {
		super.drawScreen(mouseX, mouseY, partialTickTime);
		xSize_lo = (float) mouseX;
		ySize_lo = (float) mouseY;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//fontRenderer.drawString(LanguageManager.getLocalization("container.crafting"), 86, 16, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ResourcesCore.guiInventory);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		renderPlayer(guiLeft + 33, guiTop + 75, 30, (float) (guiLeft + 33) - xSize_lo, (float) (guiTop + 75 - 50) - ySize_lo, mc.thePlayer);
	}

	public static void renderPlayer(int x, int y, int size, float lookX, float lookY, EntityLivingBase entity) {
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, 50.0F);
		GL11.glScalef((float) (-size), (float) size, (float) size);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float renderYawOffset = entity.renderYawOffset;
		float rotationYaw = entity.rotationYaw;
		float rotationPitch = entity.rotationPitch;
		float prevRotationYawHead = entity.prevRotationYawHead;
		float rotationYawHead = entity.rotationYawHead;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float) Math.atan((double) (lookY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan((double) (lookX / 40.0F)) * 20.0F;
		entity.rotationYaw = (float) Math.atan((double) (lookX / 40.0F)) * 40.0F;
		entity.rotationPitch = -((float) Math.atan((double) (lookY / 40.0F))) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entity.renderYawOffset = renderYawOffset;
		entity.rotationYaw = rotationYaw;
		entity.rotationPitch = rotationPitch;
		entity.prevRotationYawHead = prevRotationYawHead;
		entity.rotationYawHead = rotationYawHead;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
