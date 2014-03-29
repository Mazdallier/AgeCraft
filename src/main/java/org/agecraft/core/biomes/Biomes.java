package org.agecraft.core.biomes;

import net.minecraft.world.biome.BiomeGenBase;

import org.agecraft.ACComponent;

public class Biomes extends ACComponent {

	public static BiomeGenBase ocean;
	public static BiomeGenBase plains;
	public static BiomeGenBase desert;
	public static BiomeGenBase extremeHills;
	public static BiomeGenBase forest1;
	public static BiomeGenBase taiga;
	public static BiomeGenBase swamp;
	
	public static BiomeGenBase deepOcean;
	public static BiomeGenBase river;
	public static BiomeGenBase island;
	public static BiomeGenBase archipelago;
	public static BiomeGenBase beach;
	public static BiomeGenBase stoneBeach;
	public static BiomeGenBase snowBeach;

	public static BiomeGenBase frozenOcean;
	public static BiomeGenBase frozenRiver;
	public static BiomeGenBase alpine;
	public static BiomeGenBase glacier;
	public static BiomeGenBase icePlains;
	public static BiomeGenBase taigaHills;
	public static BiomeGenBase snowForest;
	public static BiomeGenBase tundra;

	public static BiomeGenBase mountains;
	public static BiomeGenBase extremeHillsEdge;
	public static BiomeGenBase extremeHillsPlus;
	public static BiomeGenBase stoneHills;
	public static BiomeGenBase volcano;

	public static BiomeGenBase greenSwamp;
	public static BiomeGenBase marsh;
	public static BiomeGenBase bayou;
	public static BiomeGenBase bog;
	public static BiomeGenBase mangrove;

	public static BiomeGenBase flowerPlains;
	public static BiomeGenBase highland;
	public static BiomeGenBase chaparral;
	public static BiomeGenBase heathland;
	public static BiomeGenBase pasture;
	public static BiomeGenBase prairie;

	public static BiomeGenBase forestHills1;
	public static BiomeGenBase forest2;
	public static BiomeGenBase forestHills2;
	public static BiomeGenBase forest3;
	public static BiomeGenBase forestHills3;
	public static BiomeGenBase meadow;
	public static BiomeGenBase roofedForest;

	public static BiomeGenBase jungle;
	public static BiomeGenBase jungleHills;
	public static BiomeGenBase jungleEdge;
	public static BiomeGenBase rainforest;
	public static BiomeGenBase rainforestHills;

	public static BiomeGenBase wasteland;
	public static BiomeGenBase deadForest;
	public static BiomeGenBase deadlands;
	public static BiomeGenBase scrubland;

	public static BiomeGenBase desertHills;
	public static BiomeGenBase redBadlands;
	public static BiomeGenBase droughtPlains;
	public static BiomeGenBase savanna;
	public static BiomeGenBase savannaPlateau;
	public static BiomeGenBase dunes;
	public static BiomeGenBase lushDesert;
	public static BiomeGenBase mesa;
	public static BiomeGenBase mesaPlateau;
	public static BiomeGenBase mesaPlateauF;
	public static BiomeGenBase steppe;
	
	public static BiomeGenBase timelessIsland;
	public static BiomeGenBase timelessIslandShore;

	public Biomes() {
		super("Biomes", false);
	}

	@Override
	public void preInit() {
		// remove vanilla biomes
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			BiomeGenBase.getBiomeGenArray()[i] = null;
		}

		// init biomes
		ocean = new BiomeOcean(0).setBiomeName("Ocean");
		plains = new BiomePlains(1).setMutation().setBiomeName("Plains");
		desert = new BiomeDesert(2).setMutation().setBiomeName("Desert");
		extremeHills = new BiomeExtremeHills(3).setMutation().setBiomeName("Extreme Hills");
		forest1 = new BiomeForest1(4).setMutation().setBiomeName("Forest 1");
		taiga = new BiomeTaiga(5).setMutation().setBiomeName("Taiga");
		swamp = new BiomeSwamp(6).setMutation().setBiomeName("Swamp");
		river = new BiomeRiver(7).setBiomeName("River");		
		
		deepOcean = new BiomeDeepOcean(8).setBiomeName("Deep Ocean");
		island = new BiomeIsland(9).setBiomeName("Island");
		archipelago = new BiomeArchipelago(10).setBiomeName("Archipelago");
		beach = new BiomeBeach(11).setBiomeName("Beach");
		stoneBeach = new BiomeStoneBeach(12).setBiomeName("Stone Beach");
		snowBeach = new BiomeSnowBeach(13).setBiomeName("Snow Beach");

