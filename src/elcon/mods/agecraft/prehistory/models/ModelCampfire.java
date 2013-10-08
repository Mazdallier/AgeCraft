package elcon.mods.agecraft.prehistory.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCampfire extends ModelBase {
	
	public ModelRenderer logCenter;
	public ModelRenderer log1;
	public ModelRenderer log2;
	public ModelRenderer log3;
	public ModelRenderer log4;
	public ModelRenderer log5;
	public ModelRenderer log6;
	public ModelRenderer log7;
	public ModelRenderer log8;
	
	public ModelRenderer spit;

	public ModelCampfire() {
		logCenter = new ModelRenderer(this, 0, 0);
		logCenter.addBox(-1.0F, 9.2F, -1.0F, 2, 2, 2);
		logCenter.setRotationPoint(0.0F, 0.0F, 0.0F);
		log1 = new ModelRenderer(this, 0, 0);
		log1.addBox(-2.0F, 9.0F, -1.0F, 5, 2, 2);
		log1.setRotationPoint(0.0F, 0.0F, 0.0F);
		log2 = new ModelRenderer(this, 0, 0);
		log2.addBox(-3.0F, 9.0F, -1.0F, 5, 2, 2);
		log2.setRotationPoint(0.0F, 0.0F, 0.0F);
		log3 = new ModelRenderer(this, 14, 0);
		log3.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log3.setRotationPoint(0.0F, -21.5F, -0.9F);
		log4 = new ModelRenderer(this, 14, 0);
		log4.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log4.setRotationPoint(0.0F, -19.5F, 8.5F);
		log5 = new ModelRenderer(this, 0, 0);
		log5.addBox(-2.0F, 9.0F, -1.0F, 5, 2, 2);
		log5.setRotationPoint(0.0F, 0.0F, -2.0F);
		log6 = new ModelRenderer(this, 0, 0);
		log6.addBox(-3.0F, 9.0F, -1.0F, 5, 2, 2);
		log6.setRotationPoint(0.0F, 0.0F, 2.0F);
		log7 = new ModelRenderer(this, 14, 0);
		log7.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log7.setRotationPoint(0.0F, -21.5F, -0.9F);
		log8 = new ModelRenderer(this, 14, 0);
		log8.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log8.setRotationPoint(4.5F, -19.5F, 6.5F);
	}

	public void renderModel(float f, int logCount) {
		logCenter.render(f);
		if(logCount >= 1) {
			log1.render(f);
		}
		if(logCount >= 2) {
			log2.render(f);
		}
		if(logCount >= 3) {
			log3.render(f);
		}
		if(logCount >= 4) {
			log4.render(f);
		}
		if(logCount >= 5) {
			log5.render(f);
		}
		if(logCount >= 6) {
			log6.render(f);
		}
		if(logCount >= 7) {
			log7.render(f);
		}
		if(logCount >= 8) {
			log8.render(f);
		}

		logCenter.rotateAngleX = 0.0F;
		logCenter.rotateAngleY = 0.0F;
		logCenter.rotateAngleZ = 0.0F;
		log1.rotateAngleX = (-(f / 57.295776F));
		log1.rotateAngleY = 0.0F;
		log1.rotateAngleZ = (f / -0.28F);
		log2.rotateAngleX = (-(f / 57.295776F));
		log2.rotateAngleY = 0.0F;
		log2.rotateAngleZ = (f / 0.25F);
		log3.rotateAngleX = -1.3F;
		log3.rotateAngleY = 0.0F;
		log3.rotateAngleZ = 0.0F;
		log4.rotateAngleX = -1.8F;
		log4.rotateAngleY = 0.0F;
		log4.rotateAngleZ = 0.0F;
		log5.rotateAngleX = 0.0F;
		log5.rotateAngleY = 0.635F;
		log5.rotateAngleZ = (f / -0.28F);
		log6.rotateAngleX = 0.0F;
		log6.rotateAngleY = 0.635F;
		log6.rotateAngleZ = (f / 0.25F);
		log7.rotateAngleX = -1.3F;
		log7.rotateAngleY = 0.635F;
		log7.rotateAngleZ = 0.0F;
		log8.rotateAngleX = -1.8F;
		log8.rotateAngleY = 0.635F;
		log8.rotateAngleZ = 0.0F;
	}
}
