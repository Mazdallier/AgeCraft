package org.agecraft;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.agecraft.core.Tools;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ACEventHandlerClient {

	public ModelBiped modelBipedMain;

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event) {
		if(event.entity.isUsingItem() && (event.entity.getItemInUse().getItem() == Tools.bow || event.entity.getItemInUse().getItem() == Tools.crossbow)) {
			int duration = event.entity.getItemInUseDuration();
			float multiplier = (float) duration / 20.0F;
			if(multiplier > 1.0F) {
				multiplier = 1.0F;
			} else {
				multiplier *= multiplier;
			}
			event.newfov *= 1.0F - multiplier * 0.15F;
		}
	}
	
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
		ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
		if(stack != null && (stack.getItem() == Tools.bow || stack.getItem() == Tools.crossbow)) {
			event.renderItem = false;
		}
	}

	@SubscribeEvent
	public void onRenderPlayerSpecialsPost(RenderPlayerEvent.Specials.Post event) {
		ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
		if(stack != null && (stack.getItem() == Tools.bow || stack.getItem() == Tools.crossbow)) {
			GL11.glPushMatrix();
			if(modelBipedMain == null) {
				try {
					Field field = RenderPlayer.class.getField("modelBipedMain");
					field.setAccessible(true);
					modelBipedMain = (ModelBiped) field.get(event.renderer);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if(stack.getItem() == Tools.bow) {
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(0.625F, -0.625F, 0.625F);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if(stack.getItem() == Tools.crossbow) {
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
