package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.StoneTypeRegistry.StoneType;

public class StoneTypeRegistry extends Registry<StoneType> {

	public static class StoneType {
		
		public int id;
		public String name;
		
		public IIcon icon;
		public IIcon iconCracked;
		public IIcon iconMossy;
		public IIcon[] iconsBrick = new IIcon[8];
		public IIcon iconChiseledTop;
		public IIcon iconBrickPillar;
		public IIcon iconBrickPillarTop;
		
		public StoneType(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static StoneTypeRegistry instance = new StoneTypeRegistry();
	
	public StoneTypeRegistry() {
		super(16);
	}
	
	public static void registerStoneType(StoneType stoneType) {
		instance.set(stoneType.id, stoneType);
	}
}
