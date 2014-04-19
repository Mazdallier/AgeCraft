package org.agecraft.core.render.entity.animals;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.agecraft.core.entity.animals.EntityChicken;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChicken extends RenderLiving {

	public static ResourceLocation texture = new ResourceLocation("agecraft", "textures/entity/animals/chicken/chicken.png");

	public RenderChicken(ModelBase model1, float f) {
		super(model1, f);
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entity, float f) {
		EntityChicken chicken = (EntityChicken) entity;
		float val = chicken.oldValue + (chicken.value - chicken.oldValue) * f;
		float amp = chicken.oldDestPos + (chicken.destPos - chicken.oldDestPos) * f;
		return (MathHelper.sin(val) + 1.0F) * amp;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
