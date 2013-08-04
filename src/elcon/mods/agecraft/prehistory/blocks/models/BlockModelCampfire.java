package elcon.mods.agecraft.prehistory.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BlockModelCampfire extends ModelBase {
	
	public ModelRenderer logCen;
	public ModelRenderer log;
	public ModelRenderer log1;
	public ModelRenderer log2;
	public ModelRenderer log3;
	public ModelRenderer log5;
	public ModelRenderer log6;
	public ModelRenderer log7;
	public ModelRenderer log8;
	
	public ModelRenderer spit;

	public BlockModelCampfire() {
		byte byte0 = 16;
		logCen = new ModelRenderer(this, 0, 0);
		logCen.addBox(-1.0F, 9.2F, -1.0F, 2, 2, 2);
		logCen.setRotationPoint(0.0F, 0.0F, 0.0F);
		log = new ModelRenderer(this, 0, 0);
		log.addBox(-2.0F, 9.0F, -1.0F, 5, 2, 2);
		log.setRotationPoint(0.0F, 0.0F, 0.0F);
		log1 = new ModelRenderer(this, 0, 0);
		log1.addBox(-3.0F, 9.0F, -1.0F, 5, 2, 2);
		log1.setRotationPoint(0.0F, 0.0F, 0.0F);
		log2 = new ModelRenderer(this, 14, 0);
		log2.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log2.setRotationPoint(0.0F, -21.5F, -0.9F);
		log3 = new ModelRenderer(this, 14, 0);
		log3.addBox(-1.0F, 2.0F, 30.0F, 2, 5, 2);
		log3.setRotationPoint(0.0F, -19.5F, 8.5F);
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
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		log.render(f5);
		log1.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		log.rotateAngleX = (-(f4 / 57.295776F));
		log.rotateAngleY = (f3 / 57.295776F);
		log1.rotateAngleX = log.rotateAngleX;
		log1.rotateAngleY = log.rotateAngleY;
	}

	public void renderModel(float f) {
		logCen.render(f);
		log.render(f);
		log1.render(f);
		log2.render(f);
		log3.render(f);
		log5.render(f);
		log6.render(f);
		log7.render(f);
		log8.render(f);

		logCen.rotateAngleX = 0.0F;
		logCen.rotateAngleY = 0.0F;
		logCen.rotateAngleZ = 0.0F;
		log.rotateAngleX = (-(f / 57.295776F));
		log.rotateAngleY = 0.0F;
		log.rotateAngleZ = (f / -0.28F);
		log1.rotateAngleX = (-(f / 57.295776F));
		log1.rotateAngleY = 0.0F;
		log1.rotateAngleZ = (f / 0.25F);
		log2.rotateAngleX = -1.3F;
		log2.rotateAngleY = 0.0F;
		log2.rotateAngleZ = 0.0F;
		log3.rotateAngleX = -1.8F;
		log3.rotateAngleY = 0.0F;
		log3.rotateAngleZ = 0.0F;
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
