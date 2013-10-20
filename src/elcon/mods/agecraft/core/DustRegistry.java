package elcon.mods.agecraft.core;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.ACUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class DustRegistry {

	public static class Dust {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public Icon icon;
		
		public Dust(int id, String name, String localization, ItemStack stack) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
		}
	}
	
	public static Dust[] dusts = new Dust[1024];
	
	public static Dust getDust(ItemStack stack) {
		for(int i = 0; i < dusts.length; i++) {
			if(dusts[i] != null && ACUtil.areItemStacksEqualNoSize(dusts[i].stack, stack)) {
				return dusts[i];
			}
		}
		return null;
	}
	
	public static void registerDust(Dust dust) {
		if(dusts[dust.id] != null) {
			ACLog.warning("[DustRegistry] Overriding existing dust (" + dusts[dust.id].id + ": " + dusts[dust.id].name.toUpperCase() + ") with new dust (" + dust.id + ": " + dust.name.toUpperCase() + ")");
		}
		dusts[dust.id] = dust; 
	}
}
