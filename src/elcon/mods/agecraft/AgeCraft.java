package elcon.mods.agecraft;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IconRegister;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.AgeCraftCore;
import elcon.mods.agecraft.prehistory.PrehistoryProvider;
import elcon.mods.agecraft.ranks.ACRankManager;
import elcon.mods.agecraft.tech.TechTree;
import elcon.mods.core.ElConCore;
import elcon.mods.core.ElConMod;

@Mod(modid = ACReference.MOD_ID, name = ACReference.NAME, version = ACReference.VERSION, acceptedMinecraftVersions = ACReference.MC_VERSION, dependencies = ACReference.DEPENDENCIES)
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
		minecraftDir = ElConCore.minecraftDir;
		ElConCore.registerMod(ACReference.NAME, new ElConMod(ACReference.NAME, ACReference.VERSION, ACReference.VERSION_URL, event.getSourceFile(), event.getSuggestedConfigurationFile(), ACConfig.class, new ACSaveHandler()));

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
		rankManager = new ACRankManager();
		rankManager.init();

		//init tech tree
		techTree = new TechTree();
		techTree.init();

		//init tick handlers
		tickHandler = new ACTickHandler();
		tickHandlerClient = new ACTickHandlerClient();

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
