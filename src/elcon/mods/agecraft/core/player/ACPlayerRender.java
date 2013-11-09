package elcon.mods.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.RankManager;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.core.player.PlayerCoreRender;

public class ACPlayerRender extends PlayerCoreRender {

	public ACPlayerRender(int playerCoreIndex, PlayerCoreRender renderPlayer) {
		super(playerCoreIndex, renderPlayer);
	}

	@Override
	protected void passSpecialRender(EntityLivingBase entity, double x, double y, double z) {
		if(MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Pre(entity, player))) {
			return;
		}
		if(func_110813_b(entity)) {
			float f = 1.6F;
			float scale = 0.016666668F * f;
			double distance = entity.getDistanceSqToEntity(player.getRenderManager().livingPlayer);
			float range = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
			if(distance < (double) (range * range)) {
				String label = entity.getTranslatedEntityName();
				if(entity.isSneaking()) {
					FontRenderer fontRenderer = getFontRendererFromRenderManager();
					GL11.glPushMatrix();
					GL11.glTranslatef((float) x + 0.0F, (float) y + entity.height + 0.5F, (float) z);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-player.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(player.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
					GL11.glScalef(-scale, -scale, scale);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glTranslatef(0.0F, 0.25F / scale, 0.0F);
					GL11.glDepthMask(false);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					Tessellator tessellator = Tessellator.instance;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					int i = fontRenderer.getStringWidth(label) / 2;
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator.addVertex((double) (-i - 1), -1.0D, 0.0D);
					tessellator.addVertex((double) (-i - 1), 8.0D, 0.0D);
					tessellator.addVertex((double) (i + 1), 8.0D, 0.0D);
					tessellator.addVertex((double) (i + 1), -1.0D, 0.0D);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(true);
					fontRenderer.drawString(label, -fontRenderer.getStringWidth(label) / 2, 0, 553648127);
					
					if(entity instanceof EntityPlayer && RankManager.getRank(((EntityPlayer) entity).username).name.equals(RankManager.developer.name)) {
						int halfLabelLength = fontRenderer.getStringWidth(label) / 2;
						Minecraft.getMinecraft().getTextureManager().bindTexture(ResourcesCore.gear);
						tessellator.startDrawingQuads();
						tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
						//tessellator.setColorRGBA_I(RankManager.getColor(((EntityPlayer) entity).username), 255);
						tessellator.addVertexWithUV((double) (-halfLabelLength - 10), -0.5D, 0.0D, 0.0D, 0.0D);
						tessellator.addVertexWithUV((double) (-halfLabelLength - 10), 7.5D, 0.0D, 0.0D, 1.0D);
						tessellator.addVertexWithUV((double) (-halfLabelLength - 2), 7.5D, 0.0D, 1.0D, 1.0D);
						tessellator.addVertexWithUV((double) (-halfLabelLength - 2), -0.5D, 0.0D, 1.0D, 0.0D);
						tessellator.draw();
					}
					
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glPopMatrix();
				} else {
					func_96449_a(entity, x, y, z, label, scale, distance);
				}
			}
		}
		MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Post(entity, player));
	}

	@Override
	protected boolean func_110813_b(EntityLivingBase entity) {
		return Minecraft.isGuiEnabled() && entity != player.getRenderManager().livingPlayer && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && entity.riddenByEntity == null;
	}

	@Override
	protected void func_96449_a(EntityLivingBase entity, double x, double y, double z, String label, float par9, double par10) {
		if(entity.isPlayerSleeping()) {
			renderLivingLabel(entity, label, x, y - 1.5D, z, 64);
		} else {
			renderLivingLabel(entity, label, x, y, z, 64);
		}
	}

	@Override
	protected void renderLivingLabel(EntityLivingBase entity, String label, double x, double y, double z, int maxDistance) {
		double distance = entity.getDistanceSqToEntity(player.getRenderManager().livingPlayer);
		if(distance <= (double) (maxDistance * maxDistance)) {
			FontRenderer fontRenderer = getFontRendererFromRenderManager();
			float f = 1.6F;
			float f1 = 0.016666668F * f;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.0F, (float) y + entity.height + 0.5F, (float) z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-player.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(player.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-f1, -f1, f1);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Tessellator tessellator = Tessellator.instance;
			byte b0 = 0;
			if(label.equals("deadmau5")) {
				b0 = -10;
			}
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.startDrawingQuads();
			int j = fontRenderer.getStringWidth(label) / 2;
			tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			tessellator.addVertex((double) (-j - 1), (double) (-1 + b0), 0.0D);
			tessellator.addVertex((double) (-j - 1), (double) (8 + b0), 0.0D);
			tessellator.addVertex((double) (j + 1), (double) (8 + b0), 0.0D);
			tessellator.addVertex((double) (j + 1), (double) (-1 + b0), 0.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			fontRenderer.drawString(label, -fontRenderer.getStringWidth(label) / 2, b0, 553648127);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			fontRenderer.drawString(label, -fontRenderer.getStringWidth(label) / 2, b0, -1);
			
			if(entity instanceof EntityPlayer && RankManager.getRank(((EntityPlayer) entity).username).name.equals(RankManager.developer.name)) {
				int halfLabelLength = fontRenderer.getStringWidth(label) / 2;
				Minecraft.getMinecraft().getTextureManager().bindTexture(ResourcesCore.gear);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 1.0F);
				//tessellator.setColorRGBA_I(RankManager.getColor(((EntityPlayer) entity).username), 255);
				tessellator.addVertexWithUV((double) (-halfLabelLength - 10), (double) (-0.5D + b0), 0.0D, 0.0D, 0.0D);
				tessellator.addVertexWithUV((double) (-halfLabelLength - 10), (double) (7.5D + b0), 0.0D, 0.0D, 1.0D);
				tessellator.addVertexWithUV((double) (-halfLabelLength - 2), (double) (7.5D + b0), 0.0D, 1.0D, 1.0D);
				tessellator.addVertexWithUV((double) (-halfLabelLength - 2), (double) (-0.5D + b0), 0.0D, 1.0D, 0.0D);
				tessellator.draw();
			}
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	public FontRenderer getFontRendererFromRenderManager() {
		return player.getRenderManager().getFontRenderer();
	}

	@Override
	protected void bindEntityTexture(Entity par1Entity) {
		bindPlayerClothingTexture((AbstractClientPlayer) par1Entity);
	}

	public void bindPlayerClothingTexture(AbstractClientPlayer player) {
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(player.username);
		if(clothing != null && clothing.glTextureID != -1) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, clothing.glTextureID);
		} else {
			super.bindEntityTexture(player);
		}
	}

	@Override
	public void renderFirstPersonArm(EntityPlayer player) {
		bindPlayerClothingTexture((AbstractClientPlayer) player);
		super.renderFirstPersonArm(player);
	}
}
