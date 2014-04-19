package org.agecraft.core.models.animals;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import org.agecraft.core.entity.animals.EntitySheep;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSheep2 extends ModelQuadruped {

	private float field_78153_i;

	public ModelSheep2() {
		super(12, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		head.setRotationPoint(0.0F, 6.0F, -8.0F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float par2, float par3, float par4) {
		super.setLivingAnimations(entity, par2, par3, par4);
		head.rotationPointY = 6.0F + ((EntitySheep) entity).func_70894_j(par4) * 9.0F;
		field_78153_i = ((EntitySheep) entity).func_70890_k(par4);
	}

	@Override
	public void setRotationAngles(float limbSwimg, float prevLimbSwing, float par3, float par4, float par5, float partialRenderTime, Entity entity) {
		super.setRotationAngles(limbSwimg, prevLimbSwing, par3, par4, par5, partialRenderTime, entity);
		head.rotateAngleX = field_78153_i;
	}
}
