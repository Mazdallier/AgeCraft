package elcon.mods.agecraft.science;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class Solid {

	public static HashMap<String, Solid> solidList = new HashMap<String, Solid>();
	
	public String name;
	public ItemStack solid;
	public ArrayList<ItemStack> equalSolids = new ArrayList<ItemStack>();
	
	public double density;

	public Solid(String name, ItemStack solid, double density) {
		this.name = name;
		this.solid = solid;
		
		this.density = density;
	}
	
	public Solid(String name, ItemStack solid, ArrayList<ItemStack> equalSolids, double density) {
		this(name, solid, density);
		this.equalSolids.addAll(equalSolids);
	}
	
	public static Solid get(String name) {
		return solidList.get(name);
	}
	
	public static void registerSolid(Solid solid) {
		solidList.put(solid.name, solid);
	}
	
	public static void registerSolids() {
		registerSolid(new Solid("water", new ItemStack(Block.ice), 0.9167));
	}
}
