package elcon.mods.agecraft.science;

import net.minecraft.item.ItemStack;

public class Solid {

	public ItemStack solid;
	
	public double density;

	public Solid(ItemStack solid, double density) {
		this.solid = solid;
		this.density = density;
	}
}
