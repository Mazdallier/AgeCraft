package org.agecraft.core.render.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.agecraft.assets.resources.ResourcesCore;
import org.agecraft.core.entity.EntityBolt;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class RenderBolt extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {
		bindTexture(getBoltHeadTexture((EntityBolt) entity));
		renderBolt((EntityBolt) entity, x, y, z, f1, f2);
		bindTexture(getBoltRodTexture((EntityBolt) entity));
		renderBolt((EntityBolt) entity, x, y, z, f1, f2);
	}

	public void renderBolt(EntityBolt entity, double x, double y, double z, float f1, float f2) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f2 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f2, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		byte b0 = 0;
		float u3 = 0.0F;
		float u4 = 0.5F;
		float v3 = (float) (0 + b0 * 10) / 32.0F;
		float v4 = (float) (5 + b0 * 10) / 32.0F;
		float u1 = 0.0F;
		float u2 = 0.15625F;
		float v1 = (float) (5 + b0 * 10) / 32.0F;
		float v2 = (float) (10 + b0 * 10) / 32.0F;
		float f3 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float shake = (float) entity.boltShake - f2;
		if(shake > 0.0F) {
			float rotation = -MathHelper.sin(shake * 3.0F) * shake;
			GL11.glRotatef(rotation, 0.0F, 0.0F, 1.0F);
		}
		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(f3, f3, f3);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(f3, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) u1, (double) v1);
		tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) u2, (double) v1);
		tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) u2, (double) v2);
		tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) u1, (double) v2);
		tessellator.draw();
		GL11.glNormal3f(-f3, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) u1, (double) v1);
		tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) u2, (double) v1);
		tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) u2, (double) v2);
		tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) u1, (double) v2);
		tessellator.draw();
		/*for(int i = 0; i < 4; ++i) {
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f3);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double) u3, (double) v3);
			tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double) u4, (double) v3);
			tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double) u4, (double) v4);
			tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double) u3, (double) v4);
			tessellator.draw();
		}*/
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	public ResourceLocation getBoltHeadTexture(EntityBolt entity) {
		return ResourcesCore.boltHeads[entity.getMaterial()];
	}

	public ResourceLocation getBoltRodTexture(EntityBolt entity) {
		return ResourcesCore.boltRods[entity.getRodMaterial()];
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
