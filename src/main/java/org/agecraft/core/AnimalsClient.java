package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.entity.animals.EntityPig;
import org.agecraft.core.models.animals.ModelPig;
import org.agecraft.core.render.entity.animals.RenderPig;

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
	}
}
