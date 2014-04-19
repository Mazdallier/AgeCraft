package org.agecraft.core.models.animals;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelChicken extends ModelBase {

	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public ModelRenderer bill;
	public ModelRenderer chin;

	public ModelChicken() {
		byte offsetY = 16;
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
		head.setRotationPoint(0.0F, (float) (-1 + offsetY), -4.0F);
		bill = new ModelRenderer(this, 14, 0);
		bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
		bill.setRotationPoint(0.0F, (float) (-1 + offsetY), -4.0F);
		chin = new ModelRenderer(this, 14, 4);
		chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
		chin.setRotationPoint(0.0F, (float) (-1 + offsetY), -4.0F);
		body = new ModelRenderer(this, 0, 9);
		body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
		body.setRotationPoint(0.0F, (float) offsetY, 0.0F);
		rightLeg = new ModelRenderer(this, 26, 0);
		rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		rightLeg.setRotationPoint(-2.0F, (float) (3 + offsetY), 1.0F);
		leftLeg = new ModelRenderer(this, 26, 0);
		leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		leftLeg.setRotationPoint(1.0F, (float) (3 + offsetY), 1.0F);
		rightWing = new ModelRenderer(this, 24, 13);
		rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
		rightWing.setRotationPoint(-4.0F, (float) (-3 + offsetY), 0.0F);
		leftWing = new ModelRenderer(this, 24, 13);
		leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
		leftWing.setRotationPoint(4.0F, (float) (-3 + offsetY), 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float prevLimbSwing, float rotationYaw1, float rotationYaw2, float rotationPitch, float f) {
		setRotationAngles(limbSwing, prevLimbSwing, rotationYaw1, rotationYaw2, rotationPitch, f, entity);
		if(isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 5.0F * f, 2.0F * f);
			head.render(f);
			bill.render(f);
			chin.render(f);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f, 0.0F);
			body.render(f);
			rightLeg.render(f);
			leftLeg.render(f);
			rightWing.render(f);
			leftWing.render(f);
			GL11.glPopMatrix();
		} else {
			head.render(f);
			bill.render(f);
			chin.render(f);
			body.render(f);
			rightLeg.render(f);
			leftLeg.render(f);
			rightWing.render(f);
			leftWing.render(f);
		}
	}

	public void setRotationAngles(float limbSwing, float prevLimbSwing, float rotationYaw1, float rotationYaw2, float rotationPitch, float f, Entity entity) {
		head.rotateAngleX = rotationPitch / (180F / (float) Math.PI);
		head.rotateAngleY = rotationYaw2 / (180F / (float) Math.PI);
		bill.rotateAngleX = head.rotateAngleX;
		bill.rotateAngleY = head.rotateAngleY;
		chin.rotateAngleX = head.rotateAngleX;
		chin.rotateAngleY = head.rotateAngleY;
		body.rotateAngleX = ((float) Math.PI / 2F);
		rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * prevLimbSwing;
		leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * prevLimbSwing;
		rightWing.rotateAngleZ = rotationYaw1;
		leftWing.rotateAngleZ = -rotationYaw1;
	}
}
