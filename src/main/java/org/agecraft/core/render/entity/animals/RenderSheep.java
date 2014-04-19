package org.agecraft.core.render.entity.animals;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.agecraft.core.entity.animals.EntitySheep;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSheep extends RenderLiving {

	public static ResourceLocation texture = new ResourceLocation("agecraft", "textures/entity/animals/sheep/sheep.png");
	public static ResourceLocation textureFur = new ResourceLocation("agecraft", "textures/entity/animals/sheep/sheep_fur.png");

	public RenderSheep(ModelBase model1, ModelBase model2, float f) {
		super(model1, f);
		setRenderPassModel(model2);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int renderPass, float partialRenderTime) {
		EntitySheep sheep = (EntitySheep) entity;
		if(renderPass == 0 && !sheep.getSheared()) {
			bindTexture(textureFur);
			int color = sheep.getFleeceColor();
			GL11.glColor3f(EntitySheep.colors[color][0], EntitySheep.colors[color][1], EntitySheep.colors[color][2]);
			return 1;
		}
		return -1;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
