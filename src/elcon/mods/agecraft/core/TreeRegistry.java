package elcon.mods.agecraft.core;

import net.minecraft.util.Icon;
import elcon.mods.agecraft.ACLog;

public class TreeRegistry {

	public static class Tree {
		
		public int id;
		public String name;
		
		public int leavesColor;
		public boolean useBiomeColor;
		public int woodColor;
		
		public Icon wood;
		public Icon woodTop;
		public Icon log;
		public Icon logTop;
		public Icon planks;
		public Icon leaves;
		public Icon leavesFast;
		public Icon smallSapling;
		public Icon sapling;
		public Icon stick;
		public Icon dust;
		public Icon bucket;
		
		public Tree(int id, String name, int leavesColor, boolean useBiomeColor, int woodColor) {
			this.id = id;
			this.name = name;
			this.leavesColor = leavesColor;
			this.useBiomeColor = useBiomeColor;
			this.woodColor = woodColor;
		}
	}
	
	public static Tree[] trees = new Tree[128];
	
	public static void registerTree(Tree tree) {
		if(trees[tree.id] != null) {
			ACLog.warning("[TreeRegistry] Overriding existing tree (" + trees[tree.id].id + ": " + trees[tree.id].name.toUpperCase() + ") with new tree (" + tree.id + ": " + tree.name.toUpperCase() + ")");
		}
		trees[tree.id]= tree;
	}
}
