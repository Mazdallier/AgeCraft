package org.agecraft;

import net.minecraft.creativetab.CreativeTabs;

import org.agecraft.core.creativetabs.CreativeTabAgeAgriculture;
import org.agecraft.core.creativetabs.CreativeTabAgeCraft;
import org.agecraft.core.creativetabs.CreativeTabAgePrehistory;
import org.agecraft.core.creativetabs.CreativeTabArmor;
import org.agecraft.core.creativetabs.CreativeTabCrafting;
import org.agecraft.core.creativetabs.CreativeTabFarming;
import org.agecraft.core.creativetabs.CreativeTabFood;
import org.agecraft.core.creativetabs.CreativeTabMetal;
import org.agecraft.core.creativetabs.CreativeTabStone;
import org.agecraft.core.creativetabs.CreativeTabTools;
import org.agecraft.core.creativetabs.CreativeTabWood;

public class ACCreativeTabs {

	public static CreativeTabs ageCraft = new CreativeTabAgeCraft(12);
	
	public static CreativeTabs stone = new CreativeTabStone(13, "Stone");
	public static CreativeTabs metals = new CreativeTabMetal(14, "Metals");
	public static CreativeTabs wood = new CreativeTabWood(15, "Wood");
	public static CreativeTabs tools = new CreativeTabTools(16, "Tools");
	public static CreativeTabs armor = new CreativeTabArmor(17, "Armor");
	public static CreativeTabs crafting = new CreativeTabCrafting(18, "Crafting");
	public static CreativeTabs food = new CreativeTabFood(19, "Food");
	public static CreativeTabs farming = new CreativeTabFarming(20, "Farming");
	
	public static CreativeTabs prehistoryAge = new CreativeTabAgePrehistory(21, "Prehistory");
	public static CreativeTabs agricultureAge = new CreativeTabAgeAgriculture(22, "Agriculture");
	public static CreativeTabs ancientEgyptAge;
	public static CreativeTabs ancientChinaAge;
	public static CreativeTabs romanGreekAge;
	public static CreativeTabs mediavalAge;
	public static CreativeTabs earlyModernAge;
	public static CreativeTabs industrialAge;
	public static CreativeTabs modernAge;
	public static CreativeTabs futureAge;
}
