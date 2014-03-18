package org.agecraft.core.biomes;

import org.agecraft.ACComponent;

import net.minecraft.world.biome.BiomeGenBase;

public class Biomes extends ACComponent {

	public static BiomeGenBase ocean; // 0
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

	public static BiomeGenBase plains; // 29
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
		deepOcean = new BiomeDeepOcean(1).setBiomeName("Deep Ocean");
		river = new BiomeRiver(2).setBiomeName("River");
		island = new BiomeIsland(3).setBiomeName("Island");
		archipelago = new BiomeArchipelago(4).setBiomeName("Archipelago");
		beach = new BiomeBeach(5).setBiomeName("Beach");
		stoneBeach = new BiomeStoneBeach(6).setBiomeName("Stone Beach");
		snowBeach = new BiomeSnowBeach(7).setBiomeName("Snow Beach");

		frozenOcean = new BiomeFrozenOcean(8).setBiomeName("Frozen Ocean");
		frozenRiver = new BiomeFrozenRiver(9).setBiomeName("Frozen River");
		alpine = new BiomeAlpine(10).setMutation().setBiomeName("Alpine");
		glacier = new BiomeGlacier(11).setMutation().setBiomeName("Glacier");
		taiga = new BiomeTaiga(12).setMutation().setBiomeName("Taiga");
		taigaHills = new BiomeTaigaHills(13).setBiomeName("Taiga Hills");
		snowForest = new BiomeSnowForest(14).setMutation().setBiomeName("Snow Forest");
		tundra = new BiomeTundra(15).setMutation().setBiomeName("Tundra");

		mountains = new BiomeMountains(16).setMutation().setBiomeName("Mountians");
		extremeHills = new BiomeExtremeHills(17).setMutation().setBiomeName("Extreme Hills");
		extremeHillsEdge = new BiomeExtremeHillsEdge(18).setBiomeName("Extreme Hills Edge");
		extremeHillsPlus = new BiomeExtremeHillsPlus(19).setBiomeName("Extreme Hills Plus");
		stoneHills = new BiomeStoneHills(20).setMutation().setBiomeName("Stone Hills");
		volcano = new BiomeVolcano(21).setMutation().setBiomeName("Volcano");

		swamp = new BiomeSwamp(22).setMutation().setBiomeName("Swamp");
		greenSwamp = new BiomeGreenSwamp(23).setMutation().setBiomeName("Green Swamp");
		marsh = new BiomeMarsh(24).setMutation().setBiomeName("Marsh");
		bayou = new BiomeBayou(25).setMutation().setBiomeName("Bayou");
		bog = new BiomeBog(26).setMutation().setBiomeName("Bog");
		mangrove = new BiomeMangrove(27).setMutation().setBiomeName("Mangrove");

		plains = new BiomePlains(28).setMutation().setBiomeName("Plains");
		highland = new BiomeHighland(29).setMutation().setBiomeName("Highland");
		chaparral = new BiomeChaparral(30).setMutation().setBiomeName("Chaparral");
		heathland = new BiomeHeathland(31).setMutation().setBiomeName("Heathland");
		pasture = new BiomePasture(32).setMutation().setBiomeName("Pasture");
		prairie = new BiomePrairie(33).setMutation().setBiomeName("Prairie");

		forest1 = new BiomeForest1(34).setMutation().setBiomeName("Forest 1");
		forestHills1 = new BiomeForestHills1(35).setBiomeName("Forest Hills 1");
		forest2 = new BiomeForest2(36).setMutation().setBiomeName("Forest 2");
		forestHills2 = new BiomeForestHills2(37).setBiomeName("Forest Hills 2");
		forest3 = new BiomeForest3(38).setMutation().setBiomeName("Forest 3");
		forestHills3 = new BiomeForestHills3(39).setBiomeName("Forest Hills 3");
		meadow = new BiomeMeadow(40).setMutation().setBiomeName("Meadow");
		roofedForest = new BiomeRoofedForest(41).setMutation().setBiomeName("Roofed Forest");

		jungle = new BiomeJungle(42).setMutation().setBiomeName("Jungle");
		jungleHills = new BiomeJungleHills(43).setBiomeName("Jungle Hills");
		jungleEdge = new BiomeJungleEdge(44).setBiomeName("Jungle Edge");
		rainforest = new BiomeRainforest(45).setMutation().setBiomeName("Rainforest");
		rainforestHills = new BiomeRainforestHills(46).setBiomeName("Rainforest Hills");

		wasteland = new BiomeWasteland(47).setMutation().setBiomeName("Wasteland");
		deadForest = new BiomeDeadForest(48).setMutation().setBiomeName("Dead Forest");
		deadlands = new BiomeDeadlands(49).setMutation().setBiomeName("Deadlands");
		scrubland = new BiomeScrubland(50).setMutation().setBiomeName("Scrubland");

		desert = new BiomeDesert(51).setMutation().setBiomeName("Desert");
		desertHills = new BiomeDesertHills(52).setBiomeName("Desert Hills");
		redBadlands = new BiomeRedBadlands(53).setBiomeName("Red Badlands");
		droughtPlains = new BiomeDroughtPlains(54).setMutation().setBiomeName("Drought Plains");
		savanna = new BiomeSavanna(55).setMutation().setBiomeName("Savanna");
		savannaPlateau = new BiomeSavannaPlateau(56).setBiomeName("Savanna Plateau");
		dunes = new BiomeDunes(57).setMutation().setBiomeName("Dunes");
		lushDesert = new BiomeLushDesert(58).setMutation().setBiomeName("Lush Desert");
		mesa = new BiomeMesa(59).setMutation().setBiomeName("Mesa");
		mesaPlateau = new BiomeMesaPlateau(60).setBiomeName("Mesa Plateau");
		mesaPlateauF = new BiomeMesaPlateauF(61).setBiomeName("Mesa Plateau F");
		steppe = new BiomeSteppe(62).setMutation().setBiomeName("Steppe");

		timelessIsland = new BiomeTimelessIsland(63).setBiomeName("Timeless Island");
		timelessIslandShore = new BiomeTimelessIslandShore(64).setBiomeName("Timeless Island Shore");
	}
}
