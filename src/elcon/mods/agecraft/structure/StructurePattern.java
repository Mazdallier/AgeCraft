package elcon.mods.agecraft.structure;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.util.Vect;

public class StructurePattern {
	
	private final String uid;
	private EnumStructureBlock[][][] structure;
	private int width;
	private int height;
	private int depth;
	private int xOffset;
	private int yOffset;
	private int zOffset = -1;

	public StructurePattern(String uid, int width, int height, int depth, String[] patterns) {
		this(uid, width, height, depth);
		setStructure(patterns);
	}

	public StructurePattern(String uid, int width, int height, int depth) {
		this.uid = uid;
		this.width = width;
		this.height = height;
		this.depth = depth;
		structure = new EnumStructureBlock[width][height][depth];
	}

	private void setStructure(String[] patterns) {
		String fullpattern = "";

		for(String pattern : patterns) {
			fullpattern = fullpattern + pattern;
		}
		if(fullpattern.length() != getWidth() * getHeight() * getDepth()) {
			ACLog.info("[MultiBlock] Incorrect pattern " + fullpattern + " (" + fullpattern.length() + ") for (" + getWidth() + "/" + getHeight() + "/" + getDepth() + ")");
		}

		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) { 
				for(int k = 0; k < getDepth(); k++) {
					for(EnumStructureBlock type : EnumStructureBlock.values()) {
						if(type.getKey() == fullpattern.charAt(i * getHeight() * getDepth() + j * getDepth() + k)) {
							structure[i][j][k] = type;
							break;
						}
					}
				}
			}
		}
	}

	public StructurePattern setOffsets(int xOffset, int yOffset, int zOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		return this;
	}

	public EnumStructureBlock getAt(int x, int y, int z, boolean rotate) {
		if(rotate)
			return structure[z][y][x];
		return structure[x][y][z];
	}

	public Vect getDimensions(boolean rotate) {
		if(rotate) {
			return new Vect(getDepth(), getHeight(), getWidth());
		}
		return new Vect(getWidth(), getHeight(), getDepth());
	}
	
	public boolean isEnabled() {
		return true;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}

	public int getOffsetX() {
		return xOffset;
	}

	public int getOffsetY() {
		return yOffset;
	}

	public int getOffsetZ() {
		return zOffset;
	}

	public static enum EnumStructureBlock {
		ANY('X'), FOREIGN('F'), AIR('O'), MASTER('M'), GLASS('G'), BLOCK_A('A'), BLOCK_B('B'), BLOCK_C('C'), BLOCK_D('D'), BLOCK_E('E');

		private char key;

		private EnumStructureBlock(char key) {
			this.key = key;
		}

		public char getKey() {
			return key;
		}
	}
}