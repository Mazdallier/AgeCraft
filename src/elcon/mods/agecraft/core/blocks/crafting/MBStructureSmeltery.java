package elcon.mods.agecraft.core.blocks.crafting;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import elcon.mods.agecraft.core.Crafting;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.core.structure.MBStructure;
import elcon.mods.core.structure.MBStructurePattern;

public class MBStructureSmeltery extends MBStructure {

	public MBStructurePattern smeltery3x3x3 = new MBStructurePattern("smeltery3x3x3", 3, 3, 3, new String[]{
			"FFF", "FMF", "FFF", "BBB", "B B", "BBB", "BBB", "BBB", "BBB"
	}).setOffsets(-1, 0, -1);
	
	public ArrayList<Integer> stoneTypes = new ArrayList<Integer>();
	
	public MBStructureSmeltery(String name) {
		super(name);
		addPattern(smeltery3x3x3);
		
		stoneTypes.add(Stone.stone.blockID);
		stoneTypes.add(Stone.stoneBrick.blockID);
		stoneTypes.add(Stone.stoneBrickPillar.blockID);
		stoneTypes.add(Stone.coloredStone.blockID);
		stoneTypes.add(Stone.coloredStone.blockID);
		stoneTypes.add(Stone.coloredStoneCracked.blockID);
		stoneTypes.add(Stone.coloredStoneMossy.blockID);
		stoneTypes.add(Stone.coloredStoneBrick.blockID);
		stoneTypes.add(Stone.coloredStoneBrickPillar.blockID);
	}

	@Override
	public boolean areCharAndBlockEqual(char required, int blockID, int blockMetadata, TileEntity blockTileEntity) {
		if(required == 'F') {
			return blockID == Crafting.smelteryFurnace.blockID;
		} else if(required == 'B') {
			return stoneTypes.contains(blockID);
		} else if(required == ' ') {
			return blockID == 0;
		}
		return false;
	}
}
