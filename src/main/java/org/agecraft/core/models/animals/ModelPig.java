package org.agecraft.core.models.animals;

import net.minecraft.client.model.ModelQuadruped;

public class ModelPig extends ModelQuadruped {

	public ModelPig() {
		this(0.0F);
	}

	public ModelPig(float f) {
		super(6, f);
		head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, f);
		field_78145_g = 4.0F;
	}
}
