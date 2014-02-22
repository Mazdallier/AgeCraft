package org.agecraft.core.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.agecraft.ACUtil;
import org.agecraft.core.registry.DustRegistry.Dust;

public class DustRegistry extends Registry<Dust> {
	
	public static class Dust {
		
		public int id;
		public String name;
		public String localization;
		
		public ItemStack stack;
		
		public IIcon icon;
		
		public Dust(int id, String name, String localization, ItemStack stack) {
			this.id = id;
			this.name = name;
			this.localization = localization;
			
			this.stack = stack;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	public static DustRegistry instance = new DustRegistry();
	
	public DustRegistry() {
		super(256);
	}
	
	public static Dust getDust(ItemStack stack) {
		for(int i = 0; i < instance.getAll().length; i++) {
			if(instance.get(i) != null && ACUtil.areItemStacksEqualNoSize(instance.get(i).stack, stack)) {
				return instance.get(i);
			}
		}
		return null;
	}
	
	public static void registerDust(Dust dust) {
		instance.set(dust.id, dust);
	}
}
