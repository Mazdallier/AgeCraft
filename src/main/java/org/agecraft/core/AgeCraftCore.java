package org.agecraft.core;

import net.minecraft.item.crafting.CraftingManager;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.core.registry.BiomeRegistry;
import org.agecraft.core.tileentities.TileEntityDNA;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AgeCraftCore extends ACComponent {

	public static AgeCraftCore instance;

	public Stone stone;
	public Metals metals;
	public Trees trees;
	public Tools tools;
	public Armor armor;

	public AgeCraftCore() {
		super("core", true);

		stone = new Stone();
		metals = new Metals();
		trees = new Trees();
		tools = new Tools();
		armor = new Armor();

		instance = this;
	}

	@Override
	public void preInit() {
		// register tile entities
		GameRegistry.registerTileEntity(TileEntityDNA.class, "TileDNA");
		
		// remove recipes
		CraftingManager.getInstance().getRecipeList().clear();
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {
		// register biomes
		BiomeRegistry.registerBiomes();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return AgeCraftCoreClient.instance != null ? AgeCraftCoreClient.instance : new AgeCraftCoreClient(this);
	}
}
