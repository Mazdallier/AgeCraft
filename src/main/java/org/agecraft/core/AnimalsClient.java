package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.entity.animals.EntityChicken;
import org.agecraft.core.entity.animals.EntityCow;
import org.agecraft.core.entity.animals.EntityPig;
import org.agecraft.core.entity.animals.EntitySheep;
import org.agecraft.core.models.animals.ModelChicken;
import org.agecraft.core.models.animals.ModelCow;
import org.agecraft.core.models.animals.ModelPig;
import org.agecraft.core.models.animals.ModelSheep1;
import org.agecraft.core.models.animals.ModelSheep2;
import org.agecraft.core.render.entity.animals.RenderChicken;
import org.agecraft.core.render.entity.animals.RenderCow;
import org.agecraft.core.render.entity.animals.RenderPig;
import org.agecraft.core.render.entity.animals.RenderSheep;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class AnimalsClient extends ACComponentClient {

	public static AnimalsClient instance;

	public AnimalsClient(ACComponent component) {
		super(component);
		instance = this;
	}

	@Override
	public void registerRenderingInformation() {
		//register entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, new RenderPig(new ModelPig(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
	}
}
