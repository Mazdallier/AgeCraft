package org.agecraft.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.CraftingManager;

import org.agecraft.ACComponent;
import org.agecraft.ACComponentClient;
import org.agecraft.AgeCraft;
import org.agecraft.core.biomes.Biomes;
import org.agecraft.core.blocks.BlockClothingSelectorTest;
import org.agecraft.core.clothing.ClothingCategory;
import org.agecraft.core.clothing.ClothingRegistry;
import org.agecraft.core.clothing.ClothingRegistry.ClothingType;
import org.agecraft.core.clothing.ClothingUpdater;
import org.agecraft.core.clothing.MessageClothingAllUpdate;
import org.agecraft.core.clothing.MessageClothingList;
import org.agecraft.core.clothing.MessageClothingSelector;
import org.agecraft.core.clothing.MessageClothingSelectorOpen;
import org.agecraft.core.clothing.MessageClothingUnlockRequest;
import org.agecraft.core.clothing.MessageClothingUnlocks;
import org.agecraft.core.clothing.MessageClothingUpdate;
import org.agecraft.core.entity.EntityFallingBlock;
import org.agecraft.core.gui.MessageOpenGui;
import org.agecraft.core.registry.BiomeRegistry;
import org.agecraft.core.techtree.MessageTechTreeAllComponents;
import org.agecraft.core.techtree.MessageTechTreeComponent;
import org.agecraft.core.techtree.TechTree;
import org.agecraft.core.tileentities.TileEntityAnvil.MessageTileAnvil;
import org.agecraft.core.tileentities.TileEntityDNA;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.ElConQore;
import elcon.mods.elconqore.items.ItemBlockName;
import elcon.mods.elconqore.network.EQMessage;

public class AgeCraftCore extends ACComponent {

	public static AgeCraftCore instance;

	public TechTree techTree;
	public Biomes biomes;
	public Stone stone;
	public Building building;
	public Metals metals;
	public Trees trees;
	public Tools tools;
	public Armor armor;
	public Crafting crafting;
	public Farming farming;
	public Animals animals;
	public MultiParts multiparts;

	public static Block clothingSelectorTest;

	public AgeCraftCore() {
		super("core", true);

		techTree = new TechTree();
		biomes = new Biomes();
		stone = new Stone();
		building = new Building();
		metals = new Metals();
		trees = new Trees();
		tools = new Tools();
		armor = new Armor();
		crafting = new Crafting();
		farming = new Farming();
		animals = new Animals();
		multiparts = new MultiParts();

		instance = this;
	}

	@Override
	public void preInit() {
		// init blocks
		clothingSelectorTest = new BlockClothingSelectorTest().setBlockName("AC_clothingSelectorTest");

		// register blocks
		GameRegistry.registerBlock(clothingSelectorTest, ItemBlockName.class, "AC_clothingSelectorTest");

		// register tile entities
		GameRegistry.registerTileEntity(TileEntityDNA.class, "AC_TileDNA");

		//register entities
		EntityRegistry.registerModEntity(EntityFallingBlock.class, "AC_FallingBlock", EntityRegistry.findGlobalUniqueEntityId(), AgeCraft.instance, 160, 20, true);
		
		// remove recipes
		CraftingManager.getInstance().getRecipeList().clear();
	}

	@Override
	public void init() {
		// register clothing types
		ClothingRegistry.registerClothingType(new ClothingType("skin", 0, 0));
		ClothingRegistry.registerClothingType(new ClothingType("hair", 1, 3));
		ClothingRegistry.registerClothingType(new ClothingType("eyes", 2, 2));
		ClothingRegistry.registerClothingType(new ClothingType("mouth", 3, 1));
		ClothingRegistry.registerClothingType(new ClothingType("facialHair", 4, 4));
		ClothingRegistry.registerClothingType(new ClothingType("hat", 5, 5));
		ClothingRegistry.registerClothingType(new ClothingType("shirt", 6, 8));
		ClothingRegistry.registerClothingType(new ClothingType("pants", 7, 7));
		ClothingRegistry.registerClothingType(new ClothingType("boots", 8, 6));

		// register clothing categories
		ClothingRegistry.registerClothingCategory(new ClothingCategory("general", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/general/general.zip"));
		ClothingRegistry.registerClothingCategory(new ClothingCategory("special", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/special/special.zip"));
		ClothingRegistry.registerClothingCategory(new ClothingCategory("prehistory", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing-versions.dat", "https://raw.github.com/AgeCraft/AgeCraft/master/clothing/prehistory/prehistory.zip"));
	
		//init rank manager
		RankManager.init();
	}

	@Override
	public void postInit() {
		// register biomes
		BiomeRegistry.registerBiomes();

		// sort clothing types
		ClothingRegistry.sortClothingTypes();

		// update clothing
		ClothingUpdater clothingUpdater = new ClothingUpdater(new File(ElConQore.minecraftDir, File.separator + "clothing"));
		clothingUpdater.run();
	}

	@Override
	public Class<? extends EQMessage>[] getMessages() {
		return new Class[]{MessageOpenGui.class, MessageTechTreeComponent.class, MessageTechTreeAllComponents.class, MessageClothingList.class, MessageClothingUpdate.class, MessageClothingAllUpdate.class, MessageClothingSelectorOpen.class, MessageClothingSelector.class, MessageClothingUnlockRequest.class, MessageClothingUnlocks.class, MessageTileAnvil.class};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ACComponentClient getComponentClient() {
		return AgeCraftCoreClient.instance != null ? AgeCraftCoreClient.instance : new AgeCraftCoreClient(this);
	}
}
