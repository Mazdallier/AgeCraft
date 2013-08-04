package elcon.mods.agecraft.structure;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import elcon.mods.agecraft.util.Vect;

public abstract class StructureLogic implements IStructureLogic {

	protected ITileStructure structure;
	protected TileEntity structureTile;
	protected String uid;
	protected StructurePattern[] patterns;
	protected short activeStructurePattern = -1;
	protected boolean isRotated = false;
	protected HashMap idOnValid = new HashMap();
	protected HashMap metaOnValid = new HashMap();

	public StructureLogic(String uid, ITileStructure structure) {
		this.uid = uid;
		this.structure = structure;
		structureTile = ((TileEntity) structure);
	}

	public String getTypeUID() {
		return uid;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("StructurePatternOrdinal"))
			activeStructurePattern = nbt.getShort("StructurePatternOrdinal");
		isRotated = nbt.getBoolean("Rotated");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		if(activeStructurePattern >= 0)
			nbt.setShort("StructurePatternOrdinal", activeStructurePattern);
		nbt.setBoolean("Rotated", isRotated);
	}

	public void validateStructure() {
		ITileStructure master = structure.getCentralTE();
		if((!structure.isMaster()) && (master != null)) {
			master.validateStructure();
			return;
		}

		BlockStructure.EnumStructureState state = BlockStructure.EnumStructureState.INDETERMINATE;

		boolean rotate = false;
		for(int i = 0; i < patterns.length; i++) {
			if(patterns[i].isEnabled()) {
				state = determineMasterState(patterns[i], false);
				rotate = false;
				if((state == BlockStructure.EnumStructureState.INVALID) && (patterns[i].getWidth() != patterns[i].getDepth())) {
					state = determineMasterState(patterns[i], true);
					rotate = true;
				}

				if(state == BlockStructure.EnumStructureState.VALID) {
					activeStructurePattern = ((short) i);
					isRotated = rotate;
					break;
				}
			}

		}

		System.out.println(state);
		
		if(state == BlockStructure.EnumStructureState.INDETERMINATE) {
			return;
		}
		if(state == BlockStructure.EnumStructureState.VALID) {
			if(!structure.isMaster()) {
				structure.makeMaster();
				markStructureBlocks(patterns[activeStructurePattern]);
			}

		} else if(structure.isMaster()) {
			resetStructureBlocks(patterns[activeStructurePattern]);
		}
	}

	protected void resetStructureBlocks(StructurePattern struct) {
		Vect dimensions = struct.getDimensions(isRotated);
		int offsetX = struct.getOffsetX();
		int offsetZ = struct.getOffsetZ();
		if(isRotated) {
			offsetX = struct.getOffsetZ();
			offsetZ = struct.getOffsetX();
		}

		for(int i = 0; i < dimensions.x; i++)
			for(int j = 0; j < struct.getHeight(); j++)
				for(int k = 0; k < dimensions.z; k++) {
					int x = structureTile.xCoord + i + offsetX;
					int y = structureTile.yCoord + j + struct.getOffsetY();
					int z = structureTile.zCoord + k + offsetZ;

					TileEntity tile = structureTile.worldObj.getBlockTileEntity(x, y, z);
					if((tile instanceof ITileStructure)) {
						ITileStructure part = (ITileStructure) tile;
						if(part.getTypeUID().equals(getTypeUID())) {
							part.onStructureReset();
						}
					}
				}
	}

	protected void markStructureBlocks(StructurePattern struct) {
		Vect dimensions = struct.getDimensions(isRotated);
		int offsetX = struct.getOffsetX();
		int offsetZ = struct.getOffsetZ();
		if(isRotated) {
			offsetX = struct.getOffsetZ();
			offsetZ = struct.getOffsetX();
		}

		for(int i = 0; i < dimensions.x; i++) { 
			for(int j = 0; j < struct.getHeight(); j++) {
				for(int k = 0; k < dimensions.z; k++) {
					int x = structureTile.xCoord + i + offsetX;
					int y = structureTile.yCoord + j + struct.getOffsetY();
					int z = structureTile.zCoord + k + offsetZ;

					TileEntity tile = structureTile.worldObj.getBlockTileEntity(x, y, z);
					if((tile instanceof ITileStructure)) {
						ITileStructure part = (ITileStructure) tile;
						if(part.getTypeUID().equals(getTypeUID())) {
							part.setCentralTE((TileEntity) structure);
						}
					}
					
					StructurePattern.EnumStructureBlock type = struct.getAt(i, j, k, isRotated);	
					System.out.println(type + " | " + idOnValid.containsKey(type) + " | " + metaOnValid.containsKey("type"));
					if(idOnValid.containsKey(type)) {
						structureTile.worldObj.setBlock(x, y, z, ((Integer) idOnValid.get(type)).intValue(), 0, 2);
						structureTile.worldObj.markBlockForUpdate(x, y, z);
					}							
					if(metaOnValid.containsKey(type)) {								
						structureTile.worldObj.setBlockMetadataWithNotify(x, y, z, ((Integer) metaOnValid.get(type)).intValue(), 2);
						structureTile.worldObj.markBlockForUpdate(x, y, z);
					}
				}
			}
		}
	}

	protected abstract BlockStructure.EnumStructureState determineMasterState(StructurePattern struct, boolean rotate);
}
