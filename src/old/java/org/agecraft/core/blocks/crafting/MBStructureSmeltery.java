package org.agecraft.core.blocks.crafting;

import java.util.ArrayList;

import org.agecraft.core.Crafting;
import org.agecraft.core.Stone;

import net.minecraft.tileentity.TileEntity;
import elcon.mods.core.structure.MBStructure;
import elcon.mods.core.structure.MBStructurePattern;

public class MBStructureSmeltery extends MBStructure {

	public MBStructurePattern[] smelteryPatterns = new MBStructurePattern[14];
	
	public ArrayList<Integer> stoneTypes = new ArrayList<Integer>();
	
	public MBStructureSmeltery(String name) {
		super(name);
		
		for(int size = 3; size <= 16; size++) {
			int halfSize = (int) Math.floor(size / 2.0D);
			char[] chars = new char[size * size * size];
			for(int j = 0; j < size; j++) {
				for(int i = 0; i < size; i++) {
					for(int k = 0; k < size; k++) {
						int index = i + k * size + j * size * size;
						if(i == 0 || i == size - 1 || j == 0 || j == size - 1 || k == 0 || k == size - 1) {
							if(j == 0) {
								chars[index] = 'F';
							} else {
								chars[index] = 'B';
							}
						} else {
							chars[index] = ' ';
						}
					}
				}
			}
			chars[halfSize + halfSize * size] = 'M';
			String sizeString = Integer.toString(size);
			smelteryPatterns[size - 3] = new MBStructurePattern("smeltery" + sizeString + "x" + sizeString + "x" + sizeString, size, size, size, new String(chars)).setOffsets(-halfSize, 0, -halfSize);
			addPattern(smelteryPatterns[size - 3]);
		}
		
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
		if(required == 'F' || required == 'M') {
			return blockID == Crafting.smelteryFurnace.blockID;
		} else if(required == 'B') {
			return stoneTypes.contains(blockID);
		} else if(required == ' ') {
			return blockID == 0;
		}
		return false;
	}
}
