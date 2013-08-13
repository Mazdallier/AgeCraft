package elcon.mods.agecraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import elcon.mods.agecraft.creativetabs.CreativeTabAge;
import elcon.mods.agecraft.creativetabs.CreativeTabAgeCraft;
import elcon.mods.agecraft.creativetabs.CreativeTabMetals;
import elcon.mods.agecraft.creativetabs.CreativeTabWood;

public class ACCreativeTabs {

	public static CreativeTabs ageCraft = new CreativeTabAgeCraft(12);
	
	public static CreativeTabs metals = new CreativeTabMetals(13, "Metals");
	public static CreativeTabs wood = new CreativeTabWood(14, "Wood");
	
	public static CreativeTabs prehistoryAge = new CreativeTabAge(15, "Prehistory", new ItemStack(Block.dirt.blockID, 1, 0));
	public static CreativeTabs agricultureAge = new CreativeTabAge(16, "Agriculture", new ItemStack(Item.wheat.itemID, 1, 0));;
	public static CreativeTabs ancientEgyptAge;
	public static CreativeTabs ancientChinaAge;
	public static CreativeTabs romanGreekAge;
	public static CreativeTabs mediavalAge;
	public static CreativeTabs earlyModernAge;
	public static CreativeTabs industrialAge;
	public static CreativeTabs modernAge;
	public static CreativeTabs futureAge;
}
