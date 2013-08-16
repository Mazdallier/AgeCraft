package elcon.mods.agecraft;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.AgeCraftCore;
import elcon.mods.agecraft.prehistory.PrehistoryProvider;
import elcon.mods.agecraft.ranks.ACRankManager;
import elcon.mods.agecraft.tech.TechTree;

@Mod(modid = ACReference.MOD_ID, name = ACReference.NAME, version = ACReference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, packetHandler = ACPacketHandler.class, channels = {"ACTech", "ACTile"})
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

	public ACRankManager rankManager;
	
	public static TechTree techTree;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		minecraftDir = new File(event.getSuggestedConfigurationFile().getPath().replace("config\\AgeCraft.cfg", ""));

		ACLog.init();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ACConfig.load(config);
		
		LanguageRegistry.instance().addStringLocalization("agecraft.version.init_log_message", "en_US", "Initializing remote version check against remote version authority, located at");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.uninitialized", "en_US", "Remote version check failed to initialize properly");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.current", "en_US", "Currently using the most up to date version (@REMOTE_MOD_VERSION@) of AgeCraft for @MINECRAFT_VERSION@");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.outdated", "en_US", "A new @MOD_NAME@ version exists (@REMOTE_MOD_VERSION@) for @MINECRAFT_VERSION@. Get it here: @MOD_UPDATE_LOCATION@");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.general_error", "en_US", "Error while connecting to remote version authority file; trying again");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.final_error", "en_US", "Version check stopping after three unsuccessful connection attempts");
		LanguageRegistry.instance().addStringLocalization("agecraft.version.mc_version_not_found", "en_US", "Unable to find a version of @MOD_NAME@ for @MINECRAFT_VERSION@ in the remote version authority");
		
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			ACVersion.execute();
		}

		config.save();

		core = new AgeCraftCore();

		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].preInit();
			}
		}
		for(ACComponent component : components) {
			component.preInit();
		}
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
		
		//init rank manager
		rankManager = new ACRankManager();
		rankManager.init();

		// init tech tree
		techTree = new TechTree();
		techTree.init();

		// init tick handlers
		tickHandler = new ACTickHandler();
		tickHandlerClient = new ACTickHandlerClient();

		// register server tick handler
		TickRegistry.registerTickHandler(AgeCraft.instance.tickHandler, Side.SERVER);

		// register packet handler
		packetHandler = new ACPacketHandler();
		NetworkRegistry.instance().registerConnectionHandler(packetHandler);
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		// register event handler
		ACEventHandler eventHandler = new ACEventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);

		// register crafting handler
		GameRegistry.registerCraftingHandler(eventHandler);

		// add world generator
		worldGenerator = new ACWorldGenerator();
		GameRegistry.registerWorldGenerator(worldGenerator);
		
		// register dimensions
		DimensionManager.registerProviderType(10, PrehistoryProvider.class, false);
		DimensionManager.registerDimension(10, 10);

		proxy.registerRenderInformation();
		proxy.registerPlayerAPI();
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

		try {
			DimensionManager.spawnSettings.put(0, false);
			DimensionManager.spawnSettings.put(1, false);
			DimensionManager.spawnSettings.put(-1, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister iconRegister) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].registerBlockIcons(iconRegister);
			}
		}
		for(ACComponent component : components) {
			component.registerBlockIcons(iconRegister);
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerItemIcons(IconRegister iconRegister) {
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				Age.ages[i].registerItemIcons(iconRegister);
			}
		}
		for(ACComponent component : components) {
			component.registerItemIcons(iconRegister);
		}
	}
}
