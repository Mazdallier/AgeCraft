package elcon.mods.agecraft;

import java.io.File;
import java.util.ArrayList;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import elcon.mods.agecraft.core.AgeCraftCore;
import elcon.mods.agecraft.core.RankManager;
import elcon.mods.agecraft.prehistory.PrehistoryProvider;
import elcon.mods.core.ECMod;
import elcon.mods.core.ElConCore;

@Mod(modid = ACReference.MOD_ID, name = ACReference.NAME, version = ACReference.VERSION, acceptedMinecraftVersions = ACReference.MC_VERSION, dependencies = ACReference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = {"AgeCraft", "ACTile", "ACTech", "ACClothing"}, packetHandler = ACPacketHandlerClient.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {"AgeCraft", "ACTile", "ACTech", "ACClothing"}, packetHandler = ACPacketHandler.class))
public class AgeCraft {

	@Instance(ACReference.MOD_ID)
	public static AgeCraft instance;

	@SidedProxy(clientSide = ACReference.CLIENT_PROXY_CLASS, serverSide = ACReference.SERVER_PROXY_CLASS)
	public static ACCommonProxy proxy;

	public static File minecraftDir;

	public ArrayList<ACComponent> components = new ArrayList<ACComponent>();

	public AgeCraftCore core;
	public ACPacketHandler packetHandler;
	public ACTickHandler tickHandler;
	public ACTickHandlerClient tickHandlerClient;
	public ACWorldGenerator worldGenerator;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		minecraftDir = ElConCore.minecraftDir;
		ECMod mod = new ECMod(ACReference.MOD_ID, ACReference.VERSION, ACReference.VERSION_URL, event.getSourceFile(), event.getSuggestedConfigurationFile(), ACConfig.class, new ACSaveHandler());
		mod.localizationURLs.add("https://raw.github.com/AgeCraft/AgeCraft/master/clothing-localizations.zip");
		ElConCore.registerMod(ACReference.MOD_ID, mod);

		ACLog.init();
		
		core = new AgeCraftCore();

		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].preInit();
			}
		}
		for(ACComponent component : components) {
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
		for(ACComponent component : components) {
			component.init();
		}

		// init rank manager
		RankManager.init();

		//init tick handlers
		tickHandler = new ACTickHandler();

		//register server tick handler
		TickRegistry.registerTickHandler(AgeCraft.instance.tickHandler, Side.SERVER);

		//register packet handler
		packetHandler = new ACPacketHandler();
		NetworkRegistry.instance().registerConnectionHandler(packetHandler);
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		//register event handler
		ACEventHandler eventHandler = new ACEventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);

		//register crafting handler
		GameRegistry.registerCraftingHandler(eventHandler);

		//add world generator
		worldGenerator = new ACWorldGenerator();
		GameRegistry.registerWorldGenerator(worldGenerator);

		//register dimensions
		DimensionManager.registerProviderType(10, PrehistoryProvider.class, false);
		DimensionManager.registerDimension(10, 10);
		
		//register render information
		proxy.registerRenderInformation();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {		
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].postInit();
			}
		}
		for(ACComponent component : components) {
			component.postInit();
		}
	}
}
