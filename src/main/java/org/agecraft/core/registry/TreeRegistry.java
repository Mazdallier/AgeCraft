package org.agecraft.core.registry;

import net.minecraft.util.IIcon;

import org.agecraft.core.registry.TreeRegistry.Tree;
import org.agecraft.core.worldgen.WorldGenTree;

public class TreeRegistry extends Registry<Tree> {

	public static class Tree {
		
		public int id;
		public String name;
		
		public int leafColor;
		public boolean useBiomeColor;
		public int woodColor;
		
		public WorldGenTree worldGen;
		
		public IIcon wood;
		public IIcon woodTop;
		public IIcon log;
		public IIcon logTop;
		public IIcon planks;
		public IIcon leaves;
		public IIcon leavesFast;
		public IIcon smallSapling;
		public IIcon sapling;
		public IIcon stick;
		public IIcon dust;
		public IIcon bucket;
		
		public Tree(int id, String name, int leavesColor, boolean useBiomeColor, int woodColor, WorldGenTree worldGen) {
			this.id = id;
			this.name = name;
			
			this.leafColor = leavesColor;
			this.useBiomeColor = useBiomeColor;
			this.woodColor = woodColor;
			
			this.worldGen = worldGen;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static TreeRegistry instance = new TreeRegistry();
	
	public TreeRegistry() {
		super(128);
	}

	public static void registerTree(Tree tree) {
		instance.set(tree.id, tree);
	}
}
