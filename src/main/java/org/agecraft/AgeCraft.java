package org.agecraft;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

import net.minecraftforge.common.DimensionManager;

import org.agecraft.core.AgeCraftCore;
import org.agecraft.core.dimension.AgeProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
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

		// init components
		new AgeCraftCore();

		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].preInit();
			}
		}
		for(ACComponent component : ACComponent.components) {
			component.preInit();
		}
		proxy.registerPlayerAPI();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		try {
			Field[] fields = DimensionManager.class.getDeclaredFields();
			for(int i = 0; i < fields.length; i++) {
				if(Modifier.isStatic(fields[i].getModifiers()) && fields[i].getName().equalsIgnoreCase("spawnSettings")) {
					fields[i].setAccessible(true);
					Hashtable<Integer, Boolean> spawnSettings = (Hashtable<Integer, Boolean>) fields[i].get(null);
					spawnSettings.put(0, false);
					spawnSettings.put(-1, false);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].init();
				DimensionManager.registerProviderType(10 + i, AgeProvider.class, false);
				DimensionManager.registerDimension(10 + i, 10 + i);
			}
		}
		for(ACComponent component : ACComponent.components) {
			component.init();
		}
		
		//register event handlers
		FMLCommonHandler.instance().bus().register(new ACEventHandler());
		FMLCommonHandler.instance().bus().register(new ACKeyHandler());
		
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
		proxy.postInit();
	}
}
