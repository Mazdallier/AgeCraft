package org.agecraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import elcon.mods.elconqore.EQMod;

@Mod(modid = ACReference.MOD_ID, name = ACReference.NAME, version = ACReference.VERSION, acceptedMinecraftVersions = ACReference.MC_VERSION, dependencies = ACReference.DEPENDENCIES)
public class AgeCraft {

	@Instance(ACReference.MOD_ID)
	public static AgeCraft instance;
	
	@SidedProxy(clientSide = ACReference.CLIENT_PROXY_CLASS, serverSide = ACReference.SERVER_PROXY_CLASS)
	public static ACCommonProxy proxy;
	
	public static Logger log = LogManager.getLogger(ACReference.MOD_ID);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EQMod mod = new EQMod(this, ACReference.VERSION_URL, new ACConfig(event.getSuggestedConfigurationFile()), event.getSourceFile());
		mod.localizationURLs.add("https://raw.github.com/AgeCraft/AgeCraft/master/clothing-localizations.zip");
		
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].preInit();
			}
		}
		for(ACComponent component : ACComponent.components) {
			component.preInit();
		}
		proxy.registerResources();
		proxy.registerPlayerAPI();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].init();
			}
		}
		for(ACComponent component : ACComponent.components) {
			component.init();
		}
		
		//register gui handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		
		//register render information
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].postInit();
			}
		}
		for(ACComponent component : ACComponent.components) {
			component.postInit();
		}
	}
}
