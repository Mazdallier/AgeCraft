package elcon.mods.agecraft.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ACCoreContainer extends DummyModContainer {

	public ACCoreContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "ACCore";
		meta.name = "ACCore";
		meta.version = "1.0.0";
		meta.authorList = Arrays.asList("ElConquistador");
		meta.description = "ACCore - AgeCraft ASM";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void init(FMLInitializationEvent event) {
		
	}
}
