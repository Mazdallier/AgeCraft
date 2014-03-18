package org.agecraft.core.biomes;

import org.agecraft.ACComponent;

import net.minecraft.world.biome.BiomeGenBase;

public class Biomes extends ACComponent {

	public static BiomeGenBase ocean;
	public static BiomeGenBase plains;
	
	public static BiomeGenBase deepOcean;
	public static BiomeGenBase river;
	public static BiomeGenBase island;
	public static BiomeGenBase archipelago;
	public static BiomeGenBase beach;
	public static BiomeGenBase stoneBeach;
	public static BiomeGenBase snowBeach;

	public static BiomeGenBase frozenOcean; // 8
	public static BiomeGenBase frozenRiver;
	public static BiomeGenBase alpine;
	public static BiomeGenBase glacier;
	public static BiomeGenBase icePlains;
	public static BiomeGenBase taiga;
	public static BiomeGenBase taigaHills;
	public static BiomeGenBase snowForest;
	public static BiomeGenBase tundra;

	public static BiomeGenBase mountains; // 17
	public static BiomeGenBase extremeHills;
	public static BiomeGenBase extremeHillsEdge;
	public static BiomeGenBase extremeHillsPlus;
	public static BiomeGenBase stoneHills;
	public static BiomeGenBase volcano;

	public static BiomeGenBase swamp; // 23
	public static BiomeGenBase greenSwamp;
	public static BiomeGenBase marsh;
	public static BiomeGenBase bayou;
	public static BiomeGenBase bog;
	public static BiomeGenBase mangrove;

	public static BiomeGenBase flowerPlains; // 29
	public static BiomeGenBase highland;
	public static BiomeGenBase chaparral;
	public static BiomeGenBase heathland;
	public static BiomeGenBase pasture;
	public static BiomeGenBase prairie;

	public static BiomeGenBase forest1; // 35
	public static BiomeGenBase forestHills1;
	public static BiomeGenBase forest2;
	public static BiomeGenBase forestHills2;
	public static BiomeGenBase forest3;
	public static BiomeGenBase forestHills3;
	public static BiomeGenBase meadow;
	public static BiomeGenBase roofedForest;

	public static BiomeGenBase jungle; // 43
	public static BiomeGenBase jungleHills;
	public static BiomeGenBase jungleEdge;
	public static BiomeGenBase rainforest;
	public static BiomeGenBase rainforestHills;

	public static BiomeGenBase wasteland; // 48
	public static BiomeGenBase deadForest;
	public static BiomeGenBase deadlands;
	public static BiomeGenBase scrubland;

	public static BiomeGenBase desert; // 52
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
	
	public static BiomeGenBase timelessIsland; //64
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
		
		deepOcean = new BiomeDeepOcean(2).setBiomeName("Deep Ocean");
		river = new BiomeRiver(3).setBiomeName("River");
		island = new BiomeIsland(4).setBiomeName("Island");
		archipelago = new BiomeArchipelago(5).setBiomeName("Archipelago");
		beach = new BiomeBeach(6).setBiomeName("Beach");
		stoneBeach = new BiomeStoneBeach(7).setBiomeName("Stone Beach");
		snowBeach = new BiomeSnowBeach(8).setBiomeName("Snow Beach");

		frozenOcean = new BiomeFrozenOcean(9).setBiomeName("Frozen Ocean");
		frozenRiver = new BiomeFrozenRiver(10).setBiomeName("Frozen River");
		alpine = new BiomeAlpine(11).setMutation().setBiomeName("Alpine");
		glacier = new BiomeGlacier(12).setMutation().setBiomeName("Glacier");
		icePlains = new BiomeIcePlains(13).setMutation().setBiomeName("Ice Plains");
		taiga = new BiomeTaiga(14).setMutation().setBiomeName("Taiga");
		taigaHills = new BiomeTaigaHills(15).setBiomeName("Taiga Hills");
		snowForest = new BiomeSnowForest(16).setMutation().setBiomeName("Snow Forest");
		tundra = new BiomeTundra(17).setMutation().setBiomeName("Tundra");

		mountains = new BiomeMountains(18).setMutation().setBiomeName("Mountians");
		extremeHills = new BiomeExtremeHills(19).setMutation().setBiomeName("Extreme Hills");
		extremeHillsEdge = new BiomeExtremeHillsEdge(20).setBiomeName("Extreme Hills Edge");
		extremeHillsPlus = new BiomeExtremeHillsPlus(21).setBiomeName("Extreme Hills Plus");
		stoneHills = new BiomeStoneHills(22).setMutation().setBiomeName("Stone Hills");
		volcano = new BiomeVolcano(23).setMutation().setBiomeName("Volcano");

		swamp = new BiomeSwamp(24).setMutation().setBiomeName("Swamp");
		greenSwamp = new BiomeGreenSwamp(25).setMutation().setBiomeName("Green Swamp");
		marsh = new BiomeMarsh(26).setMutation().setBiomeName("Marsh");
		bayou = new BiomeBayou(27).setMutation().setBiomeName("Bayou");
		bog = new BiomeBog(28).setMutation().setBiomeName("Bog");
		mangrove = new BiomeMangrove(29).setMutation().setBiomeName("Mangrove");

		flowerPlains = new BiomeFlowerPlains(30).setMutation().setBiomeName("Flower Plains");
		highland = new BiomeHighland(31).setMutation().setBiomeName("Highland");
		chaparral = new BiomeChaparral(32).setMutation().setBiomeName("Chaparral");
		heathland = new BiomeHeathland(33).setMutation().setBiomeName("Heathland");
		pasture = new BiomePasture(34).setMutation().setBiomeName("Pasture");
		prairie = new BiomePrairie(35).setMutation().setBiomeName("Prairie");

		forest1 = new BiomeForest1(36).setMutation().setBiomeName("Forest 1");
		forestHills1 = new BiomeForestHills1(37).setBiomeName("Forest Hills 1");
		forest2 = new BiomeForest2(38).setMutation().setBiomeName("Forest 2");
		forestHills2 = new BiomeForestHills2(39).setBiomeName("Forest Hills 2");
		forest3 = new BiomeForest3(40).setMutation().setBiomeName("Forest 3");
		forestHills3 = new BiomeForestHills3(41).setBiomeName("Forest Hills 3");
		meadow = new BiomeMeadow(42).setMutation().setBiomeName("Meadow");
		roofedForest = new BiomeRoofedForest(43).setMutation().setBiomeName("Roofed Forest");

		jungle = new BiomeJungle(44).setMutation().setBiomeName("Jungle");
		jungleHills = new BiomeJungleHills(45).setBiomeName("Jungle Hills");
		jungleEdge = new BiomeJungleEdge(46).setBiomeName("Jungle Edge");
		rainforest = new BiomeRainforest(47).setMutation().setBiomeName("Rainforest");
		rainforestHills = new BiomeRainforestHills(48).setBiomeName("Rainforest Hills");

		wasteland = new BiomeWasteland(49).setMutation().setBiomeName("Wasteland");
		deadForest = new BiomeDeadForest(50).setMutation().setBiomeName("Dead Forest");
		deadlands = new BiomeDeadlands(51).setMutation().setBiomeName("Deadlands");
		scrubland = new BiomeScrubland(52).setMutation().setBiomeName("Scrubland");

		desert = new BiomeDesert(53).setMutation().setBiomeName("Desert");
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