		frozenOcean = new BiomeFrozenOcean(14).setBiomeName("Frozen Ocean");
		frozenRiver = new BiomeFrozenRiver(15).setBiomeName("Frozen River");
		alpine = new BiomeAlpine(16).setMutation().setBiomeName("Alpine");
		glacier = new BiomeGlacier(17).setMutation().setBiomeName("Glacier");
		icePlains = new BiomeIcePlains(18).setMutation().setBiomeName("Ice Plains");
		taigaHills = new BiomeTaigaHills(19).setBiomeName("Taiga Hills");
		snowForest = new BiomeSnowForest(20).setMutation().setBiomeName("Snow Forest");
		tundra = new BiomeTundra(21).setMutation().setBiomeName("Tundra");

		mountains = new BiomeMountains(22).setMutation().setBiomeName("Mountians");
		extremeHillsEdge = new BiomeExtremeHillsEdge(23).setBiomeName("Extreme Hills Edge");
		extremeHillsPlus = new BiomeExtremeHillsPlus(24).setBiomeName("Extreme Hills Plus");
		stoneHills = new BiomeStoneHills(25).setMutation().setBiomeName("Stone Hills");
		volcano = new BiomeVolcano(26).setMutation().setBiomeName("Volcano");

		greenSwamp = new BiomeGreenSwamp(27).setMutation().setBiomeName("Green Swamp");
		marsh = new BiomeMarsh(28).setMutation().setBiomeName("Marsh");
		bayou = new BiomeBayou(29).setMutation().setBiomeName("Bayou");
		bog = new BiomeBog(30).setMutation().setBiomeName("Bog");
		mangrove = new BiomeMangrove(31).setMutation().setBiomeName("Mangrove");

		flowerPlains = new BiomeFlowerPlains(32).setMutation().setBiomeName("Flower Plains");
		highland = new BiomeHighland(33).setMutation().setBiomeName("Highland");
		chaparral = new BiomeChaparral(34).setMutation().setBiomeName("Chaparral");
		heathland = new BiomeHeathland(35).setMutation().setBiomeName("Heathland");
		pasture = new BiomePasture(36).setMutation().setBiomeName("Pasture");
		prairie = new BiomePrairie(37).setMutation().setBiomeName("Prairie");

		forestHills1 = new BiomeForestHills1(38).setBiomeName("Forest Hills 1");
		forest2 = new BiomeForest2(39).setMutation().setBiomeName("Forest 2");
		forestHills2 = new BiomeForestHills2(40).setBiomeName("Forest Hills 2");
		forest3 = new BiomeForest3(41).setMutation().setBiomeName("Forest 3");
		forestHills3 = new BiomeForestHills3(42).setBiomeName("Forest Hills 3");
		meadow = new BiomeMeadow(43).setMutation().setBiomeName("Meadow");
		roofedForest = new BiomeRoofedForest(44).setMutation().setBiomeName("Roofed Forest");

		jungle = new BiomeJungle(45).setMutation().setBiomeName("Jungle");
		jungleHills = new BiomeJungleHills(46).setBiomeName("Jungle Hills");
		jungleEdge = new BiomeJungleEdge(47).setBiomeName("Jungle Edge");
		rainforest = new BiomeRainforest(48).setMutation().setBiomeName("Rainforest");
		rainforestHills = new BiomeRainforestHills(49).setBiomeName("Rainforest Hills");

		wasteland = new BiomeWasteland(50).setMutation().setBiomeName("Wasteland");
		deadForest = new BiomeDeadForest(51).setMutation().setBiomeName("Dead Forest");
		deadlands = new BiomeDeadlands(52).setMutation().setBiomeName("Deadlands");
		scrubland = new BiomeScrubland(53).setMutation().setBiomeName("Scrubland");

		desertHills = new BiomeDesertHills(54).setBiomeName("Desert Hills");
		redBadlands = new BiomeRedBadlands(55).setBiomeName("Red Badlands");
		droughtPlains = new BiomeDroughtPlains(56).setMutation().setBiomeName("Drought Plains");
		savanna = new BiomeSavanna(57).setMutation().setBiomeName("Savanna");
		savannaPlateau = new BiomeSavannaPlateau(58).setBiomeName("Savanna Plateau");
		dunes = new BiomeDunes(59).setMutation().setBiomeName("Dunes");
		lushDesert = new BiomeLushDesert(60).setMutation().setBiomeName("Lush Desert");
		mesa = new BiomeMesa(61).setMutation().setBiomeName("Mesa");
		mesaPlateau = new BiomeMesaPlateau(62).setBiomeName("Mesa Plateau");
		mesaPlateauF = new BiomeMesaPlateauF(63).setBiomeName("Mesa Plateau F");
		steppe = new BiomeSteppe(64).setMutation().setBiomeName("Steppe");

		timelessIsland = new BiomeTimelessIsland(65).setBiomeName("Timeless Island");
		timelessIslandShore = new BiomeTimelessIslandShore(66).setBiomeName("Timeless Island Shore");
	}
}
