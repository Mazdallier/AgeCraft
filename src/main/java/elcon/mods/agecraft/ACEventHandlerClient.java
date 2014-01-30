package elcon.mods.agecraft;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.Tools;

@SideOnly(Side.CLIENT)
public class ACEventHandlerClient {

	@ForgeSubscribe
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
		ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
		if(stack != null && (stack.itemID == Tools.bow.itemID || stack.itemID == Tools.crossbow.itemID)) {
			event.renderItem = false;
		}
	}

	@ForgeSubscribe
	public void onRenderPlayerSpecialsPost(RenderPlayerEvent.Specials.Post event) {
		ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
		if(stack != null && (stack.itemID == Tools.bow.itemID || stack.itemID == Tools.crossbow.itemID)) {
			GL11.glPushMatrix();
			event.renderer.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			EnumAction enumaction = null;
			if(event.entityPlayer.getItemInUseCount() > 0) {
				enumaction = stack.getItemUseAction();
			}
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(stack, EQUIPPED);
			if(stack.itemID == Tools.bow.itemID) {
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(0.625F, -0.625F, 0.625F);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(stack.itemID == Tools.crossbow.itemID) {
				GL11.glRotatef(95.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.15F, 0.17F, 0.1F);
				
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-8.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(0.625F, -0.625F, 0.625F);
				GL11.glRotatef(-109.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			if(stack.getItem().requiresMultipleRenderPasses()) {
				for(int i = 0; i < stack.getItem().getRenderPasses(stack.getItemDamage()); i++) {
					int color = stack.getItem().getColorFromItemStack(stack, i);
					float r = (float) (color >> 16 & 255) / 255.0F;
					float g = (float) (color >> 8 & 255) / 255.0F;
					float b = (float) (color & 255) / 255.0F;
					GL11.glColor4f(r, g, b, 1.0F);
					RenderManager.instance.itemRenderer.renderItem(event.entityPlayer, stack, i);
				}
			}
			GL11.glPopMatrix();
		}
	}
}
