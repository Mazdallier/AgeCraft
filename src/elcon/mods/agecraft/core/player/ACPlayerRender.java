package elcon.mods.agecraft.core.player;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothingClient;
import elcon.mods.core.player.PlayerCoreRender;

public class ACPlayerRender extends PlayerCoreRender {

	public ACPlayerRender(int playerCoreIndex, PlayerCoreRender renderPlayer) {
		super(playerCoreIndex, renderPlayer);
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
