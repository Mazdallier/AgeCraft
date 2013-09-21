package elcon.mods.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.core.player.PlayerCoreRender;

public class ACPlayerRender extends PlayerCoreRender {

	public ACPlayerRender(int playerCoreIndex, PlayerCoreRender renderPlayer) {
		super(playerCoreIndex, renderPlayer);
	}
	
	public void bindPlayerClothingTexture(AbstractClientPlayer player) {
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(player.username);
		if(clothing != null && clothing.glTextureID != -1) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, clothing.glTextureID);
		} else {
			bindEntityTexture(player);
		}
	}

	@Override
	protected void renderModel(EntityLivingBase entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		bindPlayerClothingTexture((AbstractClientPlayer) entity);
		
		if(!entity.isInvisible()) {
			mainModel.render(entity, par2, par3, par4, par5, par6, par7);
		} else if(!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
			mainModel.render(entity, par2, par3, par4, par5, par6, par7);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glPopMatrix();
			GL11.glDepthMask(true);
		} else {
			mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);
		}
	}
}
